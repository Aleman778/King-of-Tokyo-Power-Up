package king_tokyo_power_up.game;

import king_tokyo_power_up.game.util.Formatting;

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
     * Creates a new dice roll, the dice are rolled directly on construction.
     * In order to reroll any dice call {@link DiceRoll#reroll(int)}
     * @param random  the random number generator to use
     * @param numDice the number of dice in this roll
     */
    public DiceRoll(Random random, int numDice) {
        this.random = random;
        this.dice = new Dice[numDice];
        rollAll();
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
     *
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
        DiceResult result = new DiceResult();
        for (Dice d : dice) {
            switch (d) {
                case ONE:
                    result.ones++;
                    break;
                case TWO:
                    result.twos++;
                    break;
                case THREE:
                    result.threes++;
                    break;
                case HEART:
                    result.hearts++;
                    break;
                case CLAW:
                    result.claws++;
                    break;
                case ENERGY:
                    result.energies++;
                    break;
            }
        }
        return result;
    }


    @Override
    public String toString() {
        String diceIds = "";
        String diceRolls = "";
        for (int i = 0; i < dice.length; i++) {
            String diceRoll = dice[i].toString();
            String diceId = "[" + (i + 1) + "]";
            int spacing = diceRoll.length() - diceId.length();
            if (spacing > 0) {
                diceId += Formatting.getSpaces(Math.abs(spacing));
            } else if (spacing < 0) {
                diceRoll += Formatting.getSpaces(Math.abs(spacing));
            }
            diceIds += diceId + " ";
            diceRolls += diceRoll + " ";
        }
        return diceIds + "\n" + diceRolls;
    }
}
