package king_tokyo_powerup.game;

import java.util.Random;


/**
 * Dice enum defines the 6 different sides of the dice.
 * It can either be a
 */
public enum Dice {
    /**
     * Dice with side ONE, roll three of these to earn one
     * star and +1 star for each additional ONE Dice.
     */
    ONE,

    /**
     * Dice side TWO, roll three of these to earn two
     * stars and +1 star for each additional TWO Dice.
     */
    TWO,

    /**
     * Dice side THREE, roll three of these to earn three
     * stars and +1 star for each additional THREE Dice.
     */
    THREE,

    /**
     * HEART dice side, roll three of these to draw
     * an evolution card from your monsters deck.
     */
    HEART,

    /**
     * CLAW dice side, deals one damage to other monsters.
     */
    CLAW,

    /**
     * ENERGY dice side, gives you one energy.
     */
    ENERGY;


    /**
     * Rolls a dice using the provided random number generator.
     *
     * @param random the random number generator to use
     * @return a dice with a random result
     */
    public static Dice roll(Random random) {
        switch (random.nextInt(6)) {
            case 0:
                return ONE;
            case 1:
                return TWO;
            case 2:
                return THREE;
            case 3:
                return HEART;
            case 4:
                return CLAW;
            case 5:
                return ENERGY;
        }
        throw new IllegalStateException("The randomized dice id was not a valid number.");
    }


    /**
     * Converts the dice name to string e.g. {@link Dice#HEART} returns [HEART].
     * @return the name of the dice in square brackets.
     */
    public String toString() {
        return "[" + name() + "]";
    }
}