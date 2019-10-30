package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.card.AttackEvent;
import king_tokyo_power_up.game.card.Event;

public abstract class Effect {

    /**
     * Start turn event is called at the beginning the owners turn.
     * @param event the event
     */
    public void startTurn(Event event) { }


    /**
     * Purchase event is called before the card owner is purchasing something.
     * @param event the event
     */
    public void purchase(Event event) { }


    /**
     * Attack event callback is called when the card owner is attacking another monster.
     * @param event the attack event
     */
    public void attack(AttackEvent event) { }


    /**
     * Attacked event callback is called when the card owner is attacked by another monster.
     * @param event the attack event
     */
    public void attacked(AttackEvent event) { }


    /**
     * Event callback is executed at the beginning the owners turn.
     * @param event the event
     */
    public void endTurn(Event event) { }


    /**
     * Immediate event callback is called directly upon use.
     * Either automatically via discard card, or manually using a card.
     */
    public void immediate(Event event) { }
}
