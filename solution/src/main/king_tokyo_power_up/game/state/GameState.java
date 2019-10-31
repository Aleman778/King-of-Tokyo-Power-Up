package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;

/**
 * The king_tokyo_power_up.game state interface provides necessary functions for all the different king_tokyo_power_up.game states.
 * The linear state chart follows:
 * 1. StartTurnState (starts a new turn changes current player)
 * 2. DiceRollState (dice roll state rolls and rerolls dice)
 * 3. ActionState (action state heal, attack and receive victory points)
 * 4. ShopState (buy store cards at the shop by spending energy)
 * 5. EndTurnState (gives an overview of the stats and checks for victory conditions)
 * After 5 it loops back to 1 and does so until the victory conditions have been met.
 */
public interface GameState {
    /**
     * Updates the state should eventually enters the next state.
     * @param game the state of the king_tokyo_power_up.game
     */
    void update(Game game);


    /**
     * Custom to string method with the king_tokyo_power_up.game state.
     * @param game the state of the king_tokyo_power_up.game
     */
    String toString(Game game);
}
