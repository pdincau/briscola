import com.google.common.eventbus.AsyncEventBus;
import commands.Command;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class CommandBus implements CommandSender {

    private static final ExecutorService threadPool = newCachedThreadPool();
    private final AsyncEventBus bus;

    public CommandBus(BriscolaCommandHandlers handler) {
        this.bus = new AsyncEventBus(threadPool);
        this.bus.register(handler);
    }

    public void send(Command command) {
        bus.post(command);
    }
}