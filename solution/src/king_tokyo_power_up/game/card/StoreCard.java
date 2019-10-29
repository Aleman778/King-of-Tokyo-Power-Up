package king_tokyo_power_up.game.card;


/**
 * CardShop cards are sold in the store and can be purchased using energy.
 * Each store card has an energy cost and can either be of type Discard or Keep.
 */
public class StoreCard extends Card {
    /**
     * The energy cost of this card
     */
    private int cost;

    /**
     * Discard flag, immediately discards retrieved
     * cards if flag is set to true, if set to false cards are kept.
     */
    private boolean discard;


    /**
     * Creates a new store card
     * @param name
     * @param cost
     * @param discard
     * @param description
     */
    public StoreCard(String name, int cost, boolean discard, String description) {
        super(name, description);
    }
}
