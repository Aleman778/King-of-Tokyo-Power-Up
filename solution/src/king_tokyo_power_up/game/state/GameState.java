package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;

/**
 * The abstract game state class.
 */
public interface GameState {
    /**
     * Updates the state should eventually enter a new state.
     * @param game the state of the game
     */
    void update(Game game);


    /**
     * Custom to string method with the game state.
     * @param game the state of the game
     */
    String toString(Game game);
}
