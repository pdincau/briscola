import com.google.common.eventbus.Subscribe;
import commands.*;

public class BriscolaCommandHandler {

    private EventStore eventStore;

    public BriscolaCommandHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Subscribe
    public void handle(CreateGame command) {
        Game game = new Game(command.gameId, command.gameName);

        eventStore.appendToStream(game.getId(), game.changes(), -1);
    }

    @Subscribe
    public void handle(AddPlayer command) {
        EventStream stream = eventStore.loadEventStream(command.gameId);
        Game game = Game.from(stream.events());

        game.addPlayer(command.playerName);
        //TODO: maybe we don't need game.getId()
        eventStore.appendToStream(game.getId(), game.changes(), stream.version());
    }

    @Subscribe
    public void handle(DealFirstHand command) {
        EventStream stream = eventStore.loadEventStream(command.gameId);
        Game game = Game.from(stream.events());

        game.dealCards();
        //TODO: maybe we don't need game.getId()
        eventStore.appendToStream(game.getId(), game.changes(), stream.version());
    }

    @Subscribe
    public void handle(PlayCard command) {
        EventStream stream = eventStore.loadEventStream(command.gameId);
        Game game = Game.from(stream.events());

        game.playCard(command.playerName, new Card(command.seed, command.value));
        //TODO: maybe we don't need game.getId()
        eventStore.appendToStream(game.getId(), game.changes(), stream.version());
    }

    @Subscribe
    public void handle(DrawCard command) {
        EventStream stream = eventStore.loadEventStream(command.gameId);
        Game game = Game.from(stream.events());

        game.drawCard(command.playerName);
        //TODO: maybe we don't need game.getId()
        eventStore.appendToStream(game.getId(), game.changes(), stream.version());
    }
}

