package king_tokyo_power_up.game.card;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The deck class holds a list of cards and works similar to a stack.
 * New cards are pushed on to the deck and drawing or peeking cards are done from the top of the deck.
 * @param <T> the type of cards in the deck, can be store cards, evolution cards or both
 */
public class Deck<T extends Card> {
    /**
     * List of all cards in this deck.
     */
    private ArrayList<T> cards;


    /**
     * Constructs a new empty card deck.
     */
    public Deck() {
        cards = new ArrayList<>();
    }


    /**
     * Add a new card on the list.
     * @param card the card to add
     */
    public void add(T card) {
        cards.add(card);
    }


    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }


    /**
     * Draw a card from the deck.
     * This card is removed from the deck.
     * @return return the drawn card
     */
    public T draw() {
        T card = cards.get(0);
        cards.remove(0);
        return card;
    }


    /**
     * Peek at the top card in the deck.
     * This does not remove the card from the deck.
     * @return returned the peeked card.
     */
    public T peek() {
        return cards.get(0);
    }
}
