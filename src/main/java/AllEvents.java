import events.Event;

import java.util.List;
import java.util.UUID;

public interface AllEvents {

    void addFor(UUID aggregateId, List<Event> events, int expectedVersion);

    List<Event> ofAggregateWithId(UUID aggregateId);
}
