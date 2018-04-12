import events.CardDealt;
import events.Event;
import events.GameCreated;
import events.PlayerJoined;
import exceptions.InvalidOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game extends AggregateRoot {

    private UUID id;
    private String name;
    private List<Player> players;
    private Deck deck;

    public Game(UUID id, String name) {
        this();
        applyChange(new GameCreated(id, name));
    }

    private Game() {
        register(GameCreated.class, this::apply);
        register(PlayerJoined.class, this::apply);
        register(CardDealt.class, this::apply);
    }

    public void addPlayer(String playerName) {
        applyChange(new PlayerJoined(id, playerName));
    }

    public void dealFirstHand() {
        for(Player player: players) {
            List<Card> cards = deck.select(3);
            for(Card card: cards) {
                CardDealt event = new CardDealt(id, player.name, card.seed, card.value);
                apply(event);
            }
        }
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

    public UUID getId() {
        return id;
    }

    public static Game from(List<Event> events) {
        Game game = new Game();
        events.forEach(game::reapplyChange);
        return game;
    }
}
