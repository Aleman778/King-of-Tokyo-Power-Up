package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.card.Event;
import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.monster.Monster;

public class StatsChangeEffect implements Effect {
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

    public StatsChangeEffect(Target target) {
        this.target = target;
    }

    @Override
    public void effect(Event event) {
        Monster[] monster = event.getMonsters(target);


    }
}
