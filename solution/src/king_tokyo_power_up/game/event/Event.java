package king_tokyo_power_up.game.event;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.card.Card;
import king_tokyo_power_up.game.monster.Monster;

/**
 * Event class holds the owner of the event, card owning this effect and the general game state.
 * This event class is used to give context to an {@link king_tokyo_power_up.game.card.effects.Effect}.
 */
public class Event {
    /**
     * The monster who owns this event i.e. has the current active effects.
     */
    public final Monster owner;

    /**
     * The game object holding the state of the game.
     */
    public final Game game;

    /**
     * Which card is owning this effect.
     */
    public final Card card;

    /**
     * The event type.
     */
    public final EventType type;


    /**
     * Creates a new event with an owner and the game state.
     * @param type the type of event
     * @param owner the monster owner
     * @param card the card
     * @param game the game state.
     */
    public Event(EventType type, Monster owner, Card card, Game game) {
        this.type = type;
        this.owner = owner;
        this.game = game;
        this.card = card;
    }


    /**
     * Sends a formatted message with the name of the card
     * the a specific monster. The message is terminated with new line.
     * @param monster the monster to message
     */
    public void sendMessage(Monster monster, String message) {
        monster.getTerminal().writeString("[" + card.getName() + "] " + message + "\n");
    }
}
