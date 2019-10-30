package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.card.effects.Effect;

public class EvolutionCard extends Card {
    /**
     * The type of evolution.
     */
    protected EvolutionType type;


    /**
     * Creates a new abstract card.
     * @param name        the name of the card
     * @param description the description of the card
     */
    public EvolutionCard(String name, String description, EvolutionType type, Effect effect) {
        super(name, description, effect);
        this.type = type;
    }


    @Override
    public boolean discard() {
        return type == EvolutionType.TEMPORARY_EVOLUTION;
    }
}
