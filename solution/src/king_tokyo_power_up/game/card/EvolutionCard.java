package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.card.effects.Effect;

public class EvolutionCard extends Card {
    /**
     * The type of evolution.
     */
    protected EvolutionType type;


    /**
     * Should the card be immediately discarded?
     * Some temporary evolutions stick around for the rest of the turn.
     * You can manually discard it in the effect by using
     * {@link king_tokyo_power_up.game.monster.Monster#discardCard(Card)}
     */
    protected boolean discard;


    /**
     * Creates a new abstract card.
     * @param name        the name of the card
     * @param description the description of the card
     */
    public EvolutionCard(String name, boolean discard, EvolutionType type, String description, Effect effect) {
        super(name, description, effect);
        this.type = type;
        this.discard = discard;
    }


    @Override
    public boolean discard() {
        return discard;
    }


    @Override
    public String toString() {
        return name + ", " + type.toString() + ", " + description;
    }
}
