package king_tokyo_power_up.game.card.effects;

import king_tokyo_power_up.game.card.Event;
import king_tokyo_power_up.game.state.ShopState;

public class ShopDiscountEffect extends Effect {

    private int discount;

    private boolean onlyCards;

    public ShopDiscountEffect(int discount, boolean onlyCards) {
        this.discount = discount;
        this.onlyCards = onlyCards;
    }


    @Override
    public void purchase(Event event) {
        ShopState state = (ShopState) event.game.getState();
        if (!onlyCards || (state.purchaseOption > 0 && state.purchaseOption < 3)) {
            state.discount(discount);
        }
    }
}
