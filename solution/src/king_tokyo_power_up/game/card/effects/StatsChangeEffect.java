package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.card.AttackEvent;
import king_tokyo_power_up.game.card.Event;
import king_tokyo_power_up.game.card.EventType;
import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.monster.Monster;


/**
 * State changed effect immediately changes the stats.
 */
public class StatsChangeEffect extends Effect {
    /**
     * The monsters to target.
     */
    public Target target;

    /**
     * Which event should be used?
     */
    public EventType when;

    /**
     * Change maximum health.
     */
    public int maxHealth;

    /**
     * Change the health.
     */
    public int health;

    /**
     * Change the stars.
     */
    public int stars;

    /**
     * Change the energy.
     */
    public int energy;


    /**
     * Changes the stats of the targeted monster
     * @param target the monsters to target
     * @param maxHealth change max health
     * @param health change max health
     * @param stars change max health
     * @param energy change max health
     */
    public StatsChangeEffect(Target target, EventType when, int maxHealth, int health, int stars, int energy) {
        this.target = target;
        this.when = when;
        this.maxHealth = maxHealth;
        this.health = health;
        this.stars = stars;
        this.energy = energy;
    }


    /**
     * Simplified construct, effect happens immediately and targets the card owner.
     * @param maxHealth change max health
     * @param health change max health
     * @param stars change max health
     * @param energy change max health
     */
    public StatsChangeEffect(int maxHealth, int health, int stars, int energy) {
        this(Target.SELF, EventType.IMMEDIATE, maxHealth, health, stars, energy);
    }


    @Override
    public void effect(Event event) {
        if (event.type != when)
            return;

        for (Monster mon : event.game.getMonsters(target)) {
            if (maxHealth != 0) {
                String posSign = maxHealth > 0 ? "+" : "";
                mon.changeMaxHealth(maxHealth);
                event.sendMessage(mon, "Your maximum health changed by " + posSign + maxHealth);
            }
            if (health != 0) {
                String posSign = health > 0 ? "+" : "";
                mon.changeHealth(health);
                event.sendMessage(mon, "Your health changed by " + posSign + health + "♥, health = " + mon.getHealthString());
            }
            if (energy != 0) {
                String posSign = energy > 0 ? "+" : "";
                mon.changeEnergy(energy);
                event.sendMessage(mon, "Your energy changed by " + posSign + energy + "⚡");
            }
            if (stars != 0) {
                String posSign = stars > 0 ? "+" : "";
                mon.changeStars(stars);
                event.sendMessage(mon, "Your stars changed by " + posSign + stars + "★");
            }
        }
    }
}
