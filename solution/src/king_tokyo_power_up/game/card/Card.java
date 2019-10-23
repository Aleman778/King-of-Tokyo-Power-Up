package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.card.effect.Effect;

public class Card {
    /**
     * The name of this card.
     */
    private String name;

    /**
     * The description of this card.
     * What the card actually does when triggered.
     */
    private String description;

    /**
     * Discard flag, immediately discards retrieved
     * cards if flag is set to true, if set to false cards are kept.
     */
    private boolean discard;

    /**
     * The effect of this cards, defines what the card
     * actually does functionally similar to the
     * the textual {@link Card#description}.
     */
    private Effect effect;


    public Card() {

    }


    @Override
    public String toString() {
        return super.toString();
    }
}
