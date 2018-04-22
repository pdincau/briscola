import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Player> whoPlayed;
    private List<Card> whatPlayed;
    private Seed briscolaSeed;

    public Hand(Seed briscolaSeed) {
        this.briscolaSeed = briscolaSeed;
        this.whoPlayed = new ArrayList<>();
        this.whatPlayed = new ArrayList<>();
    }

    public Boolean isCompleted() {
        return whatPlayed.size() == 4;
    }

    public void record(Player player, Card card) {
        whatPlayed.add(card);
        whoPlayed.add(player);
    }

    public List<Card> playedCards() {
        return whatPlayed;
    }

    public String turnWinnerName() {
        Card winningCard = whatPlayed.stream().reduce((card1, card2) -> CardEvaluator.winningCard(card1, card2, briscolaSeed)).get();
        int positionInHand = whatPlayed.indexOf(winningCard);
        Player playerWhoWonTurn = whoPlayed.get(positionInHand);
        return playerWhoWonTurn.name;
    }

    public Hand removeTakenCards() {
        return new Hand(briscolaSeed);
    }
}
