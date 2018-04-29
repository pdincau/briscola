import java.util.ArrayList;
import java.util.List;

public class WonCards {

    private final List<Card> cards;

    private WonCards(List<Card> cards) {
        this.cards = cards;
    }

    public static WonCards none() {
        return new WonCards(new ArrayList<>());
    }

    public void add(List<Card> wonCards) {
        this.cards.addAll(wonCards);
    }
}
