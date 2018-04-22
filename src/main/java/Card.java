import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Card {

    public final String seed; //TODO: use Seed
    public final String value;

    public Card(String seed, String value) {
        this.seed = seed;
        this.value = value;
    }

    public boolean hasSameSeedOf(Card card) {
        return seed.equals(card.seed);
    }

    public boolean hasSeed(Seed seed) {
        return this.seed.equals(seed.value);
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
