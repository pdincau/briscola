import events.*;
import exceptions.InvalidOperationException;

import java.util.*;

public class Game extends AggregateRoot {

    private static final int NUMBER_OF_PLAYERS_IN_GAME = 4;
    private UUID id;
    private String name;
    private List<Player> players;
    private Deck deck;
    private Hand hand;
    private WonCards firstTeamTake;
    private WonCards secondTeamTake;
    private Player playerWinningTurn;
    private Integer playersWhoPlayedInThisTurn;
    private Integer playersWhoDrewInThisTurn;

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
        register(GameClosed.class, this::apply);
    }

    public void addPlayer(String playerName) {
        applyChange(new PlayerJoined(id, playerName));
    }

    public void dealCards() {
        for(Player player: players) {
            List<Card> cards = deck.select(3);
            for(Card card: cards) {
                CardDealt event = new CardDealt(id, player.name, card.suit, card.value);
                applyChange(event);
            }
        }
        Card briscola = deck.select(1).get(0);
        BriscolaSelected event = new BriscolaSelected(id, briscola.suit, briscola.value);
        applyChange(event);
    }

    public void playCard(String playerName, Card card) {
        applyChange(new CardPlayed(id, playerName, card.suit, card.value));
        if (hand.isCompleted()) {
            applyChange(new HandCompleted(id, hand.number()));
        }
    }

    public void drawCard(String playerName) {
        Card drawnCard = deck.select(1).get(0);
        applyChange(new CardDrawn(id, playerName, drawnCard.suit, drawnCard.value));
    }

    public void close() {
        applyChange(new GameClosed(id));
    }

    private void apply(GameCreated event) {
        id = event.id;
        name = event.name;
        players = new ArrayList<>();
        firstTeamTake = WonCards.none();
        secondTeamTake = WonCards.none();
        deck = Deck.shuffleWithSeed(id.hashCode());
        playersWhoDrewInThisTurn = 0;
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
        Card card = new Card(event.suit, event.value);
        String playerName = event.name;
        validateExistsPlayerWithName(playerName);
        Player player = playerWithName(playerName);
        player.receive(card);
        deck = deck.remove(card);
    }

    private void apply(BriscolaSelected event) {
        deck = deck.moveFirstToLast();
        hand = new Hand(new Suit(event.suit), 0);
        firstPlayer().startPlayingTurn();
    }

    private void apply(CardPlayed event) {
        String playerName = event.name;
        validateExistsPlayerWithName(playerName);
        Player player = playerWithName(playerName);
        validateIsPlayingTurnOf(player);
        Card card = new Card(event.suit, event.value);
        validateHasCard(player, card);
        player.removeFromHand(card);
        hand.record(player, card);
        updatePlayingTurn(player);
    }

    private void apply(CardDrawn event) {
        String playerName = event.name;
        validateExistsPlayerWithName(playerName);
        Player player = playerWithName(playerName);
        validateIsDrawingTurnOf(player);
        Card card = new Card(event.suit, event.value);
        player.receive(card);
        deck = deck.remove(card);
        updateDrawingTurn(player);
    }

    private void apply(HandCompleted event) {
        playerWinningTurn = playerWithName(hand.turnWinnerName());
        giveWonCardToTeamOfPlayer(playerWinningTurn);
        hand = hand.next();
        playersWhoPlayedInThisTurn = 0;
        if (hand.isOneOfLastThree()) {
            playerWinningTurn.startPlayingTurn();
        } else {
            playerWinningTurn.startDrawingTurn();
        }
    }

    private void apply(GameClosed event) {
        playerWinningTurn.endPlayingTurn();
    }

    private void updatePlayingTurn(Player player) {
        playersWhoPlayedInThisTurn++;
        player.endPlayingTurn();
        if (playersWhoPlayedInThisTurn < NUMBER_OF_PLAYERS_IN_GAME) {
            Player nextPlayer = playerAfter(player);
            nextPlayer.startPlayingTurn();
        }
    }

    private void updateDrawingTurn(Player player) {
        playersWhoDrewInThisTurn++;
        player.endDrawingTurn();
        if (allPlayerDrew()) {
            playersWhoDrewInThisTurn = 0;
            playerWinningTurn.startPlayingTurn();
        } else {
            Player nextPlayer = playerAfter(player);
            nextPlayer.startDrawingTurn();
        }
    }

    private void validateNumberOfPlayers() {
        if (allPlayingSlotsAreTaken()) {
            throw new InvalidOperationException("This game has already 4 players");
        }
    }

    private boolean allPlayingSlotsAreTaken() {
        return players.size() >= NUMBER_OF_PLAYERS_IN_GAME;
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

    private boolean allPlayerDrew() {
        return playersWhoDrewInThisTurn == NUMBER_OF_PLAYERS_IN_GAME;
    }

    private Player playerAfter(Player player) {
        int position = positionInTurnOfPlayer(player);
        if (position == 3) {
            return firstPlayer();
        } else {
            return players.get(position + 1);
        }
    }

    private int positionInTurnOfPlayer(Player player) {
        return players.indexOf(player);
    }

    private void giveWonCardToTeamOfPlayer(Player player) {
        List<Card> cardsPlayedDuringHand = hand.playedCards();
        if (isInFirstTeam(player)) {
            firstTeamTake.add(cardsPlayedDuringHand);
        } else {
            secondTeamTake.add(cardsPlayedDuringHand);
        }
    }

    private boolean isInFirstTeam(Player player) {
        return positionInTurnOfPlayer(player) % 2 == 0;
    }

    public UUID getId() {
        return id;
    }
}
