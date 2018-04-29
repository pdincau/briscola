package events;

import java.util.UUID;

public class EventDescriptor {

    public final Event event;
    public final UUID id;
    public final int version;

    public EventDescriptor(Event event, UUID id, int version) {
        this.event = event;
        this.id = id;
        this.version = version;
    }
}