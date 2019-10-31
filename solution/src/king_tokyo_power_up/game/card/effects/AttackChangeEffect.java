package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.card.AttackEvent;
import king_tokyo_power_up.game.card.Event;
import king_tokyo_power_up.game.card.EventType;
import king_tokyo_power_up.game.state.ActionState;
import king_tokyo_power_up.game.state.GameState;


/**
 * Attack change effect defines different changes that can happen
 * when being attacked, e.g. getting armour/ stronger attacks etc.
 */
public class AttackChangeEffect extends Effect {

    private int defense;

    private int strength;

    public AttackChangeEffect(int defense, int strength) {
        this.defense = defense;
        this.strength = strength;
    }


    @Override
    public void effect(Event evn) {
        if (!(evn instanceof AttackEvent)) return;
        AttackEvent event = (AttackEvent) evn;
        if (defense > 0 && event.type == EventType.ATTACKED) {
            ActionState state = (ActionState) event.game.getState();
            state.result.claws -= defense;
            if (state.result.claws < 0) {
                state.result.claws = 0;
            }
            event.sendMessage(event.owner, "You have defended yourself against an attack by " + event.other.getName() + "!");
            event.sendMessage(event.other, "Your attack on " + event.owner.getName() + " was weakened by -" + defense + " damage!");
        } else if (strength > 0 && event.type == EventType.ATTACK) {
            ActionState state = (ActionState) event.game.getState();
            state.result.claws += strength;
            event.sendMessage(event.owner, "Your attack on " + event.other.getName() + " was strengthened by +" + strength + "damage!");
            event.sendMessage(event.other, "You got attacked by " + event.other.getName() + " with a +" + strength + " damage stronger attack");
        }
    }
}
