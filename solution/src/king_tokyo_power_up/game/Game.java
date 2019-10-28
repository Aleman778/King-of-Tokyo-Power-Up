package king_tokyo_power_up.game;

import king_tokyo_power_up.game.card.CardShop;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.monster.MonsterFactory;
import king_tokyo_power_up.game.state.GameState;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The game class holds information about an ongoing game.
 * This class holds the current state of the game.
 */
public class Game {
    /**
     * The minimum number of players.
     */
    public static final int MIN_PLAYERS = 2;

    /**
     * The default number of players when no specific number was provided.
     */
    public static final int DEFAULT_PLAYERS = 3;

    /**
     * The maximum number of players.
     */
    public static final int MAX_PLAYERS = 6;

    /**
     * The current game state.
     */
    private GameState state;

    /**
     * List of the monsters in this game i.e. the players.
     */
    private ArrayList<Monster> monsters;

    /**
     * The monster currently inside Tokyo.
     */
    private Monster inTokyo;

    /**
     * The index monster currently playing.
     */
    private int current;


    /**
     * Constructs a new game.
     */
    public void Game() {
        monsters = new ArrayList<>();
    }


    /**
     * Sets up the game with the specific number of players.
     * The setup process: (Requirement 1 - 6)
     * 1. [Requirement 1] Each player is assigned a monster.
     *      - [Requirement 2] Sets victory points to 0 (done during creation).
     *      - [Requirement 3] Monsters health is set to 10 (done during creation)
     *      - [Requirement 5] Shuffle the evolution cards for the respective monsters
     * 2. [Requirement 4] Shuffle the store cards (done using the CardShop class).
     * 3. [] Shuffle the evolution cards for the respective monsters
     * @param players the number of players
     */
    public void setup(int players) {
        ArrayList<Monster> allMonsters = MonsterFactory.createMonsters();
        Collections.shuffle(allMonsters);
        for (int i = 0; i < players; i++) {
            monsters.add(allMonsters.get(i));
        }
        CardShop shop = new CardShop();
    }


    /**
     * Updates the game state, should progress to the next state.
     */
    public void update() {
        state.update(this);
    }


    /**
     * Set the game state.
     * @param state the state to set
     */
    public void setState(GameState state) {
        this.state = state;
    }


    /**
     * Get the current state of the game.
     * @return the state of the game.
     */
    public GameState getState() {
        return state;
    }


    /**
     * Returns all the monsters in this game.
     * @return the monsters in game.
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
