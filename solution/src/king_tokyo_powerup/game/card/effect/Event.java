package king_tokyo_powerup.game.card.effect;

import king_tokyo_powerup.game.monster.Monster;
import king_tokyo_powerup.game.state.GameState;

/**
 * Event class holds the owner of the event and the general game state.
 */
public class Event {
    /**
     * The monster who owns this event i.e. has the current active effect.
     */
    private final Monster owner;
    private final GameState state;

    /**
     * Creates a new event with an owner and the game state.
     * @param owner the monster owner
     * @param state the game state.
     */
    public Event(Monster owner, GameState state) {
        this.owner = owner;
        this.state = state;
    }
}
