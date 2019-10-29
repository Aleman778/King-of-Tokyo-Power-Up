package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;

public class StartTurnState implements GameState {
    /**
     * Progresses to the next turn
     * - 1. [Requirement 1] If your monster is inside of Tokyo – Gain 1 star
     * - 2. Change the current monster to next one.
     * @param game
     */
    @Override
    public void update(Game game) {
        if (game.current >= 0) {
            Monster monster = game.getCurrent();
            if (monster == game.inTokyo) {
                monster.changeStars(1);
            }
        }

        game.current++;
        if (game.current >= game.players)
            game.current = 0;

        Monster monster = game.getCurrent();
        Terminal term = monster.getTerminal();
        term.writeString(toString(game));
        game.messageTo("\nWaiting for " + monster.getName() + " to make a move...\n", Target.OTHERS);
        try {
            term.writeString("QUERY\n");
            String str = term.readString();
        } catch (IOException e) {
            e.printStackTrace();
            game.exit();
        }
        game.setState(new DiceRollState());
    }


    /**
     * To string method tells the players that it is his or hers turn and displays
     * the stats of all the players i.e. health, energy etc.
     * @param game the state of the game
     * @return the string described above
     */
    @Override
    public String toString(Game game) {
        String name = game.getCurrent().getName();
        StringBuilder buffer = new StringBuilder();
        buffer.append("\nYou are " + name + " and it is your turn. Here are the stats:\n");
        for (Monster m : game.getAllMonsters()) {
            buffer.append(m.toString());
        }
        buffer.append("Press [ENTER]\n");
        return buffer.toString();
    }
}
