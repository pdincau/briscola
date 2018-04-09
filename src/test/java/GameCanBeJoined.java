import events.PlayerJoined;
import org.junit.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class GameCanBeJoined {

    @Test
    public void by_up_to_4_players() {
        Game game = new Game(id, gameName);

        game.addPlayer(playerName + "1");
        game.addPlayer(playerName + "2");
        game.addPlayer(playerName + "3");
        game.addPlayer(playerName + "4");

        assertThat(game.changes())
                .filteredOn("class", PlayerJoined.class)
                .extracting("name")
                .contains(playerName + "1")
                .contains(playerName + "2")
                .contains(playerName + "3")
                .contains(playerName + "4");
    }

    private UUID id = randomUUID();
    private String gameName = "a game name";
    private String playerName = "a player name";

}
