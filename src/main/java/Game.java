import events.*;
import exceptions.InvalidOperationException;

import java.util.*;

public class Game extends AggregateRoot {

    private UUID id;
    private String name;
    private List<Player> players;
    private Deck deck;
    private Seed seed;
    private List<Card> table;
    private Integer playersWhoPlayedInThisTurn;
    private Integer playersWhoDrawInThisTurn;


    public static Game from(List<Event> events) {
        Game game = new Game();
        events.forEach(game::reapplyChange);
        return game;
    }

    public Game(UUID id, String name) {
        this();
        applyChange(new GameCreated(id, name));
    }

    private Game() {
        register(GameCreated.class, this::apply);
        register(PlayerJoined.class, this::apply);
        register(CardDealt.class, this::apply);
        register(BriscolaSelected.class, this::apply);
        register(CardPlayed.class, this::apply);
        register(CardDrawn.class, this::apply);
        register(HandCompleted.class, this::apply);
    }

    public void addPlayer(String playerName) {
        applyChange(new PlayerJoined(id, playerName));
    }

    public void dealFirstHand() {
        for(Player player: players) {
            List<Card> cards = deck.select(3);
            for(Card card: cards) {
                CardDealt event = new CardDealt(id, player.name, card.seed, card.value);
                applyChange(event);
            }
        }
        Card briscola = deck.select(1).get(0);
        BriscolaSelected event = new BriscolaSelected(id, briscola.seed, briscola.value);
        applyChange(event);
    }

    public void playCard(String playerName, Card card) {
        applyChange(new CardPlayed(id, playerName, card.seed, card.value));
        if (table.size() == 4) {
            applyChange(new HandCompleted(id));
        }
    }

    public void drawCard(String playerName) {
        Card drawnCard = deck.select(1).get(0);
        applyChange(new CardDrawn(id, playerName, drawnCard.seed, drawnCard.value));
    }

    private void apply(HandCompleted event) {
        //capire chi a vinto e dargli le carte
        // va salvato nelle carte giocate anche chi la ha giocata
        //Player winningHandPlayer = winningPlayerInHand();
    }

    private void apply(GameCreated event) {
        id = event.id;
        name = event.name;
        players = new ArrayList<>();
        table = new ArrayList<>();
        deck = Deck.shuffleWithSeed(id.hashCode());
        playersWhoDrawInThisTurn = 0;
        playersWhoPlayedInThisTurn = 0;
    }

    private void apply(PlayerJoined event) {
        String playerName = event.name;
        Player candidatePlayer = new Player(playerName);
        validateIsNewPlayer(candidatePlayer);
        validateNumberOfPlayers();
        players.add(candidatePlayer);
    }

    private void apply(CardDealt event) {
        Card card = new Card(event.seed, event.value);
        String playerName = event.name;
        validateExistsPlayerWithName(playerName);
        Player player = playerWithName(playerName);
        player.receive(card);
        deck = deck.remove(card);
    }

    private void apply(BriscolaSelected event) {
        seed = new Seed(event.seed);
        deck = deck.moveFirstToLast();
        firstPlayer().startPlayingTurn();
    }

    private void apply(CardPlayed event) {
        String playerName = event.name;
        validateExistsPlayerWithName(playerName);
        Player player = playerWithName(playerName);
        validateIsPlayingTurnOf(player);
        Card card = new Card(event.seed, event.value);
        validateHasCard(player, card);
        player.removeFromHand(card);
        table.add(card);
        updatePlayingTurn(player);
    }

    private void apply(CardDrawn event) {
        String playerName = event.name;
        validateExistsPlayerWithName(playerName);
        Player player = playerWithName(playerName);
        validateIsDrawingTurnOf(player);
        Card card = new Card(event.seed, event.value);
        player.receive(card);
        deck = deck.remove(card);
        updateDrawingTurn(player);
    }

    private void updatePlayingTurn(Player player) {
        playersWhoPlayedInThisTurn++;
        player.endPlayingTurn();
        if (playersWhoPlayedInThisTurn == 4) {
            firstPlayer().startDrawingTurn();
        } else {
            Player nextPlayer = playerAfter(player);
            nextPlayer.startPlayingTurn();
        }
    }

    private void updateDrawingTurn(Player player) {
        playersWhoPlayedInThisTurn++;
        player.endDrawingTurn();
        if (playersWhoDrawInThisTurn == 4) {
            firstPlayer().startPlayingTurn();
        } else {
            Player nextPlayer = playerAfter(player);
            nextPlayer.startDrawingTurn();
        }
    }

    private Player playerAfter(Player player) {
        int position = players.indexOf(player);
        return players.get((position % 3) + 1);
    }

    private void validateNumberOfPlayers() {
        if (allPlayersArePresent()) {
            throw new InvalidOperationException("This game has already 4 players");
        }
    }

    private boolean allPlayersArePresent() {
        return players.size() >= 4;
    }

    private Player playerWithName(String name) {
        return players.stream().filter(player -> player.hasName(name)).findFirst().get();
    }

    private void validateIsNewPlayer(Player player) {
        if (isInGame(player)) {
            throw new InvalidOperationException("Player with this name is already in game");
        }
    }

    private void validateExistsPlayerWithName(String name) {
        if (!isInGame(new Player(name))) {
            throw new InvalidOperationException("Player not in game");
        }
    }

    private boolean isInGame(Player player) {
        return players.contains(player);
    }

    private void validateIsPlayingTurnOf(Player player) {
        if (!player.canPlay()) {
            throw new InvalidOperationException("Player can't play during another player turn");
        }
    }

    private void validateIsDrawingTurnOf(Player player) {
        if (!player.canDraw()) {
            throw new InvalidOperationException("Player can't draw during another player turn");
        }
    }

    private void validateHasCard(Player player, Card card) {
        if (!player.hasInHand(card)) {
            throw new InvalidOperationException("Player can't play a card not in her hand");
        }
    }

    private Player firstPlayer() {
        return players.get(0);
    }

    public UUID getId() {
        return id;
    }
}
