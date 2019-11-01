package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.card.*;
import king_tokyo_power_up.game.event.EventType;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;

/**
 * The shop state gives the current player the ability to purchase a card from the store.
 * This is responsible for requirement 13.
 */
public class ShopState implements GameState {
    /**
     * The energy cost of this item.
     */
    public int energyCost;

    /**
     * What the monster wants to purchase.
     */
    public int purchaseOption;

    /**
     * The card shop where cards are purchased.
     */
    public CardShop shop;

    /**
     * The state of the king_tokyo_power_up.game.
     */
    public Game game;

    /**
     * The current monster playing.
     */
    public Monster monster;

    /**
     * The terminal to current monster.
     */
    public Terminal terminal;


    /**
     * The update method is where the current monster can purchase cards.
     * @param game the state of the king_tokyo_power_up.game
     */
    @Override
    public void update(Game game) {
        this.game = game;
        shop = game.getShop();
        monster = game.getCurrent();
        terminal = monster.getTerminal();
        terminal.writeString(toString(game));
        shop();
        game.setState(new EndTurnState());
    }


    /**
     * The main shop functionality from requirement 13:
     * Buying Cards (As long as long as you have the Energy, you can take any of the following actions)
     *      - Purchase a card = Pay energy equal to the card cost (replace purchased cards with new from the deck)
     *      - Reset store – pay 2 energy
     */
    public void shop() {
        try {
            while (true) {
                terminal.writeString("QUERY:SHOP\n");
                purchaseOption = terminal.readInt(0, 4, 4, "Please enter a number from 0 to 4 (default is 4).\nQUERY:SHOP\n");
                energyCost = shop.getCost(purchaseOption);
                monster.notify(game, EventType.PURCHASE);
                if (purchaseOption >= 0 && purchaseOption <= 2) {
                    purchaseCard();
                } else if (purchaseOption == 3) {
                    purchaseReset();
                } else if (purchaseOption == 4) {
                    terminal.writeString("[SHOP] Thank you come again!\n");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            game.exit();
        }
    }


    /**
     * Purchase a card
     */
    public void purchaseCard() {
        if (energyCost <= monster.getEnergy()) {
            Card card = shop.purchase(purchaseOption);
            monster.changeEnergy(-energyCost);
            monster.addCard(game, card);
        } else {
            terminal.writeString("Cannot afford the that card! Please choose something else!\n");
        }
    }


    /**
     * Purchase a shop reset
     */
    public void purchaseReset() {
        if (energyCost <= monster.getEnergy()) {
            monster.changeEnergy(-energyCost);
            shop.reset();
            game.messageTo("[SHOP] " + monster.getName() + " has reset the shop!\n", Target.OTHERS);
            terminal.writeString("\nShop reset:\n" + game.shop.toString() + "\n");
        } else {
            terminal.writeString("Cannot afford to reset the shop! Please choose something else!\n");
        }
    }


    /**
     * Provide the monster with a discount in the shop.
     * @param amount the amount of energy to discount from the original cost
     */
    public void discount(int amount) {
        energyCost -= amount;
        if (energyCost < 0)
            energyCost = 0;
    }


    @Override
    public String toString(Game game) {
        int energy = game.getCurrent().getEnergy();
        String str = "\n\nDo you want to buy any of the cards from the store? " +
                "(you have " + energy + "⚡ energy)\n";
        return str + game.shop.toString() + "\n";
    }
}
