import java.util.*;

import static java.util.Arrays.asList;

public class Deck {

    List<Card> cards;

    private Deck(List<Card> cards) {
        this.cards = cards;
    }

    public static Deck shuffleWith(UUID id) {
        List<Card> shuffledCards = shuffledCardsWithSeed(id.hashCode());
        return new Deck(shuffledCards);
    }

    private static List<Card> shuffledCardsWithSeed(int seed) {
        List<Card> cards = generateCards();
        Collections.shuffle(cards, new Random(seed));
        return cards;
    }

    private static List<Card> generateCards() {
        List<Card> cards = new ArrayList<>();
        List<String> seeds = asList("Denari", "Spade", "Bastoni", "Coppe");
        List<String> values = asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        for (String seed: seeds) {
            for (String value: values) {
                cards.add(new Card(seed, value));
            }
        }
        return cards;
    }

}
