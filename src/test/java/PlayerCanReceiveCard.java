import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerCanReceiveCard {

    @Test
    public void and_put_it_in_her_hand() {
        Card card = new Card(BASTONI, ASSO);
        Player player = new Player("player");

        player.receive(card);

        assertThat(player.cards()).contains(card);
    }

    private String BASTONI = "bastoni";
    private String ASSO = "1";
}
