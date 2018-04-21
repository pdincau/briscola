import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Card {

    public final String seed; //TODO: use Seed
    public final String value;

    public Card(String seed, String value) {
        this.seed = seed;
        this.value = value;
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
