import com.google.common.collect.ImmutableList;
import exceptions.InvalidOperationException;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Player {
    public final String name;
    private List<Card> cards;

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
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

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj, "cards");
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

}
