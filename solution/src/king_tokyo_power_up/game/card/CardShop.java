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
    private ArrayList<StoreCard> stock;


    public CardShop() {
        deck = DeckFactory.createStoreDeck();
        deck.shuffle();
        stock = new ArrayList<>();
        reset();
    }


    public void purchase(int index) {

    }


    public StoreCard peek() {
        return deck.peek():
    }

    
    public void reset() {
        stock.clear();
        for (int i = 0; i < 3; i++) {
            StoreCard card = deck.draw();
            stock.add(card);
            deck.add(card);
        }
    }


    @Override
    public String toString() {

    }
}
