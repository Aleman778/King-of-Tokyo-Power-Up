package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.card.effects.AttackChangeEffect;
import king_tokyo_power_up.game.card.effects.ShopDiscountEffect;
import king_tokyo_power_up.game.card.effects.StatsChangeEffect;
import king_tokyo_power_up.game.util.CSVParser;

import java.io.IOException;

/**
 * The deck factory class is used to create decks for
 * either the store or each monsters evolutions.
 */
public class DeckFactory {
    /**
     * Only static access is allowed.
     */
    private DeckFactory() { }


    /**
     * Creates the store cards deck and shuffles the deck.
     * @return the deck containing store cards
     */
    public static Deck<StoreCard> createStoreDeck() {
        Deck<StoreCard> deck = new Deck<>();
        deck.add(new StoreCard(
                "Acid Attack", 6, false,
                "Deal 1 extra damage each turn (even when you don't otherwise attack)",
                new AttackChangeEffect(0, 1))
        );
        deck.add(new StoreCard(
                "Alien Metabolism", 3, false,
                "Buying cards costs you 1⚡ energy less",
                new ShopDiscountEffect(1, true))
        );
        deck.add(new StoreCard(
                "Alpha Monster", 5, false,
                "Gain 1★ star when you attack",
                new StatsChangeEffect(Target.SELF, 0,0,1,0))
        );
        deck.add(new StoreCard(
                "Apartment Building", 5, true,
                "Immediately Gain 3★",
                new StatsChangeEffect(Target.SELF, 0,0,3,0))
        );
        deck.add(new StoreCard(
                "Armour Plating", 5, true,
                "When attacked ignore 1 damage",
                new AttackChangeEffect(1, 0))
        );
        return deck;
    }


    /**
     * Creates the evolution deck for a specific monster.
     * @param monster the name of the monster
     * @return the evolution deck, null for invalid monster name
     */
    public static Deck<EvolutionCard> createEvolutionDeck(String monster) {
        switch (monster) {
            case "Alienoid":     return createAlienoidDeck();
            case "Gigazaur":     return createGigazaurDeck();
            case "Kong":         return createKongDeck();
            case "Pumpkin Jack": return createPumpkinJackDeck();
            case "Rob":          return createRobDeck();
            case "Cthulhu":      return createCthulhuDeck();
        }
        return null;
    }


    /**
     * Creates the evolution card deck for the Alienoid monster.
     * @return the evolution card deck
     */
    private static Deck<EvolutionCard> createAlienoidDeck() {
        Deck<EvolutionCard> deck = new Deck<>();
        return deck;
    }


    /**
     * Creates the evolution card deck for the Gigazaur monster.
     * @return the evolution card deck
     */
    private static Deck<EvolutionCard> createGigazaurDeck() {
        Deck<EvolutionCard> deck = new Deck<>();
        return deck;
    }


    /**
     * Creates the evolution card deck for the Kong monster.
     * @return the evolution card deck
     */
    private static Deck<EvolutionCard> createKongDeck() {
        Deck<EvolutionCard> deck = new Deck<>();
        return deck;
    }


    /**
     * Creates the evolution card deck for the Pumpkin Jack monster.
     * @return the evolution card deck
     */
    private static Deck<EvolutionCard> createPumpkinJackDeck() {
        Deck<EvolutionCard> deck = new Deck<>();
        return deck;
    }


    /**
     * Creates the evolution card deck for the Rob monster.
     * @return the evolution card deck
     */
    private static Deck<EvolutionCard> createRobDeck() {
        Deck<EvolutionCard> deck = new Deck<>();
        return deck;
    }


    /**
     * Creates the evolution card deck for the Cthulhu monster.
     * @return the evolution card deck
     */
    private static Deck<EvolutionCard> createCthulhuDeck() {
        Deck<EvolutionCard> deck = new Deck<>();
        return deck;
    }
}
