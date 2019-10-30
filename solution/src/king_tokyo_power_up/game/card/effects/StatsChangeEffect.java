package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.card.Event;
import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.monster.Monster;


/**
 * State changed effect immediately changes the stats.
 */
public class StatsChangeEffect extends Effect {
    /**
     * The monster to target this effect to.
     */
    public Target target;

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
     * @param target the monster to target
     */
    public StatsChangeEffect(Target target, int maxHealth, int health, int stars, int energy) {
        this.target = target;
        this.maxHealth = maxHealth;
        this.health = health;
        this.stars = stars;
        this.energy = energy;
    }


    /**
     * Immediately changes the stats of the targeted monsters.
     * @param event the event
     */
    @Override
    public void immediate(Event event) {
        Monster[] monsters = event.game.getMonsters(target);
        for (Monster mon : monsters) {
            if (maxHealth != 0) {
                String posSign = maxHealth > 0 ? "+" : "";
                mon.changeMaxHealth(maxHealth);
                event.sendMessage(mon, "Your maximum health changed by " + posSign + maxHealth);
            }
            if (health != 0) {
                String posSign = health > 0 ? "+" : "";
                mon.changeHealth(health);
                event.sendMessage(mon, "Your health changed by " + health + "♥, health = " + mon.getHealthString());
            }
            if (energy != 0) {
                String posSign = energy > 0 ? "+" : "";
                mon.changeEnergy(energy);
                event.sendMessage(mon, "Your energy changed by " + energy + "⚡");
            }
            if (stars != 0) {
                String posSign = stars > 0 ? "+" : "";
                mon.changeStars(stars);
                event.sendMessage(mon, "Your stars changed by " + energy + "★");
            }
        }
    }
}
