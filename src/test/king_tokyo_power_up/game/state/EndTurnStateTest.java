package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.monster.Monster;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests the victory conditions.
 * Requirement 16 - 18.
 */
public class EndTurnStateTest extends GameStateTest {

    public EndTurnState state;


    @Override
    public void init() {
        super.init();
        state = new EndTurnState();
        game.setState(state);
    }


    /**
     * [Requirement 16]
     * Assume current player reached 20 victory points.
     */
    @Test
    public void test20StarsWins() {
        game.getCurrent().changeStars(20);
        assertEquals(game.getCurrent(), state.getMonster20Stars(game));
    }


    /**
     * [Requirement 17]
     * Assume all other monsters are at out.
     */
    @Test
    public void testSoleSurvivingMonsterWins() {
        for (Monster mon : game.getMonsters(Target.OTHERS)) {
            mon.changeHealth(-10);
        }
        assertEquals(game.getCurrent(), state.getLastStandingMonster(game));
    }


    /**
     * [Requirement 18]
     * Assume you have 0 hp, you should be out of the game i.e. not playing.
     */
    @Test
    public void testMonsterHealth0Out() {
        game.getCurrent().changeHealth(-10);
        assertFalse(state.isMonsterPlaying(game.getCurrent()));
    }
}
