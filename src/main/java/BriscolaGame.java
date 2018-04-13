import commands.AddPlayer;
import commands.CreateGame;

import java.util.UUID;

public class BriscolaGame {
    public static void main(String[] args) {

        EventBus publisher = new EventBus(new ConsoleOutputListener());
        BriscolaCommandHandler handler = new BriscolaCommandHandler(new InMemoryEventStore(publisher));
        CommandBus commandBus = new CommandBus(handler);

        FirstHandDealer firstHandDealer = new FirstHandDealer(commandBus);
        publisher.register(firstHandDealer);

        UUID gameId = UUID.randomUUID();
        CreateGame createGame = new CreateGame(UUID.randomUUID(), gameId, "A Guided Game");
        commandBus.send(createGame);
        waitFor(1000);

        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Pietro"));
        waitFor(1000);

        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Paolo"));
        waitFor(1000);

        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Ivo"));
        waitFor(1000);

        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Joe"));

    }

    private static void waitFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
