package events;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class CardDealt extends Event {

    public final String name;
    public final String seed;
    public final String value;

    public CardDealt(UUID id, String name, String seed, String value) {
        super(id);
        this.name = name;
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
