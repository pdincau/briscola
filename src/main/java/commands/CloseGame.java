package commands;

import java.util.UUID;

public class CloseGame extends Command {

    public CloseGame(UUID id, UUID gameId) {
        super(id, gameId);
    }
}
