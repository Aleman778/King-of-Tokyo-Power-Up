package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.monster.Monster;

import java.util.ArrayList;

/**
 * Event class holds the owner of the event and the general game state.
 */
public class Event {
    /**
     * The monster who owns this event i.e. has the current active effects.
     */
    private final Monster owner;

    /**
     * The game object holding the state of the game.
     */
    private final Game game;


    /**
     * Creates a new event with an owner and the game state.
     * @param owner the monster owner
     * @param game the game state.
     */
    public Event(Monster owner, Game game) {
        this.owner = owner;
        this.game = game;
    }


    /**
     * Get the monster who owns this event
     * @return the owner
     */
    public Monster getOwner() {
        return owner;
    }


    /**
     * @return the game.
     */
    public Game getGame() {
        return game;
    }
}
