package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.monster.Monster;

public class AttackEvent extends Event {
    /**
     * The other monster who is either getting attacked
     * by the owner or attacking the owner.
     * This depends on the callback, attacked/ attack.
     */
    public final Monster other;


    /**
     * Creates a new event with an owner and the game state.
     * @param type the type of event
     * @param owner the monster owner
     * @param card the card
     * @param game the game state.
     */
    public AttackEvent(EventType type, Monster owner, Card card, Game game, Monster other) {
        super(type, owner, card, game);
        this.other = other;
    }
}
