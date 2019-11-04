package king_tokyo_power_up.game.monster;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.card.*;
import king_tokyo_power_up.game.event.AttackEvent;
import king_tokyo_power_up.game.event.Event;
import king_tokyo_power_up.game.event.EventType;
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
        this.evolutions = new Deck<>();
    }


    /**
     * Get the evolution deck to be used by this monster.
     * @return the evolution deck
     */
    public Deck<EvolutionCard> getEvolutions() {
        return evolutions;
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
            addCard(game, card);
            terminal.writeString("You got a new evolution:\n" + card.toString() + "\n");
        } else {
            terminal.writeString("Your evolution card deck is empty!\n");
        }
    }


    /**
     * Start turn event is called at the beginning the owners turn.
     * @param game the king_tokyo_power_up.game state
     */
    public void notify(Game game, EventType type) {
        for (Card card : cards) {
            Event event = new Event(type, this, card, game);
            card.getEffect().effect(event);
        }
    }


    /**
     * Attack event callback is called when the card owner is attacking another monster.
     * @param game the king_tokyo_power_up.game state
     * @param target the monster who you are attacking
     */
    public void attack(Game game, Monster target) {
        for (Card card : cards) {
            AttackEvent event = new AttackEvent(EventType.ATTACK, this, card, game, target);
            card.getEffect().effect(event);
        }
    }


    /**
     * Attacked event callback is called when the card owner is attacked by another monster.
     * @param game the king_tokyo_power_up.game state
     * @param attacker the monster who attacked you
     */
    public void attacked(Game game, Monster attacker) {
        for (Card card : cards) {
            AttackEvent event = new AttackEvent(EventType.ATTACKED, this, card, game, attacker);
            card.getEffect().effect(event);
        }
    }


    /**
     * Adds the given card to the list of cards.
     * If the card is discarded upon retrieval then
     * the immediate effect is carried out and discarded.
     * @param card
     */
    public void addCard(Game game, Card card) {
        if (!card.discard())
            cards.add(card);
        Event event = new Event(EventType.IMMEDIATE, this, card, game);
        card.getEffect().effect(event);
    }


    /**
     * Discards the given card from the monsters hand.
     * @param card the card to discard
     */
    public void discardCard(Card card) {
        cards.remove(card);
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
        String cardStatus = "";
        if (cards.size() > 0) {
            cardStatus = spacing + "The monster has the following cards:\n";
            for (int i = 0; i < cards.size(); i++) {
                cardStatus += spacing + "[" + i + "] " + cards.toString() + "\n";
            }
        }
        if (isAlive()) {
            return monsterName + spacing + getStatsString() + "\n" + cardStatus;
        } else {
            return monsterName + spacing + "is dead (RIP)\n";
        }
    }

    @Override
    public String toString() {
        return toString(false);
    }
}
