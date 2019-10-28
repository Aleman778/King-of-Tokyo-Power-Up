package king_tokyo_power_up.game;

import king_tokyo_power_up.game.card.Deck;
import king_tokyo_power_up.game.card.EvolutionCard;
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
     * Constructs a new game.
     */
    public void Game() {
        monsters = new ArrayList<>();

    }


    /**
     * Sets up the game with the specific number of players.
     * The setup process:
     * 1. [Requirement 1] Each player is assigned a monster.
     *      - [Requirement 2] Sets victory points to 0 (done during creation).
     *      - [Requirement 3] Monsters health is set to 10 (done during creation)
     *      - Each monsters evolution decks created and shuffled.
     * 2. [Requirement 4] Shuffle the store cards (done using the Store class).
     * @param players the number of players
     */
    public void setup(int players) {
        ArrayList<Monster> allMonsters = MonsterFactory.createMonsters();
        Collections.shuffle(allMonsters);
        for (int i = 0; i < players; i++) {
            monsters.add(allMonsters.get(i));
        }
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
