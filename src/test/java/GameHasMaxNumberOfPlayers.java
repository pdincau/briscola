import exceptions.InvalidOperationException;
import org.junit.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class GameHasMaxNumberOfPlayers {

    @Test
    public void which_is_4() {
        Game game = new Game(id, gameName);

        game.addPlayer(playerName + "1");
        game.addPlayer(playerName + "2");
        game.addPlayer(playerName + "3");
        game.addPlayer(playerName + "4");

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> game.addPlayer(playerName + "5"))
                .withMessage("This game has already 4 players");
    }

    private UUID id = randomUUID();
    private String gameName = "a game name";
    private String playerName = "a player name";

}
