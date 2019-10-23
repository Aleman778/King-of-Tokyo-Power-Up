package king_tokyo_powerup.game;

import java.util.Random;

/**
 * The dice roll class holds multiple dice that can be rolled simultaneously
 * or rerolled on at a time, there can be any number of dice specified.
 * At the end of the dice rolls call {@link DiceRoll#getResult()} to get
 * an overview of the number
 */
public class DiceRoll {
    /**
     * The random number generator to use.
     */
    private Random random;
    /**
     * The dice to roll.
     */
    private Dice[] dice;


    /**
     * Creates a new dice roll, to initiate the roll call {@link DiceRoll#rollAll()}.
     * In order to reroll any dice call {@link DiceRoll#reroll(int)}
     * @param random the random number generator to use
     * @param numDice the number of dice in this roll
     */
    public DiceRoll(Random random, int numDice) {
        this.random = random;
        this.dice = new Dice[numDice];
    }


    /**
     * Rolls all the dice.
     */
    public void rollAll() {
        for (int i = 0; i < dice.length; i++) {
            dice[i] = Dice.roll(random);
        }
    }


    /**
     * Reroll a specific dice, can be called multiple times if
     * multiple rerolls are requested at once.
     * @param id the id of the dice starting at 0
     */
    public void reroll(int id) {
        if (id < 0 || id >= dice.length) {
            throw new IllegalArgumentException("The id " + id + " is out of range, expected 0 through " + (dice.length - 1));
        }
        dice[id] = Dice.roll(random);
    }


    /**
     * @return the number of dice in this roll
     */
    public int numDice() {
        return dice.length;
    }


    /**
     * @return the resulting stats of this dice roll
     */
    public DiceResult getResult() {
        return null;
    }


    @Override
    public String toString() {
        String diceId = "";
        String diceRolls = "";
        return diceId + "\n" + diceRolls;
    }
}
