import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Card {

    public final String suit; //TODO: use Suit?
    public final String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public boolean hasSameSuitOf(Card card) {
        return suit.equals(card.suit);
    }

    public boolean hasSuit(Suit suit) {
        return this.suit.equals(suit.value);
    }

    public boolean isThree() {
        return value.equals("3");
    }

    public boolean isAce() {
        return value.equals("1");
    }

    public boolean hasValueHigherOf(Card card) {
        return parseInt(value) > parseInt(card.value);
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
