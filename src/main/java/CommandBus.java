import commands.Command;
import com.google.common.eventbus.EventBus;

public class CommandBus implements CommandSender {

    private final EventBus bus;

    public CommandBus(BriscolaCommandHandler handler) {
        this.bus = new EventBus();
        this.bus.register(handler);
    }

    public void send(Command command) {
        bus.post(command);
    }
}