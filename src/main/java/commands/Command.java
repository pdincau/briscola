package commands;

import java.util.UUID;

public abstract class Command {

    public final UUID id;
    public final UUID gameId;

    public Command(UUID id, UUID gameId) {
        this.id = id;
        this.gameId = gameId;
    }
}