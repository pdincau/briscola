import commands.AddPlayer;
import commands.CreateGame;
import commands.PlayCard;

import java.util.UUID;

public class BriscolaGame {

    private static UUID gameId = UUID.randomUUID();

    public static void main(String[] args) {

        EventBus publisher = new EventBus(new ConsoleOutputListener());
        BriscolaCommandHandler handler = new BriscolaCommandHandler(new InMemoryEventStore(publisher));
        CommandBus commandBus = new CommandBus(handler);

        FirstHandDealer firstHandDealer = new FirstHandDealer(commandBus);
        publisher.register(firstHandDealer);

        commandBus.send(new CreateGame(UUID.randomUUID(), gameId, "A Guided Game"));

        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Pietro"));
        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Paolo"));
        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Ivo"));
        commandBus.send(new AddPlayer(UUID.randomUUID(), gameId, "Joe"));


        waitForSeconds(1);
        Card card = firstCardOfFirstPlayer();
        commandBus.send(new PlayCard(UUID.randomUUID(), gameId, "Pietro", card.seed, card.value));
        Card anotherCard = firstCardOfSecondPlayer();
        commandBus.send(new PlayCard(UUID.randomUUID(), gameId, "Paolo", anotherCard.seed, anotherCard.value));
    }

    private static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Card firstCardOfFirstPlayer() {
        Deck deck = Deck.shuffleWithSeed(gameId.hashCode());
        return deck.select(1).get(0);
    }

    private static Card firstCardOfSecondPlayer() {
        Deck deck = Deck.shuffleWithSeed(gameId.hashCode());
        return deck.select(4).get(3);
    }

}
