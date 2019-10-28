package king_tokyo_power_up.game.card;

public abstract class Card {
    /**
     * The name of this card.
     */
    private String name;

    /**
     * The description of this card.
     * What the card actually does when triggered.
     */
    private String description;

    /**
     * The effects of this cards, defines what the card
     * actually does functionally similar to the
     * the textual {@link Card#description}.
     */
    private EffectListener effect;


    /**
     * Creates a new abstract card.
     * @param name the name of the card
     * @param description the description of the card
     */
    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
