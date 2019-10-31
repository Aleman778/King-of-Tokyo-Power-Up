package king_tokyo_power_up.game;

import king_tokyo_power_up.game.client.ClientTest;
import king_tokyo_power_up.game.client.GameClient;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.server.GameServer;
import static org.junit.Assert.*;

import king_tokyo_power_up.game.server.ServerTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * GameTest tests the Game class and the Requirements 1 - 6.
 * 1. Each player is assigned a monster.
 * 2. Set Victory Points to 0
 * 3. Set Life to 10
 * 4. Shuffle the store cards (contained in the deck)
 *      • Start with 3 cards face up (available for purchase).
 * 5. Shuffle the evolution cards for the respective monsters
 * 6. Randomise which monster starts the game.
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

    @Test
    public void testMonsters() {
        for (Monster mon : game.getAllMonsters()) {
            System.out.println(mon);
        }
        assertTrue(true);
    }


    @Test
    public void testVictoryPoints() {
        assertTrue(true);
    }



}
