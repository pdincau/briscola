import commands.DealFirstHand;
import events.PlayerJoined;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WhenFourPlayersJoined {

    @Mock
    private CommandSender commandSender;

    @Test
    public void it_is_time_to_deal_cards() {
        FirstHandDealer dealer = new FirstHandDealer(commandSender);

        dealer.consume(new PlayerJoined(gameId, "player 1"));
        dealer.consume(new PlayerJoined(gameId, "player 2"));
        dealer.consume(new PlayerJoined(gameId, "player 3"));
        dealer.consume(new PlayerJoined(gameId, "player 4"));

        verify(commandSender).send(isA(DealFirstHand.class));
    }

    private UUID gameId = randomUUID();
}