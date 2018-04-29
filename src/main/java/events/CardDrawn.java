package events;

import java.util.UUID;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class CardDrawn extends Event {

    public final String name;
    public final String suit;
    public final String value;

    public CardDrawn(UUID id, String name, String suit, String value) {
        super(id);
        this.name = name;
        this.suit = suit;
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
