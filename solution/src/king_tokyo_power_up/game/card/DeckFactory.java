package king_tokyo_power_up.game.card;

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
        try {
            String[] values = CSVParser.parse("game/card/store.csv", ";");
            Deck<StoreCard> deck = new Deck<>();
            int index = 0;
            while (index < values.length - 4) {
                String name = values[index];
                int cost = Integer.parseInt(values[index + 1]);
                boolean discard = values[index + 2].equals("Discard");
                String description = values[index + 3];
                deck.add(new StoreCard(name, cost, discard, description));
                index += 4;
            }

            return deck;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
