import events.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class CardCanBePlayed {

    @Test
    public void by_players_during_their_turn() {
        List<Event> history = Arrays.asList(
                new GameCreated(ID, GAME_NAME),
                new PlayerJoined(ID, PLAYER_ONE),
                new PlayerJoined(ID, PLAYER_TWO),
                new PlayerJoined(ID, PLAYER_THREE),
                new PlayerJoined(ID, PLAYER_FOUR),
                new CardDealt(ID, PLAYER_ONE, A_CARD.suit, A_CARD.value),
                new CardDealt(ID, PLAYER_ONE, "Spade", "6"),
                new CardDealt(ID, PLAYER_ONE, "Bastoni", "5"),
                new CardDealt(ID, PLAYER_TWO, ANOTHER_CARD.suit, ANOTHER_CARD.value),
                new CardDealt(ID, PLAYER_TWO, "Spade", "1"),
                new CardDealt(ID, PLAYER_TWO, "Bastoni", "3"),
                new CardDealt(ID, PLAYER_THREE, "Coppe", "5"),
                new CardDealt(ID, PLAYER_THREE, "Spade", "6"),
                new CardDealt(ID, PLAYER_THREE, "Bastoni", "7"),
                new CardDealt(ID, PLAYER_FOUR, "Denari", "5"),
                new CardDealt(ID, PLAYER_FOUR, "Bastoni", "6"),
                new CardDealt(ID, PLAYER_FOUR, "Spade", "7"),
                new BriscolaSelected(ID,  "Spade", "2")
        );

        Game game = Game.from(history);

        game.playCard(PLAYER_ONE, A_CARD);
        game.playCard(PLAYER_TWO, ANOTHER_CARD);

        assertThat(game.changes())
                .filteredOn("class", CardPlayed.class)
                .extracting("name", "suit", "value")
                .containsExactly(
                        tuple(PLAYER_ONE, A_CARD.suit, A_CARD.value),
                        tuple(PLAYER_TWO, ANOTHER_CARD.suit, ANOTHER_CARD.value)
                );
    }

    private static final UUID ID = randomUUID();
    private static final String GAME_NAME = "a game name";
    private static final String A_PLAYER_NAME = "a player name";
    private static final String PLAYER_ONE = A_PLAYER_NAME + "1";
    private static final String PLAYER_TWO = A_PLAYER_NAME + "2";
    private static final String PLAYER_THREE = A_PLAYER_NAME + "3";
    private static final String PLAYER_FOUR = A_PLAYER_NAME + "4";
    private static final Card A_CARD = new Card("Denari", "2");
    private static final Card ANOTHER_CARD = new Card("Coppe", "7");
}
