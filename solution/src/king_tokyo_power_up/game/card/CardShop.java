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


    public CardShop() {
        deck = DeckFactory.createStoreDeck();
        deck.shuffle();
        stock = new StoreCard[3];
        reset();
    }


    public StoreCard purchase(int index) {
        if (index < 0 || index >= 3)
            throw new IllegalArgumentException("Cannot purchase card " + index + " pick a card from 0 through 2");
        StoreCard card = stock[index];
        deck.add(card);
        stock[index] = deck.draw();
        return card;
    }


    public StoreCard peek() {
        return deck.peek();
    }


    public void reset() {
        for (int i = 0; i < 3; i++) {
            StoreCard card = deck.draw();
            deck.add(stock[i]);
            stock[i] = card;
        }
    }


    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 3; i++) {
            result += "[" + i + "] " + stock[i];
        }
        result += "[3] Reset the store (cost: 2⚡ energy)";
        return result;
    }
}
