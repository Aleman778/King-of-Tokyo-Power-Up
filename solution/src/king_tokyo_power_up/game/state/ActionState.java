package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.dice.DiceResult;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;

/**
 * The action state is where the dice rolled are taken into effect.
 * This state is responsible for requirement 12:
 * Sum up the dice and assign stars, health, or damage
 * • Triple 1’s = 1 Star Each additional 1 equals +1 star
 * • Triple 2’s = 2 Stars Each additional 2 equals +1 star
 * • Triple 3’s = 3 Stars Each additional 3 equals +1 star
 * • Each energy = 1 energy
 * • Each heart
 *      i. Inside Tokyo – no extra health
 *      ii. Outside Tokyo - +1 health (up to your max life, normally 10 unless altered by a card)
 * • Triple hearts = Draw an Evolution Card.
 * • Each claw
 *      i. Inside Tokyo – 1 damage dealt to each monster outside of Tokyo
 *      ii. Outside Tokyo
 *          1. Tokyo Unoccupied = Move into Tokyo and Gain 1 star
 *          2. Tokyo Occupied
 *              a. 1 damage dealt to the monster inside Tokyo
 *              b. Monsters damaged may choose to leave Tokyo
 *              c. If there is an open spot in Tokyo – Move into Tokyo and Gain 1 star
 */
public class ActionState implements GameState {
    /**
     * The result of the dice rolled in previous state.
     */
    public DiceResult result;

    /**
     * The current monster performing the action based on the dice result.
     */
    private Monster monster;

    /**
     * The terminal used to communicate with the current monster.
     */
    private Terminal terminal;

    /**
     * The state of the game.
     */
    private Game game;


    /**
     * Constructs a new action state with the given dice result.
     * @param result the result 
     */
    public ActionState(DiceResult result) {
        this.result = result;
    }


    /**
     * The update method acts according to the rules given in
     * requirement 12 (also defined in class description)
     * and the provided dice result.
     * @param game the state of the game
     */
    @Override
    public void update(Game game) {
        this.game = game;
        monster = game.getCurrent();
        terminal = monster.getTerminal();
        monster.changeStars(getStars());
        monster.changeEnergy(getEnergy());
        monster.changeHealth(getHealth());
        if (isEvolving()) {
            monster.evolve(game);
        }
        attackAction();
        game.setState(new ShopState());
    }


    /**
     * Updates the victory points of the current monster.
     * - Triple 1’s = 1 Star Each additional 1 equals +1 star
     * - Triple 2’s = 2 Stars Each additional 2 equals +1 star
     * - Triple 3’s = 3 Stars Each additional 3 equals +1 star
     */
    public int getStars() {
        int stars = 0;
        if (result.ones   >= 3) stars += result.ones - 2;
        if (result.twos   >= 3) stars += result.twos - 1;
        if (result.threes >= 3) stars += result.threes;
        return stars;
    }


    /**
     * Updates the energy points of the current monster.
     * - Each energy = 1 energy
     */
    public int getEnergy() {
        return result.energies;
    }


    /**
     * Updates the health points of the current monster and other.
     * - Each heart
     *      i. Inside Tokyo – no extra health
     *      ii. Outside Tokyo - +1 health (up to your max life, normally 10 unless altered by a card)
     */
    public int getHealth() {
        if (monster == game.inTokyo) {
            return 0;
        }
        return result.hearts;
    }


    /**
     * Checks if the monster can evolve i.e. draw an evolution card.
     * - Triple hearts = Draw an Evolution Card.
     * @return true if an evolution card can be drawn, false otherwise
     */
    public boolean isEvolving() {
        return result.hearts >= 3;
    }


    /**
     * The attack action function performs the attack based the given requirement below:
     * - Each claw
     *      i. Inside Tokyo – 1 damage dealt to each monster outside of Tokyo
     *      ii. Outside Tokyo
     *          1. Tokyo Unoccupied = Move into Tokyo and Gain 1 star
     *          2. Tokyo Occupied
     *              a. 1 damage dealt to the monster inside Tokyo
     *              b. Monsters damaged may choose to leave Tokyo
     *              c. If there is an open spot in Tokyo – Move into Tokyo and Gain 1 star
     */
    public void attackAction() {
        if (result.claws == 0)
            return;

        if (monster == game.inTokyo) {
            Monster[] targets = game.getMonsters(Target.OTHERS);
            for (Monster mon : targets) {
                attack(mon);
            }
        } else {
            if (game.inTokyo == null) {
                enterTokyo();
            } else {
                Monster target = game.inTokyo;
                attack(target);
                if (result.claws == 0)
                    return;

                if (target.isAlive()) {
                    askLeaveTokyo(target);
                } else {
                    game.inTokyo = null;
                    enterTokyo();
                }
            }
        }
    }


    /**
     * Ask the person who was attacked if it wishes to leave Tokyo.
     * If he answers YES you have to enter Tokyo.
     * @param target the monster to ask
     */
    public void askLeaveTokyo(Monster target) {
        terminal.writeString("Waiting for " + target.getName() + "...\n");
        Terminal targetTerminal = target.getTerminal();
        targetTerminal.writeString("Do you wish to leave Tokyo? (enter Yes or No)\n");
        targetTerminal.writeString("QUERY:LEAVE_TOKYO\n");
        try {
            if (targetTerminal.readBoolean("yes", "no", "Please enter Yes or No!\nQUERY:LEAVE_TOKYO\n")) {
                game.inTokyo = null;
                enterTokyo();
            }
        } catch (IOException e) {
            e.printStackTrace();
            game.exit();
        }
    }


    /**
     * Attacks a specific monster.
     * @param target the monster to attack
     */
    public void attack(Monster target) {
        Terminal terminal = target.getTerminal();
        target.attacked(game, monster);
        monster.attack(game, target);
        // Your attack can be shielded by the target.
        if (result.claws == 0)
            return;

        target.changeHealth(-result.claws);
        if (target.getHealth() > 0) {
            terminal.writeString("You were attacked by " + monster.getName() + " and lost " + result.claws + " hp\n");
            terminal.writeString("You have " + target.getHealthString() + " health left!\n");
        } else {
            terminal.writeString("You were killed by " + monster.getName() + " and lost the game!\n");
        }
    }


    /**
     * The current monster enters Tokyo if it is unoccupied.
     */
    public void enterTokyo() {
        game.inTokyo = monster;
        monster.changeStars(1);
        terminal.writeString("You have entered Tokyo and gained +1 Star\n");
    }

    /**
     * This is a silent state i.e. does not print anything.
     * @param game the state of the game
     * @return empty string
     */
    @Override
    public String toString(Game game) {
        return "";
    }
}
