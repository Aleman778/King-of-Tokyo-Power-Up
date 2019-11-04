package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.dice.DiceResult;

import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.TerminalTest;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * [Requirement 12]
 * Broken up into smaller tests for each step.
 */
public class ActionStateTest extends GameStateTest {

    @Override
    public void init() {
        super.init();
    }


    @Test
    public void testVictoryPoints() {
        DiceResult res = new DiceResult();
        res.ones = 5; // 1 + 2 = 3
        res.twos = 4; // 2 + 1 = 3
        res.threes = 6; // 3 + 3 = 6 Total of 3 + 3 + 6 = 12 Stars.
        game.setState(new ActionState(res));
        game.update();
        assertEquals(12, game.getCurrent().getStars());
    }


    /**
     * Outside tokyo should heal
     */
    @Test
    public void testHealing() {
        Monster monster = game.getCurrent();
        monster.changeHealth(-6);
        DiceResult res = new DiceResult();
        res.hearts = 20;
        // Outside Tokyo - healing 2
        game.setState(new ActionState(res));
        game.update();
        assertEquals(10, game.getCurrent().getHealth());
    }

    /**
     * Inside tokyo should not heal.
     */
    @Test
    public void testHealingInTokyo() {
        Monster monster = game.getCurrent();
        monster.changeHealth(-6);
        DiceResult res = new DiceResult();
        res.hearts = 20;
        // Inside Tokyo - no healing
        game.inTokyo = monster;
        game.setState(new ActionState(res));
        game.update();
        assertEquals(4, game.getCurrent().getHealth());
    }


    /**
     * Test if monster is evolvoing
     */
    @Test
    public void testEvolution() {
        Monster monster = game.getCurrent();
        monster.changeHealth(-6);
        DiceResult res = new DiceResult();
        res.hearts = 20;
        ActionState state = new ActionState(res);
        game.setState(state);
        assertTrue(state.isEvolving());
    }


    /**
     * Tokyo is unoccupied so monster will move into it.
     */
    @Test
    public void testAttackTokyoUnoccupied() {
        DiceResult res = new DiceResult();
        res.claws = 1;
        ActionState state = setupState(res);
        state.attackAction();
        assertEquals(game.getCurrent(), game.inTokyo);
    }


    /**
     * Assume the second player is inside tokyo and will be targeted.
     */
    @Test
    public void testAttackOutsideTokyoTargetLeaves() {
        Monster target = game.getAllMonsters().get(1);
        target.setTerminal(new TerminalTest(new String[]{"Yes"})); //Leave tokyo
        game.inTokyo = target;
        DiceResult res = new DiceResult();
        res.claws = 3;
        ActionState state = setupState(res);
        state.attackAction();
        assertEquals(7, target.getHealth());
        assertEquals(game.getCurrent(), game.inTokyo); // Current player will move into tokyo
        assertEquals(1, game.getCurrent().getStars()); // Also gains +1 star
    }


    /**
     * Assume the second player is inside tokyo and will be targeted.
     */
    @Test
    public void testAttackOutsideTokyoTargetStays() {
        Monster target = game.getAllMonsters().get(1);
        target.setTerminal(new TerminalTest(new String[]{"No"})); //Stay in tokyo
        game.inTokyo = target;
        DiceResult res = new DiceResult();
        res.claws = 3;
        ActionState state = setupState(res);
        state.attackAction();
        assertEquals(7, target.getHealth());
        assertEquals(target, game.inTokyo); // Current player will move into tokyo
        assertEquals(0, game.getCurrent().getStars()); // Also gains +1 star
    }


    /**
     * Attack inside tokyo, should target everyone outside tokyo.
     * Assume that the current player is in tokyo.
     */
    @Test
    public void testAttackInsideTokyo() {
        game.inTokyo = game.getCurrent();
        DiceResult res = new DiceResult();
        res.claws = 5;
        ActionState state = setupState(res);
        state.attackAction();
        for (Monster mon : game.getMonsters(Target.OUTSIDE_TOKYO)) {
            assertEquals(5, mon.getHealth());
        }
    }


    /**
     * Simplify setting up the state
     * @param res the dice result
     * @return the state
     */
    private ActionState setupState(DiceResult res) {
        ActionState state = new ActionState(res);
        state.game = game;
        state.monster = game.getCurrent();
        state.terminal = state.monster.getTerminal();
        return state;
    }
}
