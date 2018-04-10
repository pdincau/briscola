package commands;

import java.util.UUID;

public class AddPlayer extends Command {

    public final String playerName;
    public final int originalVersion;

    //TODO: REMOVE ORIGINAL VERSION FROM COMMANDS
    public AddPlayer(UUID id, UUID gameId, String playerName, int originalVersion) {
        super(id, gameId);
        this.playerName = playerName;
        this.originalVersion = originalVersion;
    }
}
