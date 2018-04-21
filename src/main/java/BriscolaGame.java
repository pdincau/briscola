import commands.AddPlayer;
import commands.CreateGame;
import commands.DrawCard;
import commands.PlayCard;

import java.util.UUID;

public class BriscolaGame {

    private static UUID gameId = UUID.fromString("0ce4420b-17f5-477e-b303-e2bf7528ffe4");

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
        Card card1 = firstCardOfFirstPlayer();
        commandBus.send(new PlayCard(UUID.randomUUID(), gameId, "Pietro", card1.seed, card1.value));
        Card card2 = firstCardOfSecondPlayer();
        commandBus.send(new PlayCard(UUID.randomUUID(), gameId, "Paolo", card2.seed, card2.value));
        Card card3 = firstCardOfThirdPlayer();
        commandBus.send(new PlayCard(UUID.randomUUID(), gameId, "Ivo", card3.seed, card3.value));
        Card card4 = firstCardOfFourthPlayer();
        commandBus.send(new PlayCard(UUID.randomUUID(), gameId, "Joe", card4.seed, card4.value));

        commandBus.send(new DrawCard(UUID.randomUUID(), gameId, "Pietro"));
        commandBus.send(new DrawCard(UUID.randomUUID(), gameId, "Paolo"));
        commandBus.send(new DrawCard(UUID.randomUUID(), gameId, "Ivo"));
        commandBus.send(new DrawCard(UUID.randomUUID(), gameId, "Joe"));
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

    private static Card firstCardOfThirdPlayer() {
        Deck deck = Deck.shuffleWithSeed(gameId.hashCode());
        return deck.select(7).get(6);
    }

    private static Card firstCardOfFourthPlayer() {
        Deck deck = Deck.shuffleWithSeed(gameId.hashCode());
        return deck.select(10).get(9);
    }

}
