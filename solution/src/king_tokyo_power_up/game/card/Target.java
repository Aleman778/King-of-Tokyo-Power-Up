package king_tokyo_power_up.game.card;

public enum Target {
    /**
     * Effects your self.
     */
    SELF,
    /**
     * Effects only other monsters.
     */
    OTHERS,

    /**
     * Effects everyone.
     */
    ALL,

    /**
     * Effects monsters in Tokyo
     */
    IN_TOKYO,

    /**
     * Effects monsters outside Tokyo.
     */
    OUTSIDE_TOKYO,
}
