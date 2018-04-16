package commands;

import java.util.UUID;

public class DrawCard extends Command {

    public final String playerName;

    public DrawCard(UUID id, UUID gameId, String playerName) {
        super(id, gameId);
        this.playerName = playerName;
    }
}
