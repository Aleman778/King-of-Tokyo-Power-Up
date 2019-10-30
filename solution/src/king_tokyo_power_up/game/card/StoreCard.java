package king_tokyo_power_up.game.card;


/**
 * CardShop cards are sold in the store and can be purchased using energy.
 * Each store card has an energy cost and can either be of type Discard or Keep.
 */
public class StoreCard extends Card {
    /**
     * The energy cost of this card
     */
    public int cost;

    /**
     * Discard flag, immediately discards retrieved
     * cards if flag is set to true, if set to false cards are kept.
     */
    public boolean discard;


    /**
     * Creates a new store card
     * @param name the name of the card
     * @param cost the cost of the card
     * @param discard the type of card, true is discard, false is keep
     * @param description the description of what the card does
     */
    public StoreCard(String name, int cost, boolean discard, String description) {
        super(name, description);
        this.cost = cost;
        this.discard = discard;
    }


    /**
     * Returns a string with the card information.
     * @return string containing the card info
     */
    @Override
    public String toString() {
        String type = discard ? "DISCARD" : "KEEP";
        return name + ", Cost " + cost + "⚡, " + type + ", " + description;
    }
}
