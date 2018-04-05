import commands.AddPlayer;
import commands.CreateGame;

public class BriscolaCommandHandlers {

    private AggregateRootRepository<Game> allGames;

    public BriscolaCommandHandlers(AggregateRootRepository<Game> allGames) {
        this.allGames = allGames;
    }

    public void handle(CreateGame command) {
        Game game = new Game(command.gameId, command.gameName);
        allGames.add(game, -1);
    }

    public void handle(AddPlayer command) {
        Game game = allGames.withId(command.gameId);

        game.addPlayer(command.playerName);

        allGames.add(game, command.originalVersion);
    }
}
