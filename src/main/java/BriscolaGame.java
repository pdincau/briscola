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
        commandBus.send(new CreateGame(UUID.randomUUID(), gameId, "A Guided Game"));

        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Pietro"));
        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Paolo"));
        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Ivo"));
        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Joe"));

    }

}
