import events.Event;
import events.EventDescriptor;
import events.EventStream;
import exceptions.ConcurrencyException;

import java.util.*;

public class InMemoryEventStore implements EventStore {

    private final EventPublisher publisher;

    private final Map<UUID, List<EventDescriptor>> store = new HashMap<>();

    public InMemoryEventStore(EventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void appendToStream(UUID aggregateId, List<Event> events, int expectedVersion) {
        List<EventDescriptor> eventDescriptors = descriptorsFor(aggregateId);
        if (aggregateNotFound(eventDescriptors)) {
            store.put(aggregateId, eventDescriptors);
        } else if(conflictExists(expectedVersion, eventDescriptors)) {
            throw new ConcurrencyException();
        }

        int i = expectedVersion;

        for (Event event : events) {
            event.version = ++i;
            eventDescriptors.add(new EventDescriptor(event, aggregateId, i));
            store.put(aggregateId, eventDescriptors);
            publisher.publish(event);
        }
    }

    @Override
    public EventStream loadEventStream(UUID aggregateId) {
        List<EventDescriptor> eventDescriptors = descriptorsFor(aggregateId);

        if (aggregateNotFound(eventDescriptors)) {
            throw new AggregateNotFoundException();
        }

        return EventStream.from(eventDescriptors);
    }

    private boolean aggregateNotFound(List<EventDescriptor> eventDescriptors) {
        return eventDescriptors.isEmpty();
    }

    private List<EventDescriptor> descriptorsFor(UUID aggregateId) {
        return store.getOrDefault(aggregateId, new ArrayList<>());
    }

    private boolean conflictExists(int expectedVersion, List<EventDescriptor> eventDescriptors) {
        return eventDescriptors.get(eventDescriptors.size() - 1).version != expectedVersion && expectedVersion != -1;
    }

}