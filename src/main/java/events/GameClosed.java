package events;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.UUID;

import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class GameClosed extends Event {

    public final String name;

    public GameClosed(UUID id, String name) {
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
