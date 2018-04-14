import events.*;
import exceptions.InvalidOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game extends AggregateRoot {

    private UUID id;
    private String name;
    private List<Player> players;
    private Deck deck;
    private Seed seed;

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
    }

    private void apply(CardPlayed event) {
        String playerName = event.name;
        validateExistsPlayerWithName(playerName);
        Player player = playerWithName(playerName);
        validateIsTurnOf(player);
        validatePlayerHasCard(player, new Card(event.seed, event.value));
        //TODO: here I should give the turn to next player
    }

    private void apply(CardDealt event) {
        Card card = new Card(event.seed, event.value);
        String playerName = event.name;
        validateExistsPlayerWithName(playerName);
        Player player = playerWithName(playerName);
        player.receive(card);
        deck = deck.remove(card);
    }

    private void apply(GameCreated event) {
        id = event.id;
        name = event.name;
        players = new ArrayList<>();
        deck = Deck.shuffleWithSeed(id.hashCode());
    }

    private void apply(PlayerJoined event) {
        String playerName = event.name;
        Player candidatePlayer = new Player(playerName);
        validateIsNewPlayer(candidatePlayer);
        validateNumberOfPlayers();
        players.add(candidatePlayer);
    }

    private void apply(BriscolaSelected event) {
        seed = new Seed(event.seed);
        deck = deck.moveFirstToLast();
        Player nextPlayerToPlay = players.get(0);
        nextPlayerToPlay.youAreNextToPlay();
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

    private void validateIsTurnOf(Player player) {
        if (isNotTurnOfPlayer(player)) {
            throw new InvalidOperationException("Player can't play during another player turn");
        }
    }

    private boolean isNotTurnOfPlayer(Player player) {
        return !player.canYouPlay();
    }

    private void validatePlayerHasCard(Player player, Card card) {
        if (!player.hasInHand(card)) {
            throw new InvalidOperationException("Player can't play a card not in her hand");
        }
    }

    public UUID getId() {
        return id;
    }

    public static Game from(List<Event> events) {
        Game game = new Game();
        events.forEach(game::reapplyChange);
        return game;
    }

}
