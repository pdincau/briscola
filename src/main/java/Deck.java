import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Collections.rotate;
import static java.util.stream.Collectors.toList;

//TODO: TEST METHODS
public class Deck {

    private List<Card> cards;

    private Deck(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> select(int howMany) {
        List<Card> selectedCards = cards.stream().limit(howMany).collect(toList());
        return selectedCards;
    }

    public Deck remove(Card card) {
        List<Card> newCards = currentCardsWithout(card);
        return new Deck(newCards);
    }

    public Deck moveFirstToLast() {
        rotate(cards, -1);
        return new Deck(cards);
    }

    public static Deck shuffleWithSeed(int seed) {
        List<Card> shuffledCards = shuffledCardsWithSeed(seed);
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

    private List<Card> currentCardsWithout(Card cardToRemove) {
        return cards.stream().filter(card -> !card.equals(cardToRemove)).collect(toList());
    }

}
