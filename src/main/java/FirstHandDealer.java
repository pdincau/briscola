import com.google.common.eventbus.Subscribe;
import commands.DealFirstHand;
import events.PlayerJoined;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class FirstHandDealer {

    private CommandSender commandSender;
    private Map<UUID, Integer> store;

    public FirstHandDealer(CommandSender commandSender) {
        this.commandSender = commandSender;
        store = new HashMap<>();
    }

    @Subscribe
    public void consume(PlayerJoined event) {
        UUID gameId = event .id;
        Integer count = store.getOrDefault(gameId, 0);
        Integer newCount = count + 1;
        store.put(gameId, newCount);
        if (newCount.equals(4)) {
            commandSender.send(new DealFirstHand(randomUUID(), gameId));
        }
    }
}
