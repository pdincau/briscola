import events.Event;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class InMemoryEventStream implements EventStore {

    private final EventPublisher publisher;

    private final Map<UUID, List<EventDescriptor>> store = new HashMap<>();

    public InMemoryEventStream(EventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void appendToStream(UUID aggregateId, List<Event> events, int expectedVersion) {
        List<EventDescriptor> eventDescriptors = store.get(aggregateId);
        if (aggregateNotFound(eventDescriptors)) {
            eventDescriptors = new ArrayList<>();
            store.put(aggregateId, eventDescriptors);
        } else if(eventDescriptors.get(eventDescriptors.size() - 1).version != expectedVersion && expectedVersion != -1) {
            throw new ConcurrencyException();
        }

        int i = expectedVersion;

        for (Event event : events) {
            i++;
            event.version = i;
            eventDescriptors.add(new EventDescriptor(event, aggregateId, i));
            publisher.publish(event);
        }
    }

    @Override
    public List<Event> loadEventStream(UUID aggregateId) {
        List<EventDescriptor> eventDescriptors = store.get(aggregateId);

        if (aggregateNotFound(eventDescriptors)) {
            throw new AggregateNotFoundException();
        }

        return descriptorsToEvents(eventDescriptors);
    }

    private boolean aggregateNotFound(List<EventDescriptor> eventDescriptors) {
        return eventDescriptors == null;
    }

    private List<Event> descriptorsToEvents(List<EventDescriptor> eventDescriptors) {
        return eventDescriptors.stream().map(descriptor -> descriptor.event).collect(toList());
    }

}