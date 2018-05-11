import commands.DealFirstHand;
import events.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static helpers.EventStreams.streamForGameWith4Players;
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
        when(eventStore.loadEventStream(gameId)).thenReturn(streamForGameWith4Players(gameId));

        handler.handle(new DealFirstHand(commandId, gameId));

        verify(eventStore).appendToStream(eq(gameId), anyListOf(Event.class), eq(version+4));
    }

    private int version = 0;
    private UUID commandId = randomUUID();
    private UUID gameId = randomUUID();

}