import commands.AddPlayer;
import events.Event;
import events.GameCreated;
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
public class OnAddPlayerCommand {

    @Mock
    private EventStore eventStore;

    private BriscolaCommandHandler handler;

    @Before
    public void setup() {
        handler = new BriscolaCommandHandler(eventStore);
    }

    @Test
    public void a_new_player_is_added() {
        GameCreated event = new GameCreated(gameId, gameName);
        when(eventStore.loadEventStream(gameId)).thenReturn(streamFrom(event, gameId, version));

        handler.handle(new AddPlayer(commandId, gameId, playerName));

        verify(eventStore).appendToStream(eq(gameId), anyListOf(Event.class), eq(version));
    }

    private EventStream streamFrom(GameCreated event, UUID gameId, int version) {
        return EventStream.from(asList(new EventDescriptor(event, gameId, version)));
    }

    private int version = 0;
    private UUID commandId = randomUUID();
    private UUID gameId = randomUUID();
    private String gameName = "a game name";
    private String playerName = "a player name";

}