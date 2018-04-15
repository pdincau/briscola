import com.google.common.collect.ImmutableList;
import exceptions.InvalidOperationException;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Player {
    public final String name;
    private List<Card> cards;
    private Boolean turn;

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.turn = false;
    }

    boolean hasName(String name) {
        return this.name.equals(name);
    }

    public void receive(Card card) {
        if (cards.size() >= 3) {
            throw new InvalidOperationException("Player can't have more than 3 cards");
        }
        cards.add(card);
    }

    public List<Card> cards() {
        return ImmutableList.copyOf(cards);
    }

    public void youAreNextToPlay() {
        turn = true;
    }

    public void youEndedYourTurn() {
        turn = false;
    }

    public Boolean canYouPlay() {
        return turn;
    }

    public Boolean hasInHand(Card card) {
        return cards.contains(card);
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj, "cards", "turn");
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
