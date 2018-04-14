package commands;

import java.util.UUID;

public class PlayCard extends Command {

    public final String playerName;
    public final String seed;
    public final String value;

    public PlayCard(UUID id, UUID gameId, String playerName, String seed, String value) {
        super(id, gameId);
        this.playerName = playerName;
        this.seed = seed;
        this.value = value;
    }
}
