package king_tokyo_power_up.game.state;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.card.CardShop;
import king_tokyo_power_up.game.card.StoreCard;
import king_tokyo_power_up.game.card.Target;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.util.Terminal;

import java.io.IOException;

/**
 * The shop state gives the current player the ability to purchase a card from the store.
 * This is responsible for requirement 13.
 */
public class ShopState implements GameState {
    /**
     * The card shop where cards are purchased.
     */
    private CardShop shop;
    /**
     * The current monster playing.
     */
    private Monster monster;

    /**
     * The terminal to current monster.
     */
    private Terminal terminal;


    /**
     * The update method is where the current monster can purchase cards.
     * @param game the state of the game
     */
    @Override
    public void update(Game game) {
        shop = game.getShop();
        monster = game.getCurrent();
        terminal = monster.getTerminal();
        terminal.writeString(toString(game));
        shop(game);
        game.setState(new EndTurnState());
    }


    /**
     * The main shop functionality from requirement 13:
     * Buying Cards (As long as long as you have the Energy, you can take any of the following actions)
     *      - Purchase a card = Pay energy equal to the card cost (replace purchased cards with new from the deck)
     *      - Reset store – pay 2 energy
     * @param game the state of the game
     */
    public void shop(Game game) {
        try {
            while (true) {
                terminal.writeString("QUERY:SHOP\n");
                int option = terminal.readInt(0, 4, 4, "Please enter a number from 0 to 4 (default is 4).\nQUERY:SHOP\n");
                if (option >= 0 && option <= 2) {
                    int cost = shop.getCost(option);
                    if (cost <= monster.getEnergy()) {
                        shop.purchase(option);
                        monster.changeEnergy(-cost);
                    } else {
                        terminal.writeString("Cannot afford the that card! Please choose something else!\n");
                    }
                } else if (option == 3) {
                    int cost = shop.getResetCost();
                    if (cost <= monster.getEnergy()) {
                        shop.reset();
                        monster.changeEnergy(-cost);
                        game.messageTo("[SHOP] " + monster.getName() + " has reset the shop!\n", Target.OTHERS);
                        terminal.writeString("\nShop reset:\n" + game.shop.toString() + "\n");
                    } else {
                        terminal.writeString("Cannot afford to reset the shop! Please choose something else!\n");
                    }
                } else if (option == 4) {
                    terminal.writeString("[SHOP] Thank you come again!\n");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            game.exit();
        }
    }


    @Override
    public String toString(Game game) {
        int energy = game.getCurrent().getEnergy();
        String str = "\n\nDo you want to buy any of the cards from the store? " +
                "(you have " + energy + "⚡ energy)\n";
        return str + game.shop.toString() + "\n";
    }
}
