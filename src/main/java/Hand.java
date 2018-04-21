import java.util.*;

import static java.lang.Integer.parseInt;

public class Hand {

    private List<Player> whoPlayed;
    private List<Card> whatPlayed;
    private Seed seed;

    public Hand(Seed seed) {
        this.seed = seed;
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
        //TODO: forse non tiene la carta?
        Card winningCard = whatPlayed.stream().reduce((card1, card2) -> strongerCardAccordingToSeed(card1, card2)).get();
        int positionInHand = whatPlayed.indexOf(winningCard);
        Player playerWhoWonTurn = whoPlayed.get(positionInHand);
        return playerWhoWonTurn.name;
    }

    private Card strongerCardAccordingToSeed(Card firstCard, Card secondCard) {
        if (firstCard.seed.equals(secondCard.seed)) {
            if (firstCard.value.equals("1")) {
                return firstCard;
            }
            if (secondCard.value.equals("1")) {
                return secondCard;
            }
            if (firstCard.value.equals("3")) {
                return firstCard;
            }
            if (secondCard.value.equals("3")) {
                return secondCard;
            }
            return (parseInt(firstCard.value) > parseInt(secondCard.value)) ? firstCard : secondCard;
        } else {
            if (firstCard.seed.equals(seed.value)) {
                return firstCard;
            }
            if (secondCard.seed.equals(seed.value)) {
                return secondCard;
            }
        }
        return firstCard;
    }

    public Hand removeTakenCards() {
        return new Hand(seed);
    }
}
