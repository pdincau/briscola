import com.google.common.eventbus.Subscribe;
import events.*;

public class ConsoleOutputListener {

    @Subscribe
    public void consume(GameCreated event) {
        System.out.println("Game created with name: " + event.name);
    }

    @Subscribe
    public void consume(PlayerJoined event) {
        System.out.println("Player joined with name: " + event.name);
    }

    @Subscribe
    public void consume(CardDealt event) {
        System.out.println("Player with name: " + event.name + " received card (" + event.value + " " + event.seed + ")");
    }

    @Subscribe
    public void consume(BriscolaSelected event) {
        System.out.println("Briscola selected: (" + event.value + " " + event.seed + ")");
    }
}
