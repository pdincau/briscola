import com.google.common.collect.ImmutableList;
import exceptions.InvalidOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Player {
    public final String name;
    private List<Card> cards;
    private Boolean playingTurn;
    private Boolean drawingTurn;

    public Player(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
        this.playingTurn = false;
        this.drawingTurn = false;
    }

    boolean hasName(String name) {
        return this.name.equals(name);
    }

    public void receive(Card card) {
        if (cards.size() >= 3) {
            throw new InvalidOperationException("Player can't have more than 3 cards");
        }
        cards.add(card);
    }

    public void removeFromHand(Card cardToRemove) {
        cards = cards.stream().filter(card -> !card.equals(cardToRemove)).collect(toList());
    }

    public List<Card> cards() {
        return ImmutableList.copyOf(cards);
    }

    public void startPlayingTurn() {
        playingTurn = true;
    }

    public void endPlayingTurn() {
        playingTurn = false;
    }

    public void startDrawingTurn() {
        drawingTurn = true;
    }

    public void endDrawingTurn() {
        drawingTurn = false;
    }

    public Boolean canPlay() {
        return playingTurn;
    }

    public Boolean canDraw() {
        return drawingTurn;
    }

    public Boolean hasInHand(Card card) {
        return cards.contains(card);
    }

    @Override
    public boolean equals(Object obj) {
        return reflectionEquals(this, obj, "cards", "playingTurn", "drawingTurn");
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
