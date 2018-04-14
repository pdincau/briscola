import exceptions.InvalidOperationException;
import org.junit.Test;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class CardCannotBePlayed {

    @Test
    public void by_player_during_another_player_turn() {
        Game game = new Game(id, gameName);
        game.addPlayer(playerName + "1");
        game.addPlayer(playerName + "2");
        game.addPlayer(playerName + "3");
        game.addPlayer(playerName + "4");
        game.dealFirstHand();

        Card firstCardOfFirstPlayer = firstCardOfFirstPlayer();

        game.playCard(playerName + "1", firstCardOfFirstPlayer);

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> game.playCard(playerName + "3", firstCardOfFirstPlayer))
                .withMessage("Player can't play during another player turn");
    }

    @Test
    public void if_it_is_not_in_player_hand() {
        Game game = new Game(id, gameName);
        game.addPlayer(playerName + "1");
        game.addPlayer(playerName + "2");
        game.addPlayer(playerName + "3");
        game.addPlayer(playerName + "4");
        game.dealFirstHand();

        Card firstCardOfSecondPlayer = firstCardOfSecondPlayer();

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> game.playCard(playerName + "1", firstCardOfSecondPlayer))
                .withMessage("Player can't play a card not in her hand");
    }

    @Test
    public void by_player_who_is_not_in_game() {
        Game game = new Game(id, gameName);
        game.addPlayer(playerName + "1");
        game.addPlayer(playerName + "2");
        game.addPlayer(playerName + "3");
        game.addPlayer(playerName + "4");
        game.dealFirstHand();

        Card firstCardOfFirstPlayer = firstCardOfFirstPlayer();

        game.playCard(playerName + "1", firstCardOfFirstPlayer);

        assertThatExceptionOfType(InvalidOperationException.class)
                .isThrownBy(() -> game.playCard(playerName + "not existing", anotherCard))
                .withMessage("Player not in game");
    }

    private Card firstCardOfFirstPlayer() {
        Deck deck = Deck.shuffleWithSeed(id.hashCode());
        return deck.select(1).get(0);
    }

    private Card firstCardOfSecondPlayer() {
        Deck deck = Deck.shuffleWithSeed(id.hashCode());
        return deck.select(6).get(3);
    }

    private UUID id = randomUUID();
    private String gameName = "a game name";
    private String playerName = "a player name";
    private Card anotherCard = new Card("Bastoni", "1");
}
