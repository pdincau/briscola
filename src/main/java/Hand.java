import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Player> whoPlayed;
    private List<Card> whatPlayed;
    private Suit briscolaSuit;
    private int number;

    public Hand(Suit briscolaSuit, int number) {
        this.briscolaSuit = briscolaSuit;
        this.number = number;
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
        Card winningCard = whatPlayed.stream().reduce((card1, card2) -> CardEvaluator.winningCard(card1, card2, briscolaSuit)).get();
        int positionInHand = whatPlayed.indexOf(winningCard);
        Player playerWhoWonTurn = whoPlayed.get(positionInHand);
        return playerWhoWonTurn.name;
    }

    public Boolean isOneOfLastThreeHands() {
        return number > 7 && number <= 9;
    }

    public int number() {
        return number;
    }

    public static Hand nextAfter(Hand hand) {
        return new Hand(hand.briscolaSuit, hand.number+1);
    }

    public static Hand goesAt(String suit) {
        return new Hand(new Suit(suit), 0);
    }
}
