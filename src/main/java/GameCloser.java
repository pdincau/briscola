import com.google.common.eventbus.Subscribe;
import commands.CloseGame;
import events.HandCompleted;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class GameCloser {

    private CommandSender commandSender;

    public GameCloser(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @Subscribe
    public void consume(HandCompleted event) {
        UUID gameId = event .id;
        if (lastHandIsCompleted(event)) {
            commandSender.send(new CloseGame(randomUUID(), gameId));
        }
    }

    private boolean lastHandIsCompleted(HandCompleted event) {
        return event.number == 9;
    }
}
