package king_tokyo_power_up.game.card;

/**
 * Evolution type enum defines different types of evolution cards.
 */
public enum EvolutionType {
    /**
     * Permanent evolution is an evolution that you keep throughout the game.
     */
    PERMANENT_EVOLUTION("Permanent Evolution"),

    /**
     * Temporary evolution is an evolution that is played when received and then discarded.
     */
    TEMPORARY_EVOLUTION("Temporary Evolution"),

    /**
     * Gift evolution is an evolution that is put onto another monster, has a bad effects.
     */
    GIFT_EVOLUTION("Gift Evolution");


    /**
     * The string representation of enum.
     */
    private String string;

    /**
     * Evolution type enum constructor.
     * @param str the name of evolution type
     */
    EvolutionType(String str) {
        this.string = str;
    }

    @Override
    public String toString() {
        return string;
    }
}
