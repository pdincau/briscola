public class CardEvaluator {

    public static Card winningCard(Card firstCard, Card secondCard, Seed briscolaSeed) {
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
            if (firstCard.hasSeed(briscolaSeed)) {
                return firstCard;
            }
            if (secondCard.hasSeed(briscolaSeed)) {
                return secondCard;
            }
        }
        return firstCard;
    }

}