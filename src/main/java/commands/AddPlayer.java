package commands;

import java.util.UUID;

public class AddPlayer extends Command {

    public final String playerName;

    public AddPlayer(UUID id, UUID gameId, String playerName) {
        super(id, gameId);
        this.playerName = playerName;
    }
}
