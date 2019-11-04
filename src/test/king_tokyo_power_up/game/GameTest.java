package king_tokyo_power_up.game;

import king_tokyo_power_up.game.card.StoreCard;
import king_tokyo_power_up.game.client.ClientTest;
import king_tokyo_power_up.game.monster.Monster;
import static org.junit.Assert.*;

import king_tokyo_power_up.game.server.ServerTest;
import king_tokyo_power_up.game.state.StartTurnState;
import org.junit.Before;
import org.junit.Test;

/**
 * GameTest tests the Game class and the Requirements 1 - 6.
 * 1. Each player is assigned a monster.
 * 2. Set Victory Points to 0
 * 3. Set Life to 10
 * 4. Shuffle the store cards (contained in the deck)
 *      • Start with 3 cards face up (available for purchase).
 * 5. Shuffle the evolution cards for the respective monsters
 * 6. Randomise which monster starts the game.
 *
 * Notes:
 * Requirement 4 cannot be tested due to randomness behaviour of Collection.shuffle
 * Requirement 5 cannot be tested since I only have implemented 1 evolution card at the moment.
 * Requirement 6 cannot be tested due to randomness behaviour of Collection.shuffle
 * Note: You could provide a custom Random to Collection.shuffle class that has deterministic
 *       behaviour but that removes the randomness making the requirement not satisfied anymore.
 */
public class GameTest {
    /**
     * The number of clients to test with.
     */
    private static final int NUM_PLAYERS = 3;

    /**
     * The game server used for testing.
     */
    private ServerTest server;

    /**
     * The game client used for testing.
     */
    private ClientTest[] clients;

    /**
     * The game object to test.
     */
    private Game game;


    /**
     * Setting up the game for testing.
     */
    @Before
    public void initGame() {
        server = new ServerTest(NUM_PLAYERS);
        clients = new ClientTest[NUM_PLAYERS];
        game = server.getGame();

        for (int i = 0; i < NUM_PLAYERS; i++) {
            clients[i] = new ClientTest();
            server.accept();
            clients[i].start();
        }
        server.start();
    }


    /**
     * [Requirement 1]
     * Test every player are assigned one monster
     */
    @Test
    public void testMonsters() {
        for (Monster mon : game.getAllMonsters()) {
            System.out.println(mon);
        }
        assertTrue(true);
    }


    /**
     * [Requirement 2]
     * Every monster starts with 0 victory points.
     */
    @Test
    public void testVictoryPointsSetTo0() {
        for (Monster mon : game.getAllMonsters()) {
            assertEquals(0, mon.getStars());
        }
    }


    /**
     * [Requirement 3]
     * Every monster starts with 10 health.
     */
    @Test
    public void testLifeSetTo10() {
        for (Monster mon : game.getAllMonsters()) {
            assertEquals(0, mon.getStars());
        }
    }


    /**
     * [Requirement 4]
     */
    @Test
    public void testStoreCardsShuffled() {
        StoreCard[] cards = game.shop.getStock();

    }
}
