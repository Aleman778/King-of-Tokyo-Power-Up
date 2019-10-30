package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.card.effects.Effect;

public abstract class Card {
    /**
     * The name of this card.
     */
    protected String name;

    /**
     * The description of this card.
     * What the card actually does when triggered.
     */
    protected String description;


    /**
     * The effect of this card, can be an immediate effect,
     * passive ability etc.
     */
    protected Effect effect;



    /**
     * Creates a new abstract card.
     * @param name the name of the card
     * @param description the description of the card
     * @param effect the effect of having this card
     */
    public Card(String name, String description, Effect effect) {
        this.name = name;
        this.effect = effect;
        this.description = description;
    }


    /**
     * Checks if the card should be discarded when received.
     * @return true if discard, false otherwise
     */
    public abstract boolean discard();


    /**
     * Get the card name.
     * @return the name of the card.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the effect of this card.
     * @return the effect of this card.
     */
    public Effect getEffect() {
        return effect;
    }


    /**
     * Get the description of this card.
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
