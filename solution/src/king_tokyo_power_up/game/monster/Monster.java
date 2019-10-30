package king_tokyo_power_up.game.monster;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.card.*;
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
        this.cards = new ArrayList<>();
    }


    /**
     * Set the evolution deck to be used by this monster.
     * @param evolutions the evolution deck to set
     */
    public void setEvolutionDeck(Deck<EvolutionCard> evolutions) {
        this.evolutions = evolutions;
    }


    /**
     * Get the current health of the monster.
     * @return current health
     */
    public int getHealth() {
        return health;
    }


    /**
     * Check if the monster is alive hp > 0.
     * @return true if alive, false is dead.
     */
    public boolean isAlive() {
        return health > 0;
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
     * Change the maximum health by adding the given max number.
     * @param max the maximum health points to increase
     */
    public void changeMaxHealth(int max) {
        maxHealth += max;
        if (health > maxHealth)
            health = maxHealth;
        if (maxHealth < 0)
            maxHealth = 0;
    }


    /**
     * Get the current number of stars of the monster.
     * @return current number of stars
     */
    public int getStars() {
        return stars;
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
     * Get the current amount of energy of the monster.
     * @return current amount of energy
     */
    public int getEnergy() {
        return energy;
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
     * Draw an evolution card from the evolution deck.
     */
    public void evolve(Game game) {
        EvolutionCard card = evolutions.draw();
        if (card != null) {
            immediate(game, card);
            if (!card.discard())
                cards.add(card);
        } else {
            terminal.writeString("Your evolution card deck is empty!\n");
        }
    }


    /**
     * Start turn event is called at the beginning the owners turn.
     * @param game the game state
     */
    public void startTurn(Game game) {
        for (Card card : cards) {
            Event event = new Event(this, card, game);
            card.getEffect().startTurn(event);
        }
    }


    /**
     * Purchase event is called before the card owner is purchasing something.
     * @param game the game state
     */
    public void purchase(Game game) {
        for (Card card : cards) {
            Event event = new Event(this, card, game);
            card.getEffect().purchase(event);
        }
    }


    /**
     * Attack event callback is called when the card owner is attacking another monster.
     * @param game the game state
     * @param target the monster who you are attacking
     */
    public void attack(Game game, Monster target) {
        for (Card card : cards) {
            AttackEvent event = new AttackEvent(this, card, game, target);
            card.getEffect().attack(event);
        }
    }


    /**
     * Attacked event callback is called when the card owner is attacked by another monster.
     * @param game the game state
     * @param attacker the monster who attacked you
     */
    public void attacked(Game game, Monster attacker) {
        for (Card card : cards) {
            AttackEvent event = new AttackEvent(this, card, game, attacker);
            card.getEffect().attacked(event);
        }
    }


    /**
     * Event callback is executed at the beginning the owners turn.
     * @param game the game state
     */
    public void endTurn(Game game) {
        for (Card card : cards) {
            Event event = new Event(this, card, game);
            card.getEffect().endTurn(event);
        }
    }


    /**
     * Immediate event callback is called directly upon use.
     * Either automatically via discard card, or manually using a card.
     * @param game the game state
     */
    public void immediate(Game game, Card card) {
        Event event = new Event(this, card, game);
        card.getEffect().immediate(event);
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
     * Get the health as string e.g. ♥♥♥♥♥♥♡♡♡♡ 6/10.
     * @return the health string
     */
    public String getHealthString() {
        String healthBar = Formatting.getRepeated(health, '♥');
        healthBar += Formatting.getRepeated(maxHealth - health, '♡');
        return healthBar + " " + health + "/" + maxHealth;
    }


    /**
     * Get the stats as string e.g. energy ⚡ = 5, stars ★ = 3.
     * @return the stats string
     */
    public String getStatsString() {
        return "health = " + getHealthString() + ", energy ⚡ = " + energy + ", stars ★ = " + stars;
    }


    /**
     * Converts the monster to string.
     * @param inTokyo is the monster in tokyo?
     * @return string with stats of this monster
     */
    public String toString(boolean inTokyo) {
        String inTokyoStatus = inTokyo ? " (in Tokyo)" : "";
        String monsterName = name + inTokyoStatus + ":\n";
        String spacing = Formatting.getSpaces(4);
        if (isAlive()) {
            return monsterName + spacing + getStatsString() + "\n";
        } else {
            return monsterName + spacing + "is dead (RIP)\n";
        }
    }

    @Override
    public String toString() {
        return toString(false);
    }
}
