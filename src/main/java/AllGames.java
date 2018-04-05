import events.Event;

import java.util.List;
import java.util.UUID;

public class AllGames implements AggregateRootRepository<Game> {

    private final AllEvents allEvents;

    public AllGames(AllEvents allEvents) {
        this.allEvents = allEvents;
    }

    @Override
    public void add(Game game, int expectedVersion) {
        allEvents.addFor(game.getId(), game.uncomittedChanges(), expectedVersion);
    }

    @Override
    public Game withId(UUID id) {
        List<Event> events = allEvents.ofAggregateWithId(id);
        return Game.from(events);
    }
}
