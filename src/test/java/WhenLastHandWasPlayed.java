import commands.CloseGame;
import events.HandCompleted;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WhenLastHandWasPlayed {

    @Mock
    private CommandSender commandSender;

    @Test
    public void it_is_time_to_close_game() {
        GameCloser closer = new GameCloser(commandSender);

        closer.consume(new HandCompleted(randomUUID(), LAST_HAND_TURN_NUMBER));

        verify(commandSender).send(isA(CloseGame.class));
    }

    private static final int LAST_HAND_TURN_NUMBER = 9;
}