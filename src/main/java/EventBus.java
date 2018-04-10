import com.google.common.eventbus.AsyncEventBus;
import events.Event;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class EventBus implements EventPublisher {

    private static final ExecutorService threadPool = newCachedThreadPool();
    private final AsyncEventBus bus;

    public EventBus(Object ... listeners) {
        this.bus = new AsyncEventBus(threadPool);
        for (Object l: listeners) {
            bus.register(l);
        }
    }

    @Override
    public void publish(Event event) {
        bus.post(event);
    }
}