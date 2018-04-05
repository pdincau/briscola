import commands.AddPlayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OnAddPlayerCommand {

    @Mock
    private AggregateRootRepository<Game> repository;

    private BriscolaCommandHandlers handler;

    @Before
    public void setup() {
        handler = new BriscolaCommandHandlers(repository);
    }

    @Test
    public void a_new_player_is_added() {
        Game createdGame = new Game(gameId, gameName);
        when(repository.withId(gameId)).thenReturn(createdGame);

        handler.handle(new AddPlayer(commandId, gameId, playerName, 1));

        verify(repository).add(isA(Game.class), eq(1));
    }

    private UUID commandId = randomUUID();
    private UUID gameId = randomUUID();
    private String gameName = "a game name";
    private String playerName = "a player name";

}