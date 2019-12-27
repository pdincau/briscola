import events.Event;
import events.EventStream;

import java.util.List;
import java.util.UUID;

public interface EventStore {

    int APPEND_ANYWAY = -1;

    void appendToStream(UUID aggregateId, List<Event> events, int expectedVersion);

    EventStream loadEventStream(UUID aggregateId);
}
