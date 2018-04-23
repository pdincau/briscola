import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HandIsWon {


    @Test
    public void by_player_who_played_ace_when_all_cards_have_same_seed() {
        Hand hand = new Hand(new Seed("Bastoni"), 0);

        hand.record(new Player("One"), new Card("Denari", "1"));
        hand.record(new Player("Two"), new Card("Denari", "3"));

        assertThat("One", is(hand.turnWinnerName()));
    }

    @Test
    public void by_player_who_played_three_when_all_cards_have_same_seed_without_ace() {
        Hand hand = new Hand(new Seed("Bastoni"), 0);

        hand.record(new Player("One"), new Card("Denari", "5"));
        hand.record(new Player("Two"), new Card("Denari", "3"));

        assertThat("Two", is(hand.turnWinnerName()));
    }

    @Test
    public void by_player_who_played_highest_value_when_all_cards_have_same_seed_without_ace_or_three() {
        Hand hand = new Hand(new Seed("Bastoni"), 0);

        hand.record(new Player("One"), new Card("Denari", "5"));
        hand.record(new Player("Two"), new Card("Denari", "7"));

        assertThat("Two", is(hand.turnWinnerName()));
    }

    @Test
    public void by_player_who_played_briscola() {
        Hand hand = new Hand(new Seed("Bastoni"), 0);

        hand.record(new Player("One"), new Card("Denari", "5"));
        hand.record(new Player("Two"), new Card("Bastoni", "7"));

        assertThat("Two", is(hand.turnWinnerName()));
    }

    @Test
    public void by_first_card_played_if_cards_are_both_non_briscola() {
        Hand hand = new Hand(new Seed("Bastoni"), 0);

        hand.record(new Player("One"), new Card("Denari", "5"));
        hand.record(new Player("Two"), new Card("Bastoni", "7"));

        assertThat("Two", is(hand.turnWinnerName()));
    }
}