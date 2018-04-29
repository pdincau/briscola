import commands.DrawCard;
import events.*;
import helpers.EventStreams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static helpers.EventStreams.streamForGameAfterAPlayedTurn;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OnDrawCardCommand {

    @Mock
    private EventStore eventStore;

    private BriscolaCommandHandler handler;

    @Before
    public void setup() {
        handler = new BriscolaCommandHandler(eventStore);
    }

    @Test
    public void a_card_is_drawn() {
        EventStream eventStream = streamForGameAfterAPlayedTurn(gameId);
        when(eventStore.loadEventStream(gameId)).thenReturn(eventStream);

        handler.handle(new DrawCard(commandId, gameId, playerName));

        verify(eventStore).appendToStream(eq(gameId), anyListOf(Event.class), eq(eventStream.version()));
    }

    private UUID commandId = randomUUID();
    private UUID gameId = randomUUID();
    private String playerName = "Player 4";

}