package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.event.EventType;
import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;

/**
 * The end of the current players turn [Requirement 15], this gives an overview of the stats
 * resets the loop so that StartTurnState changes the current monster to the next.
 * This state is also responsible for checking the following victory conditions:
 * - [Requirement 16] First monster to get 20 stars win the game
 * - [Requirement 17] The sole surviving monster wins the game (other monsters at 0 or less health)
 * - [Requirement 18] A monster that reaches 0 or less health is out of the game
 * If any of the victory conditions have been met than the winner is announced and game exits.
 */
public class EndTurnState implements GameState {
    /**
     *
     * @param game the state of the game
     */
    @Override
    public void update(Game game) {
        Monster monster = game.getCurrent();
        monster.notify(game, EventType.END_TURN);
        Terminal terminal = monster.getTerminal();
        terminal.writeString(toString(game));
        terminal.writeString("QUERY:ENTER\n");
        try {
            terminal.readString();
        } catch(IOException e) {
            e.printStackTrace();
            game.exit();
        }
        checkVictoryConditions(game);
        game.setState(new StartTurnState());
    }


    /**
     * Checks the victory conditions based on requirement 16 - 18.
     * If there is a winner monster then this is announced and the game is exited.
     * @param game the state of the game
     */
    public void checkVictoryConditions(Game game) {
        Monster winner = getWinningMonster(game);
        if (winner != null) {
            for (Monster mon : game.getAllMonsters()) {
                if (winner == mon) {
                    mon.getTerminal().writeString("\n\n\n-- You win! -- \n");
                } else {
                    mon.getTerminal().writeString("\n\n\n-- You lose! --\n");
                }
            }

            game.messageTo("Congratulations to " + winner.getName() +
                    " the " + winner.getType() + "!\nHere is the final result:\n" +
                    game.toString() +"\n", Target.ALL);
            game.exit();
        }
    }


    /**
     * Get the monster who is victorious, if none is null is returned.
     * @param game the state of the game
     * @return the monster who is winning, null if none
     */
    public Monster getWinningMonster(Game game) {
        Monster winner = getMonster20Stars(game);
        if (winner != null) return winner;
        winner = getLastStandingMonster(game);
        if (winner != null) return winner;
        return null;
    }


    /**
     * Get the winning monster with 20 or more stars [Requirement 16].
     * If no monster has at least 20 stars then null is returned.
     * @param game the state of the game
     * @return the monster with 20 or more stars, if none then null is returned
     */
    public Monster getMonster20Stars(Game game) {
        for (Monster mon : game.getAllMonsters()) {
            if (mon.getStars() >= 20) {
                return mon;
            }
        }
        return null;
    }


    /**
     * Get the last standing monster if there are multiple
     * alive monsters then null is returned [Requirement 17].
     * @param game the state of the game
     * @return the last standing monster
     */
    public Monster getLastStandingMonster(Game game) {
        Monster winner = null;
        int alive = 0;
        for (Monster mon : game.getAllMonsters()) {
            if (isMonsterPlaying(mon)) {
                alive++;
                winner = mon;
            }
        }
        if (alive == 1) {
            return winner;
        } else {
            return null;
        }
    }


    /**
     * Check if a monster is playing i.e. is alive [Requirement 17].
     * @param monster the monster to check
     * @return true if monster is still playing, false otherwise.
     */
    public boolean isMonsterPlaying(Monster monster) {
        return monster.isAlive();
    }


    @Override
    public String toString(Game game) {
        String name = game.getCurrent().getName();
        StringBuilder buffer = new StringBuilder();
        buffer.append("\n\nIt is the end of your turn. Here are the stats:\n");
        buffer.append(game.toString());
        buffer.append("Press [ENTER]\n");
        return buffer.toString();
    }
}
