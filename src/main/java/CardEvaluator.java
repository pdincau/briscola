public class CardEvaluator {

    public static Card winningCard(Card firstCard, Card secondCard, Suit briscolaSuit) {
        if (firstCard.hasSameSuitOf(secondCard)) {
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
            if (firstCard.hasSuit(briscolaSuit)) {
                return firstCard;
            }
            if (secondCard.hasSuit(briscolaSuit)) {
                return secondCard;
            }
        }
        return firstCard;
    }

}