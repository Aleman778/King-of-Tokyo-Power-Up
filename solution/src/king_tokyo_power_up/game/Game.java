package king_tokyo_power_up.game;

import king_tokyo_power_up.game.card.CardShop;
import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.monster.MonsterFactory;
import king_tokyo_power_up.game.state.GameState;
import king_tokyo_power_up.game.state.StartTurnState;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;
import java.net.SocketException;
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
    public GameState state;

    /**
     * The card shop where {@link king_tokyo_power_up.game.card.StoreCard} are purchased.
     */
    public CardShop shop;

    /**
     * List of the monsters in this game i.e. the players.
     */
    private ArrayList<Monster> monsters;

    /**
     * The monster currently inside Tokyo.
     */
    public Monster inTokyo;

    /**
     * The index monster currently playing.
     */
    public int current;

    /**
     * The number of players.
     */
    public int players;

    /**
     * Is the game still running?
     */
    private boolean running;


    /**
     * Constructs a new game.
     */
    public Game() {
        monsters = new ArrayList<>();
        current = -1;
        players = 0;
    }


    /**
     * Sets up the game with the specific number of players.
     * The setup process: (Requirement 1 - 6)
     * 1. [Requirement 1] Each player is assigned a monster.
     *      - [Requirement 2] Sets victory points to 0 (done during creation).
     *      - [Requirement 3] Monsters health is set to 10 (done during creation)
     *      - [Requirement 5] Shuffle the evolution cards for the respective monsters
     * 2. [Requirement 4] Shuffle the store cards (done using the CardShop class).
     * @param players the maximum number of players
     */
    public void setup(int players) {
        this.players = players;
        ArrayList<Monster> allMonsters = MonsterFactory.createMonsters();
        Collections.shuffle(allMonsters);
        for (int i = 0; i < players; i++) {
            monsters.add(allMonsters.get(i));
        }
        CardShop shop = new CardShop();
    }


    /**
     * Starts the game:
     * 1. Remove monsters if not all players have joined.
     * 2. [Requirement 6] Randomise which monster starts the game.
     * 3. Sets the initial state to StartTurnState and updates it.
     * @param players the number of players actually playing
     */
    public void start(int players) {
        if (this.players > players) {
            monsters.subList(players, this.players).clear();
        }
        Collections.shuffle(monsters);
        this.players = players;
        this.running = true;
        setState(new StartTurnState());
        messageTo("Everyone is here! Starting the game now.\n\n", Target.ALL);
        while (running) {
            // Sleep zzz... reduce CPU usage by allowing other processes to run.
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            update();
        }
    }

    /**
     * Updates the game state, should progress to the next state.
     */
    public void update() {
        state.update(this);
    }


    /**
     * Exits the game used when an error has occurred or server
     * runs the exit command.
     */
    public void exit() {
        messageTo("EXIT", Target.ALL);
        running = false;
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
     * Send message to specific target of one or many monsters.
     * @param message the message to send
     * @param target the monsters to target
     */
    public void messageTo(String message, Target target) {
        for (Monster monster : getMonsters(target)) {
            monster.getTerminal().writeString(message);
        }
    }


    /**
     * Get an array of specific targeted monsters.
     * @param target the target
     * @return the array of monsters
     */
    public Monster[] getMonsters(Target target) {
        ArrayList<Monster> targeted = new ArrayList<>();
        for (Monster monster : monsters) {
            switch (target) {
                case SELF:
                    if (monster == getCurrent()) {
                        targeted.add(monster);
                    }
                    break;
                case OTHERS:
                    if (monster != getCurrent()) {
                        targeted.add(monster);
                    }
                    break;
                case ALL:
                    targeted.add(monster);
                    break;
                case IN_TOKYO:
                    if (monster == inTokyo) {
                        targeted.add(monster);
                    }
                    break;
                case OUTSIDE_TOKYO:
                    if (monster != inTokyo) {
                        targeted.add(monster);
                    }
                    break;
            }
        }
        return targeted.toArray(new Monster[targeted.size()]);
    }


    /**
     * Returns all the monsters in this game.
     * @return the monsters in game.
     */
    public ArrayList<Monster> getAllMonsters() {
        return monsters;
    }


    /**
     * Returns the current monster.
     * @return the monster null if game has not started yet.
     */
    public Monster getCurrent() {
        if (current == -1)
            return null;
        return monsters.get(current);
    }
}
