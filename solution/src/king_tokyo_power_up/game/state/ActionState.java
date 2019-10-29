package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.dice.DiceResult;

/**
 * The action state is where the dice rolled are taken into effect.
 * This state is responsible for requirement 12:
 * Sum up the dice and assign stars, health, or damage
 * • Tripple 1’s = 1 Star Each additional 1 equals +1 star
 * • Tripple 2’s = 2 Stars Each additional 2 equals +1 star
 * • Tripple 3’s = 3 Stars Each additional 3 equals +1 star
 * • Each energy = 1 energy
 * • Each heart
 *      i. Inside Tokyo – no extra health
 *      ii. Outside Tokyo - +1 health (up to your max life, normally 10 unless altered by a card)
 * • Tripple hearts = Draw an Evolution Card.
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
    private DiceResult result;


    /**
     * Construvst a new action state with the given dice result.
     * @param result the result 
     */
    public ActionState(DiceResult result) {
        this.result = result;
    }


    @Override
    public void update(Game game) {

    }


    public void victory


    @Override
    public String toString(Game game) {
        return null;
    }
}
