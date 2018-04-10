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

    public Game(UUID id, String name) {
        this();
        applyChange(new GameCreated(id, name));
    }

    private Game() {
        register(GameCreated.class, this::apply);
        register(PlayerJoined.class, this::apply);
    }

    public void addPlayer(String playerName) {
        applyChange(new PlayerJoined(id, playerName));
    }

    private void apply(GameCreated event) {
        id = event.id;
        name = event.name;
        players = new ArrayList<>();
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

    private void validateIsNewPlayer(Player candidatePlayer) {
        if (isAlreadyInGame(candidatePlayer)) {
            throw new InvalidOperationException("Player with this name is already in game");
        }
    }

    private boolean isAlreadyInGame(Player candidatePlayer) {
        return players.contains(candidatePlayer);
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
