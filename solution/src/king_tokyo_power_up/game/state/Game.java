package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.monster.Monster;

import java.util.ArrayList;

/**
 * The game class holds information about an ongoing game.
 * This class holds the current state of the game.
 */
public class Game {

    private GameState state;
    private ArrayList<Monster> monsters;

    public void Game() {
        monsters = new ArrayList<>();
    }


    public void setup() {

    }


    public void update() {
        state.update(this);
    }


    public void setState(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }
}
