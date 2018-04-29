import commands.PlayCard;
import events.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OnPlayCardCommand {

    @Mock
    private EventStore eventStore;

    private BriscolaCommandHandler handler;

    @Before
    public void setup() {
        handler = new BriscolaCommandHandler(eventStore);
    }

    @Test
    public void a_card_is_played() {
        when(eventStore.loadEventStream(gameId)).thenReturn(streamForGameWith4Players());

        handler.handle(new PlayCard(commandId, gameId, playerName, suit, value));

        verify(eventStore).appendToStream(eq(gameId), anyListOf(Event.class), eq(version+17));
    }

    private EventStream streamForGameWith4Players() {
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

    private int version = 0;
    private UUID commandId = randomUUID();
    private UUID gameId = randomUUID();
    private String playerName = "Player 1";
    private String suit = "Denari";
    private String value = "1";

}