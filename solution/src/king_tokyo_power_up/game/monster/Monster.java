package king_tokyo_power_up.game.monster;

import king_tokyo_power_up.game.card.Card;
import king_tokyo_power_up.game.card.Deck;
import king_tokyo_power_up.game.card.EvolutionCard;
import king_tokyo_power_up.game.utils.Formatting;

import java.util.ArrayList;

/**
 * The players in this games are monsters. The monster
 * class defines the status of a players i.e. health, energy etc.
 */
public class Monster {
    /**
     * The maximum number of health points.
     */
    private int maxHealth = 10;

    /**
     * The current number of health points.
     */
    private int health = 10;

    /**
     * The current number of energy points.
     */
    private int energy = 0;

    /**
     * The current number of victory points (or stars).
     */
    private int stars = 0;

    /**
     * The name of the monster.
     */
    private String name;

    /**
     * The evolution card deck for this monster.
     * The decs are unique to each different monsters.
     * A player can draw from this deck if three hearts are rolled.
     */
    private Deck<EvolutionCard> evolutions;


    /**
     * The list of cards purchased from the store.
     * Note: cards of type discard will not be stored here.
     */
    private ArrayList<Card> cards;


    /**
     * Constructs a new monster with a specified name.
     * Each monster have also needs an evolution deck.
     */
    public Monster(String name) {
        this.name = name;
    }





    /**
     * Set the evolution deck to be used by this monster.
     * @param evolutions the evolution deck to set
     */
    public void setEvolutionDeck(Deck<EvolutionCard> evolutions) {
        this.evolutions = evolutions;
    }


    @Override
    public String toString() {
        String monsterName = name + ": ";

        // Health Bar
        String healthBar = Formatting.getRepeated(health, '#');
        healthBar += Formatting.getSpaces(maxHealth - health);
        healthBar = "health [" + healthBar + "] " + health + "/ " + maxHealth + "\n";

        String spacing = Formatting.getSpaces(monsterName.length());
        String stats = spacing + "energy = " + energy + ", stars = " + stars + "\n";

        return monsterName + healthBar + stats;
    }
}
