package commands;

import java.util.UUID;

public class CreateGame extends Command {

    public final String gameName;

    public CreateGame(UUID id, UUID gameId, String gameName) {
        super(id, gameId);
        this.gameName = gameName;
    }
}
