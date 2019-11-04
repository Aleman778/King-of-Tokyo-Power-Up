package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.TerminalTest;
import org.junit.Before;

public class GameStateTest {

    public Game game;

    @Before
    public void init() {
        game = new Game();
        game.current = 0;
        game.setup(3);
        for (Monster mon : game.getAllMonsters()) {
            mon.setTerminal(new TerminalTest(null));
        }
    }
}
