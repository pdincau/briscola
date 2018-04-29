import commands.DealFirstHand;
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
public class OnDealFirstHandCommand {

    @Mock
    private EventStore eventStore;

    private BriscolaCommandHandler handler;

    @Before
    public void setup() {
        handler = new BriscolaCommandHandler(eventStore);
    }

    @Test
    public void cards_are_given_and_briscola_is_shown() {
        when(eventStore.loadEventStream(gameId)).thenReturn(streamForGameWith4Players());

        handler.handle(new DealFirstHand(commandId, gameId));

        verify(eventStore).appendToStream(eq(gameId), anyListOf(Event.class), eq(version+5));
    }

    private EventStream streamForGameWith4Players() {
        Event event1 = new GameCreated(gameId, gameName);
        Event event2 = new PlayerJoined(gameId, "Player 1");
        Event event3 = new PlayerJoined(gameId, "Player 2");
        Event event4 = new PlayerJoined(gameId, "Player 3");
        Event event5 = new PlayerJoined(gameId, "Player 4");
        return EventStream.from(asList(
                new EventDescriptor(event1, gameId, version),
                new EventDescriptor(event2, gameId, version+1),
                new EventDescriptor(event3, gameId, version+2),
                new EventDescriptor(event4, gameId, version+3),
                new EventDescriptor(event5, gameId, version+5)
                ));
    }

    private int version = 0;
    private UUID commandId = randomUUID();
    private UUID gameId = randomUUID();
    private String gameName = "a game name";

}