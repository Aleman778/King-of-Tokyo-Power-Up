package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.card.StoreCard;
import king_tokyo_power_up.game.card.effects.Effect;
import king_tokyo_power_up.game.event.Event;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.TerminalTest;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * This tests the requirement 13.
 * Requirement 14 is related but difficult to test, the Discard aspect is
 * tested in requirement 13 when purchasing the cards and testing their effect.
 */
public class ShopStateTest extends GameStateTest {

    private ShopState state;

    @Override
    public void init() {
        super.init();
        state = new ShopState();
        StoreCard[] shop = game.getShop().getStock();
        // Setting up some fake store cards
        shop[0] = new StoreCard("Get Stars", 3, true, "add 1 star", new Effect() {
            @Override
            public void effect(Event event) {
                event.owner.changeStars(1);
            }
        });
        shop[1] = new StoreCard("Lose 1 Hp", 2, true, "remove 1 hp", new Effect() {
            @Override
            public void effect(Event event) {
                event.owner.changeHealth(-1);
            }
        });
        shop[2] = new StoreCard("Get Energy", 5, true, "add 6 energy", new Effect() {
            @Override
            public void effect(Event event) {
                event.owner.changeEnergy(6);
            }
        });
        state.game = game;
        state.shop = game.shop;
        state.monster = game.getCurrent();
        state.terminal = state.monster.getTerminal();
    }


    /**
     * [Requirement 13] part 1
     * Purchase all cards and test the immediate effect.
     */
    @Test
    public void testPurchaseCard() {
        Monster monster = game.getCurrent();
        monster.changeEnergy(10);
        state.terminal = new TerminalTest(new String[]{"0", "1", "2"}); // Buy every card in stock.
        state.shop();
        assertEquals(1, monster.getStars());
        assertEquals(9, monster.getHealth());
        assertEquals(6, monster.getEnergy()); // Starts with 10 energy, buys cards for 10 get 6 energy from one card.
    }


    /**
     * [Requirement 13] part 2
     * Check that the stock has been replaced when resetting.
     */
    @Test
    public void testPurchaseReset() {
        game.getCurrent().changeEnergy(2);
        state.terminal = new TerminalTest(new String[]{"3"});
        state.shop();
        StoreCard[] cards = state.shop.getStock();
        assertNotSame("Get Stars", cards[0].getName());
        assertNotSame("Lose 1 Hp", cards[1].getName());
        assertNotSame("Get Energy", cards[2].getName());
    }
}
