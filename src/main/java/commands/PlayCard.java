package commands;

import java.util.UUID;

public class PlayCard extends Command {

    public final String playerName;
    public final String suit;
    public final String value;

    public PlayCard(UUID id, UUID gameId, String playerName, String suit, String value) {
        super(id, gameId);
        this.playerName = playerName;
        this.suit = suit;
        this.value = value;
    }
}
