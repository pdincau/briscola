import events.Event;

import java.util.List;
import java.util.UUID;

public interface EventStore {

    void appendToStream(UUID aggregateId, List<Event> events, int expectedVersion);

    List<Event> loadEventStream(UUID aggregateId);
}
