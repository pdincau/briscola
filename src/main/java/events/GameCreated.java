package events;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.UUID;

import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class GameCreated extends Event {

    public final String name;

    public GameCreated(UUID id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

}
