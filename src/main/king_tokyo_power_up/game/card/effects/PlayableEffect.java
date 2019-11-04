package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.event.Event;
import king_tokyo_power_up.game.event.EventType;
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
     * When should the monster be asked?
     */
    public EventType when;


    /**
     * Playable effect can be chosen to be played whenever the monster wants.
     * @param when at which state is the monster asked?
     */
    public PlayableEffect(EventType when) {
        this.when = when;
    }


    @Override
    public void effect(Event event) {
        if (event.type == when) {
            event.sendMessage(event.owner, "Do you wish to use this card? (Enter Yes or No)\n    Card: " + event.card.toString());
            event.sendMessage(event.owner,"QUERY:PLAY_CARD");
            try {
                Terminal terminal = event.owner.getTerminal();
                if (terminal.readBoolean("Yes", "No", "Please enter Yes or No!")) {
                    play(event);
                }
            } catch (IOException e) {
                e.printStackTrace();
                event.game.exit();
            }
        }
    }


    /**
     * The card owner choose to play this card, now what should happen here?
     * @param event the event
     */
    public abstract void play(Event event);
}
