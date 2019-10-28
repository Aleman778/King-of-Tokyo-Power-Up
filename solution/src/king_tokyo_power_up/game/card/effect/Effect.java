package king_tokyo_power_up.game.card.effect;

/**
 * Effects are defined as callbacks to events they can modify a monsters
 * stats or provide other effects. These effects are defined within one
 * or more different callback functions e.g. beginTurn. For each callback
 * comes an event that holds the necessary information.
 */
public abstract class Effect {
    /**
     * Begin turn event is called at the beginning of each turn
     * @param event the event
     */
    public void beginTurn(Event event) { }


    /**
     * Event callback is executed at the beginning of each event.
     * @param event the event
     */
    public void attacked(Event event) { }


    /**
     * Event callback is executed at the beginning of each event.
     * @param event the event
     */
    public void endTurn(Event event) { }


    /**
     * Immediate event callback is called directly upon use.
     * Either automatically via discard card, or manually using a card.
     */
    public void immediate() { }
}
