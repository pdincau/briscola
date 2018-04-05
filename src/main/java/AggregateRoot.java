import events.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AggregateRoot {

    private Map<Class, Consumer> appliers = new HashMap<>();
    private final List<Event> changes = new ArrayList<>();

    public List<Event> uncomittedChanges() {
        return changes;
    }

    protected <T extends Event> void register(Class<T> event, Consumer<T> consumer) {
        appliers.put(event, consumer);
    }

    protected void applyChange(Event event) {
        mutateStateAccordingTo(event);
        changes.add(event);
    }

    private void mutateStateAccordingTo(Event event) {
        Consumer applier = appliers.getOrDefault(event.getClass(), (e) -> { });
        applier.accept(event);
    }

    protected void reapplyChange(Event event) {
        mutateStateAccordingTo(event);
    }

}
