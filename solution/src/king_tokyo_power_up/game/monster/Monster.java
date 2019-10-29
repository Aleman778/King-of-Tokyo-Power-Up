package king_tokyo_power_up.game.monster;

import king_tokyo_power_up.game.card.Card;
import king_tokyo_power_up.game.card.Deck;
import king_tokyo_power_up.game.card.EvolutionCard;
import king_tokyo_power_up.game.util.Formatting;
import king_tokyo_power_up.game.util.Terminal;

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
     * The name of the monster e.g. Alienoid.
     */
    private String name;

    /**
     * The type of monsters e.g. Alien.
     */
    private String type;

    /**
     * The terminal used to communicate with other monsters.
     */
    private Terminal terminal;

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
     * @param name the name of the monster e.g. Alienoid
     * @param type the monster type e.g. Alien
     */
    public Monster(String name, String type) {
        this.name = name;
        this.type = type;
    }


    /**
     * Set the evolution deck to be used by this monster.
     * @param evolutions the evolution deck to set
     */
    public void setEvolutionDeck(Deck<EvolutionCard> evolutions) {
        this.evolutions = evolutions;
    }


    /**
     * Change the health by adding the given health points.
     * @param hp the health points to increase
     */
    public void changeHealth(int hp) {
        health += hp;
        if (health > maxHealth)
            health = maxHealth;
        if (health < 0)
            health = 0;
    }


    /**
     * Change the victory points by adding the given victory points.
     * @param vp the victory points to increase
     */
    public void changeStars(int vp) {
        stars += vp;
        if (stars < 0)
            stars = 0;
    }


    /**
     * Change the energy by adding the given energy points.
     * @param ep the energy points to increase
     */
    public void changeEnergy(int ep) {
        energy += ep;
        if (energy < 0)
            energy = 0;
    }


    /**
     * Get the name of the monster.
     * @return the name string
     */
    public String getName() {
        return name;
    }


    /**
     * Get the type of monster.
     * @return the type string
     */
    public String getType() {
        return type;
    }


    /**
     * Get the terminal used to communicate with this player.
     * @return the terminal
     */
    public Terminal getTerminal() {
        return terminal;
    }


    /**
     * Set the terminal to be used for communicating with this monster.
     * @param terminal the terminal to set
     */
    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    /**
     * Get the health as string e.g. health = ♥♥♥♥♥♥♡♡♡♡ 6/10.
     * @return the health string
     */
    public String getHealthString() {
        String healthBar = Formatting.getRepeated(health, '♥');
        healthBar += Formatting.getRepeated(maxHealth - health, '♡');
        return "health = " + healthBar + " " + health + "/" + maxHealth + ", ";
    }


    /**
     * Get the stats as string e.g. energy ⚡ = 5, stars ★ = 3.
     * @return the stats string
     */
    public String getStatsString() {
        return "energy ⚡ = " + energy + ", stars ★ = " + stars;
    }


    @Override
    public String toString() {
        String monsterName = name + ":\n";
        String spacing = Formatting.getSpaces(4);
        return monsterName + spacing + getHealthString() + getStatsString() + "\n";
    }
}
