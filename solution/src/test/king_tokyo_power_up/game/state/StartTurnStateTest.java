package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.TerminalTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StartTurnStateTest extends GameStateTest {

    private StartTurnState state;


    /**
     * Setup the game state to be the start of new turn.
     * Assume last player has finished its turn and that the first player is in Tokyo.
     */
    @Before
    public void init() {
        super.init();
        game.current = 2;
        game.inTokyo = game.getAllMonsters().get(0);
        game.setState(new StartTurnState());
        game.update();
    }


    /**
     * [Requirement 7]
     * Tests if the monster is awarded 1 star for being
     * in Tokyo when the new turn starts.
     * Assume that the first monster already is in Tokyo.
     * The current monster should have one star after updating the game.
     */
    @Test
    public void monsterInTokyoGain1Star() {
        assertEquals(1, game.getCurrent().getStars());
    }
}
