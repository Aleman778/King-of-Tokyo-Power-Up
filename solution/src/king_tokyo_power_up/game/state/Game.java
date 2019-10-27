package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.monster.Monster;

import java.util.ArrayList;

/**
 * The game class holds information about an ongoing game.
 * This class holds the current state of the game.
 */
public class Game {
    public static int MIN_PLAYERS = 2;
    public static int DEFAULT_PLAYERS = 6;
    public static int MAX_PLAYERS = 6;

    /**
     * The current game state.
     */
    private GameState state;

    /**
     * List of the monsters in this game i.e. the players.
     */
    private ArrayList<Monster> monsters;


    /**
     * Constructs a new game.
     */
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
