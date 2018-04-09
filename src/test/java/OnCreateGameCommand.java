import commands.CreateGame;
import events.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OnCreateGameCommand {

    @Mock
    private EventStore eventStreams;

    private BriscolaCommandHandlers handler;

    @Before
    public void setup() {
        handler = new BriscolaCommandHandlers(eventStreams);
    }

    @Test
    public void a_new_game_is_created() {
        CreateGame command = new CreateGame(commandId, gameId, gameName);

        handler.handle(command);

        verify(eventStreams).appendToStream(isA(UUID.class), anyListOf(Event.class), eq(-1));
    }

    private UUID commandId = randomUUID();
    private UUID gameId = randomUUID();
    private String gameName = "a name";

}