package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.event.Event;

public abstract class Effect {

    /**
     * The effect callback is called for every event.
     * The event contains which type of event.
     * @param event the event
     */
    public void effect(Event event) { }
}
