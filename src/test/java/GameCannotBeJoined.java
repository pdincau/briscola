import org.junit.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class GameCannotBeJoined {

    @Test
    public void twice_by_the_same_player() {
        Game game = new Game(id, gameName);

        game.addPlayer(playerName);

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> game.addPlayer(playerName))
                .withMessage("Player with this name is already in game");
    }

    private UUID id = randomUUID();
    private String gameName = "a game name";
    private String playerName = "a player name";

}
