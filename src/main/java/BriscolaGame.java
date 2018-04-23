import commands.AddPlayer;
import commands.CreateGame;
import commands.DrawCard;
import commands.PlayCard;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class BriscolaGame {

    private static UUID gameId = UUID.fromString("0ce4420b-17f5-477e-b303-e2bf7528ffe4");

    public static void main(String[] args) {
        EventBus publisher = new EventBus(new ConsoleOutputListener());
        BriscolaCommandHandler handler = new BriscolaCommandHandler(new InMemoryEventStore(publisher));
        CommandBus commandBus = new CommandBus(handler);

        FirstHandDealer firstHandDealer = new FirstHandDealer(commandBus);
        publisher.register(firstHandDealer);

        commandBus.send(new CreateGame(randomUUID(), gameId, "A Guided Game"));

        commandBus.send(new AddPlayer(randomUUID(), gameId, "Pietro"));
        commandBus.send(new AddPlayer(randomUUID(), gameId, "Paolo"));
        commandBus.send(new AddPlayer(randomUUID(), gameId, "Ivo"));
        commandBus.send(new AddPlayer(randomUUID(), gameId, "Joe"));

        waitForSeconds(1);
        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Coppe", "1"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Bastoni", "2"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Denari", "10"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Denari", "1"));

        commandBus.send(new DrawCard(randomUUID(), gameId, "Pietro"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Paolo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Ivo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Joe"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Denari", "9"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Spade", "7"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Bastoni", "1"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Denari", "8"));

        commandBus.send(new DrawCard(randomUUID(), gameId, "Pietro"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Paolo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Ivo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Joe"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Denari", "4"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Denari", "6"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Spade", "2"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Spade", "1"));

        commandBus.send(new DrawCard(randomUUID(), gameId, "Paolo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Ivo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Joe"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Pietro"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Bastoni", "10"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Coppe", "8"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Coppe", "10"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Bastoni", "6"));

        commandBus.send(new DrawCard(randomUUID(), gameId, "Joe"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Pietro"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Paolo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Ivo"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Bastoni", "7"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Spade", "4"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Spade", "9"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Bastoni", "8"));

        commandBus.send(new DrawCard(randomUUID(), gameId, "Ivo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Joe"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Pietro"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Paolo"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Bastoni", "5"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Spade", "6"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Spade", "8"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Coppe", "6"));

        commandBus.send(new DrawCard(randomUUID(), gameId, "Paolo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Ivo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Joe"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Pietro"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Spade", "3"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Denari", "3"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Coppe", "7"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Coppe", "5"));

        commandBus.send(new DrawCard(randomUUID(), gameId, "Joe"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Pietro"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Paolo"));
        commandBus.send(new DrawCard(randomUUID(), gameId, "Ivo"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Denari", "5"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Denari", "7"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Bastoni", "4"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Coppe", "2"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Spade", "5"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Bastoni", "9"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Bastoni", "3"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Coppe", "9"));

        commandBus.send(new PlayCard(randomUUID(), gameId, "Paolo", "Denari", "2"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Ivo", "Coppe", "3"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Joe", "Coppe", "4"));
        commandBus.send(new PlayCard(randomUUID(), gameId, "Pietro", "Spade", "10"));
    }

    private static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
