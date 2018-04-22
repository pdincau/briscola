import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Player> whoPlayed;
    private List<Card> whatPlayed;
    private Seed briscolaSeed;

    public Hand(Seed seed) {
        this.briscolaSeed = seed;
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
        Card winningCard = whatPlayed.stream().reduce((card1, card2) -> strongerCardAccordingToSeed(card1, card2)).get();
        int positionInHand = whatPlayed.indexOf(winningCard);
        Player playerWhoWonTurn = whoPlayed.get(positionInHand);
        return playerWhoWonTurn.name;
    }

    private Card strongerCardAccordingToSeed(Card firstCard, Card secondCard) {
        if (firstCard.hasSameSeedOf(secondCard)) {
            if (firstCard.isAce()) {
                return firstCard;
            }
            if (secondCard.isAce()) {
                return secondCard;
            }
            if (firstCard.isThree()) {
                return firstCard;
            }
            if (secondCard.isThree()) {
                return secondCard;
            }
            return firstCard.hasValueHigherOf(secondCard) ? firstCard : secondCard;
        } else {
            if (isBriscola(firstCard)) {
                return firstCard;
            }
            if (isBriscola(secondCard)) {
                return secondCard;
            }
        }
        return firstCard;
    }

    public Hand removeTakenCards() {
        return new Hand(briscolaSeed);
    }

    private boolean isBriscola(Card firstCard) {
        return firstCard.hasSeed(briscolaSeed);
    }
}
