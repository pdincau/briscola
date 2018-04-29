import events.CardDrawn;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.UUID.fromString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class CardCanBeDrawn {

    @Test
    public void by_players_during_their_turn() {
        Game game = new Game(id, gameName);
        List<Card> cardsToPlay = cardsToPlay();
        game.addPlayer(playerName + "1");
        game.addPlayer(playerName + "2");
        game.addPlayer(playerName + "3");
        game.addPlayer(playerName + "4");
        game.dealCards();
        game.playCard(playerName + "1", cardsToPlay.get(0));
        game.playCard(playerName + "2", cardsToPlay.get(1));
        game.playCard(playerName + "3", cardsToPlay.get(2));
        game.playCard(playerName + "4", cardsToPlay.get(3));

        Card firstCardDrawn = firstCardDrawn();
        game.drawCard(playerName + "1");

        assertThat(game.changes())
                .filteredOn("class", CardDrawn.class)
                .extracting("name", "suit", "value")
                .contains(tuple(playerName + "1", firstCardDrawn.suit, firstCardDrawn.value));
    }

    private String idThatAllowsPositiveFlowDuringDrawing = "0ce4420b-17f5-477e-b303-e2bf7528ffe4";
    private UUID id = fromString(idThatAllowsPositiveFlowDuringDrawing);
    private String gameName = "a game name";
    private String playerName = "a player name";

    private List<Card> cardsToPlay() {
        List<Card> cardsToPlay = new ArrayList<>();
        Deck deck = Deck.shuffleWithSeed(id.hashCode());
        List<Card> cards = deck.select(10);
        cardsToPlay.addAll(asList(cards.get(0), cards.get(3), cards.get(6), cards.get(9)));
        return cardsToPlay;
    }

    private Card firstCardDrawn() {
        Deck deck = Deck.shuffleWithSeed(id.hashCode());
        List<Card> cards = deck.select(14);
        return cards.get(13);
    }
}
