import events.CardPlayed;
import org.junit.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class CardCanBePlayed {

    @Test
    public void by_players_during_their_turn() {
        Game game = new Game(id, gameName);
        game.addPlayer(playerName + "1");
        game.addPlayer(playerName + "2");
        game.addPlayer(playerName + "3");
        game.addPlayer(playerName + "4");
        game.dealCards();

        Card aCard = firstCardOfFirstPlayer();
        Card anotherCard = firstCardOfSecondPlayer();

        game.playCard(playerName + "1", aCard);
        game.playCard(playerName + "2", anotherCard);

        assertThat(game.changes())
                .filteredOn("class", CardPlayed.class)
                .extracting("name", "seed", "value")
                .contains(tuple(playerName + "1", aCard.seed, aCard.value),
                          tuple(playerName + "2", anotherCard.seed, anotherCard.value)
                );
    }

    private UUID id = randomUUID();
    private String gameName = "a game name";
    private String playerName = "a player name";

    private Card firstCardOfFirstPlayer() {
        Deck deck = Deck.shuffleWithSeed(id.hashCode());
        return deck.select(1).get(0);
    }

    private Card firstCardOfSecondPlayer() {
        Deck deck = Deck.shuffleWithSeed(id.hashCode());
        return deck.select(6).get(3);
    }
}
