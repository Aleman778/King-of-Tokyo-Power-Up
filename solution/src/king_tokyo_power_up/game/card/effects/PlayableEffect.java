package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.card.Event;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;

/**
 * A playable effect is an effect that can be activated by the monster
 * at the start of its turn. This is an abstract effect and has no effect.
 * Extend this class to provide other types of effects.
 * Playing a card does not discard it call discard to do so yourself.
 * Use the {@link PlayableEffect#play(Event)} to put the effect.
 */
public abstract class PlayableEffect extends Effect {

    /**
     * Ask if the card owner wants to play this card?
     * @param event the event
     */
    @Override
    public void startTurn(Event event) {
        Terminal terminal = event.owner.getTerminal();
        event.sendMessage(event.owner, "Do you wish to use this card?\n" + event.card.toString());
        event.sendMessage(event.owner,"QUERY:PLAY_CARD\n");
        try {
            if (terminal.readBoolean("Yes", "No", "Enter Yes or No!")) {
                play(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
            event.game.exit();
        }
    }


    /**
     * The card owner choose to play this card, now what should happen here?
     * @param event the event
     */
    public abstract void play(Event event);
}
