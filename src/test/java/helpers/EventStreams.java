package helpers;

import events.*;
import java.util.UUID;
import events.EventDescriptor;

import static java.util.Arrays.asList;

public class EventStreams {

    public static EventStream streamForGameAfterAPlayedTurn(UUID gameId) {
        int version = 0;
        Event event1 = new GameCreated(gameId, "a game");
        Event event2 = new PlayerJoined(gameId, "Player 1");
        Event event3 = new PlayerJoined(gameId, "Player 2");
        Event event4 = new PlayerJoined(gameId, "Player 3");
        Event event5 = new PlayerJoined(gameId, "Player 4");
        Event event6 = new CardDealt(gameId, "Player 1", "Denari", "1");
        Event event7 = new CardDealt(gameId, "Player 1", "Denari", "2");
        Event event8 = new CardDealt(gameId, "Player 1", "Denari", "3");
        Event event9 = new CardDealt(gameId, "Player 2", "Bastoni", "1");
        Event event10 = new CardDealt(gameId, "Player 2", "Bastoni", "2");
        Event event11 = new CardDealt(gameId, "Player 2", "Bastoni", "3");
        Event event12 = new CardDealt(gameId, "Player 3", "Coppe", "1");
        Event event13 = new CardDealt(gameId, "Player 3", "Coppe", "2");
        Event event14 = new CardDealt(gameId, "Player 3", "Coppe", "3");
        Event event15 = new CardDealt(gameId, "Player 4", "Spade", "1");
        Event event16 = new CardDealt(gameId, "Player 4", "Spade", "2");
        Event event17 = new CardDealt(gameId, "Player 4", "Spade", "3");
        Event event18 = new BriscolaSelected(gameId, "Spade", "4");
        Event event19 = new CardPlayed(gameId, "Player 1", "Denari", "1");
        Event event20 = new CardPlayed(gameId, "Player 2", "Bastoni", "1");
        Event event21 = new CardPlayed(gameId, "Player 3", "Coppe", "1");
        Event event22 = new CardPlayed(gameId, "Player 4", "Spade", "1");
        Event event23 = new HandCompleted(gameId,1);
        return EventStream.from(asList(
                new EventDescriptor(event1, gameId, version),
                new EventDescriptor(event2, gameId, version+1),
                new EventDescriptor(event3, gameId, version+2),
                new EventDescriptor(event4, gameId, version+3),
                new EventDescriptor(event5, gameId, version+4),
                new EventDescriptor(event6, gameId, version+5),
                new EventDescriptor(event7, gameId, version+6),
                new EventDescriptor(event8, gameId, version+7),
                new EventDescriptor(event9, gameId, version+8),
                new EventDescriptor(event10, gameId, version+9),
                new EventDescriptor(event11, gameId, version+10),
                new EventDescriptor(event12, gameId, version+11),
                new EventDescriptor(event13, gameId, version+12),
                new EventDescriptor(event14, gameId, version+13),
                new EventDescriptor(event15, gameId, version+14),
                new EventDescriptor(event16, gameId, version+15),
                new EventDescriptor(event17, gameId, version+16),
                new EventDescriptor(event18, gameId, version+17),
                new EventDescriptor(event19, gameId, version+18),
                new EventDescriptor(event20, gameId, version+19),
                new EventDescriptor(event21, gameId, version+20),
                new EventDescriptor(event22, gameId, version+21),
                new EventDescriptor(event23, gameId, version+22)
        ));
    }

    public static EventStream streamForGameWith4Players(UUID gameId) {
        int version = 0;
        Event event1 = new GameCreated(gameId, "a game");
        Event event2 = new PlayerJoined(gameId, "Player 1");
        Event event3 = new PlayerJoined(gameId, "Player 2");
        Event event4 = new PlayerJoined(gameId, "Player 3");
        Event event5 = new PlayerJoined(gameId, "Player 4");
        Event event6 = new CardDealt(gameId, "Player 1", "Denari", "1");
        Event event7 = new CardDealt(gameId, "Player 1", "Denari", "2");
        Event event8 = new CardDealt(gameId, "Player 1", "Denari", "3");
        Event event9 = new CardDealt(gameId, "Player 2", "Bastoni", "1");
        Event event10 = new CardDealt(gameId, "Player 2", "Bastoni", "2");
        Event event11 = new CardDealt(gameId, "Player 2", "Bastoni", "3");
        Event event12 = new CardDealt(gameId, "Player 3", "Coppe", "1");
        Event event13 = new CardDealt(gameId, "Player 3", "Coppe", "2");
        Event event14 = new CardDealt(gameId, "Player 3", "Coppe", "3");
        Event event15 = new CardDealt(gameId, "Player 4", "Spade", "3");
        Event event16 = new CardDealt(gameId, "Player 4", "Spade", "3");
        Event event17 = new CardDealt(gameId, "Player 4", "Spade", "3");
        Event event18 = new BriscolaSelected(gameId, "Spade", "4");
        return EventStream.from(asList(
                new EventDescriptor(event1, gameId, version),
                new EventDescriptor(event2, gameId, version+1),
                new EventDescriptor(event3, gameId, version+2),
                new EventDescriptor(event4, gameId, version+3),
                new EventDescriptor(event5, gameId, version+4),
                new EventDescriptor(event6, gameId, version+5),
                new EventDescriptor(event7, gameId, version+6),
                new EventDescriptor(event8, gameId, version+7),
                new EventDescriptor(event9, gameId, version+8),
                new EventDescriptor(event10, gameId, version+9),
                new EventDescriptor(event11, gameId, version+10),
                new EventDescriptor(event12, gameId, version+11),
                new EventDescriptor(event13, gameId, version+12),
                new EventDescriptor(event14, gameId, version+13),
                new EventDescriptor(event15, gameId, version+14),
                new EventDescriptor(event16, gameId, version+15),
                new EventDescriptor(event17, gameId, version+16),
                new EventDescriptor(event18, gameId, version+17)
        ));
    }
}
