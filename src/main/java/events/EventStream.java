package events;

import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public class EventStream {

    private List<Event> events;
    private int version;

    private EventStream(List<Event> events, int version) {
        this.events = events;
        this.version = version;
    }

    public static EventStream from(List<EventDescriptor> descriptors) {
        List<Event> events = descriptors.stream().map(descriptor -> descriptor.event).collect(toList());

        EventDescriptor lastDescriptor = lastDescriptor(descriptors);

        return new EventStream(events, lastDescriptor.version);
    }

    private static EventDescriptor lastDescriptor(List<EventDescriptor> descriptors) {
        EventDescriptor nullDescriptor = new EventDescriptor(null, null, -1);
        return descriptors.stream().reduce((first, second) -> second).orElse(nullDescriptor);
    }

    public List<Event> events() {
        return unmodifiableList(events);
    }

    public int version() {
        return version;
    }

}
