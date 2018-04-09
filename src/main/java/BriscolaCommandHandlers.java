import commands.AddPlayer;
import commands.CreateGame;
import events.Event;

import java.util.List;

public class BriscolaCommandHandlers {

    private EventStore eventStore;

    public BriscolaCommandHandlers(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void handle(CreateGame command) {
        Game game = new Game(command.gameId, command.gameName);

        eventStore.appendToStream(game.getId(), game.changes(), -1);
    }

    public void handle(AddPlayer command) {
        List<Event> events = eventStore.loadEventStream(command.gameId);
        Game game = Game.from(events);

        game.addPlayer(command.playerName);

        eventStore.appendToStream(game.getId(), game.changes(), command.originalVersion);
    }
}
