package king_tokyo_power_up.game.card;

import java.util.ArrayList;

/**
 * The store class handles the store cards and purchasing of these cards.
 */
public class CardShop {
    /**
     * The deck containing all the store cards.
     */
    private Deck<StoreCard> deck;

    /**
     * The list of cards that monsters are able to purchase.
     */
    private StoreCard[] stock;


    /**
     * Constructs a new store and sets up the stock immediately.
     */
    public CardShop() {
        deck = DeckFactory.createStoreDeck();
        deck.shuffle();
        stock = new StoreCard[3];
        reset();
    }


    /**
     * Purchase a card from the store.
     * @param index the index to purchase
     * @return
     */
    public StoreCard purchase(int index) {
        if (index < 0 || index > 2)
            throw new IllegalArgumentException("Cannot purchase card " + index + " pick a card from 0 through 2");
        StoreCard card = stock[index];
        deck.add(card);
        stock[index] = deck.draw();
        return card;
    }


    /**
     * Peek at what the next card in the store deck is.
     * @return next store card in the deck
     */
    public StoreCard peek() {
        return deck.peek();
    }


    /**
     * Reset the store, removes the current cards in stock and
     * draws three new cards to the stock in the store.
     */
    public void reset() {
        for (int i = 0; i < 3; i++) {
            StoreCard card = deck.draw();
            deck.add(stock[i]);
            stock[i] = card;
        }
    }


    /**
     * Get the cost of a specific card the shop.
     * @param index the card index
     * @return the energy cost
     */
    public int getCost(int index) {
        if (index < 0 || index > 2)
            throw new IllegalArgumentException("Cannot check card " + index + " pick a card from 0 through 2");
        return stock[index].cost;
    }


    /**
     * Get the cost of resetting the store.
     * @return always 2
     */
    public int getResetCost() {
        return 2;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            result.append("[" + i + "] " + stock[i] + "\n");
        }
        result.append("[3] Reset the store, Cost 2⚡\n");
        result.append("[4] No thanks (or press [ENTER])!");
        return result.toString();
    }
}
