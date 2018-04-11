import exceptions.InvalidOperationException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class PlayerCannotReceiveCard {

    @Test
    public void when_hand_has_3_cards() {
        Player player = new Player("player");

        player.receive(new Card(BASTONI, ASSO));
        player.receive(new Card(DENARI, ASSO));
        player.receive(new Card(COPPE, ASSO));

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> player.receive(new Card(SPADE, ASSO)))
                .withMessage("Player can't have more than 3 cards");
    }

    private String BASTONI = "bastoni";
    private String SPADE = "bastoni";
    private String DENARI = "bastoni";
    private String COPPE = "bastoni";
    private String ASSO = "1";
}
