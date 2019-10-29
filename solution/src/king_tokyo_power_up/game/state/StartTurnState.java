package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;
import java.net.SocketException;

public class StartTurnState implements GameState {
    /**
     * Progresses to the next turn
     * - 1. Change the current monster to next one.
     * @param game
     */
    @Override
    public void update(Game game) {
        game.current++;
        if (game.current >= game.players)
            game.current = 0;
        System.out.println("STARTING A NEW TURN!!!");

        Terminal term = game.getCurrent().getTerminal();
        System.out.println(toString(game));
        term.writeString(toString(game));
        try {
            term.writeString("QUERY\n");
            String str = term.readString();
            System.err.println("OUT: " + str);
        } catch (IOException e) {
            e.printStackTrace();
            game.exit();
        }
    }


    @Override
    public String toString(Game game) {
        String name = game.getCurrent().getName();
        StringBuilder buffer = new StringBuilder();
        buffer.append("You are " + name + " and it is your turn. Here are the stats:\n");
        for (Monster m : game.getMonsters()) {
            buffer.append(m.toString());
        }
        buffer.append("Press [ENTER]\n");
        return buffer.toString();
    }
}
