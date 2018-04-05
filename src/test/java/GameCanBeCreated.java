import events.GameCreated;
import org.junit.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class GameCanBeCreated {

    @Test
    public void with_its_fields() {
        Game game = new Game(id, name);

        GameCreated gameCreated = new GameCreated(id, name);

        assertThat(game.uncomittedChanges()).contains(gameCreated);
    }

    private UUID id = randomUUID();
    private String name = "a name";

}
