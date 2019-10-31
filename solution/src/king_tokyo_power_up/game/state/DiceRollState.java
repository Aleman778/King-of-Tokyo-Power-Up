package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.event.EventType;
import king_tokyo_power_up.game.dice.DiceResult;
import king_tokyo_power_up.game.dice.DiceRoll;
import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;
import java.util.Random;

/**
 * The dice roll state is where the current player rolls the dice and
 * is asked to reroll twice possibly more if an effect changes this.
 * This state is responsible for the requirements 8 through 11.
 */
public class DiceRollState implements GameState {
    /**
     * The number of rerolls left to use.
     * Default is 2.
     */
    public int rerolls = 2;

    /**
     * The number of dice in this rolls.
     * Default is 6.
     */
    public int numDice = 6;

    /**
     * The dice roll object containing all the dice.
     */
    public DiceRoll diceRoll;

    /**
     * Terminal used to communicate with the monster who is rolling.
     */
    private Terminal terminal;


    /**
     * The dice roll state.
     */
    public DiceRollState() {
        diceRoll = new DiceRoll(new Random(), numDice);
    }


    /**
     * The update method for rolling the dice:
     * 1. [Requirement 8] Roll your 6 dice (effects can alter the number of dice)
     * 2. [Requirement 9] Select which of your 6 dice to reroll (can also skip step 3 and 4)
     * 3. [Requirement 10] Reroll the selected dice
     * 4. [Requirement 11] Repeat step 2 and 3 once (effect can alter the number or rerolls)
     * 3. Displays the result of the dice roll.
     * @param game the state of the game
     */
    @Override
    public void update(Game game) {
        diceRoll.rollAll();

        Monster monster = game.getCurrent();
        monster.notify(game, EventType.DICE_ROLL);

        terminal = monster.getTerminal();
        terminal.writeString(getControllsString());

        while (rerolls > 0) {
            reroll(game);
            monster.notify(game, EventType.DICE_REROLL);
        }

        DiceResult result = diceRoll.getResult();
        terminal.writeString(result.toString() + "\n");
        game.setState(new ActionState(result));
    }


    /**
     * Rerolls the dice, returns true if the monster wants
     * to skip rerolling.
     * @param game the state of the game
     * @return true for skipping rerolling.
     */
    public void reroll(Game game) {
        try {
            terminal.writeString(toString(game));
            terminal.writeString("QUERY:DICE_REROLL\n");
            int[] reroll = terminal.readIntArray(1, numDice, new int[0], "Not a valid list of rerolls, enter e.g. 3, 1, 5!\nQUERY:REROLL\n");
            if (reroll.length == 0) {
                rerolls = 0;
                return;
            }
            for (int i : reroll) {
                diceRoll.reroll(i - 1);
            }
            rerolls--;
        } catch (IOException e) {
            e.printStackTrace();
            game.exit();
        }
    }

    /**
     * Displays the constrolls
     * @return
     */
    public String getControllsString() {
        return "\n\nEnter the numbers of the dice you wish to reroll or empty to skip, e.g. 3, 5, 1\n";
    }


    /**
     * Returns the status of the rolled dice.
     * @param game the state of the game
     * @return string containing which dice was rolled.
     */
    @Override
    public String toString(Game game) {
        String plural = rerolls == 1 ? "" : "s";
        return "You rolled (you can reroll " + rerolls + " more time" + plural +
                "):\n" + diceRoll.toString() + "\n";
    }
}
