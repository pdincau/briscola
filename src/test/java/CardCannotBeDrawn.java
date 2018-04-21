import exceptions.InvalidOperationException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class CardCannotBeDrawn {

    @Test
    public void by_player_during_another_player_turn() {
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

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> game.drawCard(playerName + "2"))
                .withMessage("Player can't draw during another player turn");
    }

    @Test
    public void by_player_who_is_not_in_game() {
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

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> game.drawCard(playerName + "not existing"))
                .withMessage("Player not in game");
    }

    private List<Card> cardsToPlay() {
        List<Card> cardsToPlay = new ArrayList<>();
        Deck deck = Deck.shuffleWithSeed(id.hashCode());
        List<Card> cards = deck.select(10);
        cardsToPlay.addAll(asList(cards.get(0), cards.get(3), cards.get(6), cards.get(9)));
        return cardsToPlay;
    }

    private UUID id = randomUUID();
    private String gameName = "a game name";
    private String playerName = "a player name";
    private Card anotherCard = new Card("Bastoni", "1");
}
