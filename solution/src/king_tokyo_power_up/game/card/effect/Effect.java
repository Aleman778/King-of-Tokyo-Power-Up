package king_tokyo_power_up.game.card.effect;

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
