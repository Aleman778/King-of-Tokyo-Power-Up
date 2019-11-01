package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.dice.Dice;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.TerminalTest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DiceRollStateTest extends GameStateTest {

    public DiceRollState state;

    @Before
    public void init() {
        super.init();
        state = new DiceRollState();
        state.terminal = new TerminalTest(null);
        state.monster = game.getCurrent();
        game.setState(state);
    }


    /**
     * [Requirement 8]
     * Rolls all the dice and checks that all 6 dice has a value.
     * Since Dice is an enum it will start out as Null and when
     * rolling they all will get a value.
     */
    @Test
    public void rollYour6Dice() {
        state.rollAll(game);
        Dice[] dice = state.diceRoll.getDice();
        for (int i = 0; i < dice.length; i++) {
            assertNotNull(dice);
        }
    }


    /**
     * [Requirement 9 && 10]
     * Select which of you 6 dice to reroll.
     * Rerolling only specific dice will set their value,
     * and other dice should have a NULL value.
     * Note that dice array indices start from 0, the game uses index 1.
     */
    @Test
    public void rerollSelectedDice() {
        Monster monster = game.getCurrent();
        state.terminal = new TerminalTest(new String[] {"1,3,5,6"});
        state.reroll(game);
        Dice[] roll = state.diceRoll.getDice();
        assertNotNull(roll[0]);
        assertNull(roll[1]);
        assertNotNull(roll[2]);
        assertNull(roll[3]);
        assertNotNull(roll[4]);
        assertNotNull(roll[5]);
    }


    /**
     * [Requirement 11]
     * Tests the number of rerolls that are done, requirement 9 and 10 should be repeated once.
     * So one dice roll + two rerolls and that should change the state to ActionState.
     */
    @Test
    public void testRollRepetition() {
        state.terminal = new TerminalTest(new String[] {"1,2", "3,4"});
        state.update(game);
        assertNotSame(state, game.getState());
    }
}
