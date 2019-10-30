package king_tokyo_power_up.game.monster;

import king_tokyo_power_up.game.card.DeckFactory;

import java.util.ArrayList;

/**
 * The monster factory class is used to create all the monsters.
 */
public class MonsterFactory {
    /**
     * Only static access is allowed.
     */
    private MonsterFactory() { }


    /**
     * Creates a list of all the monsters in this game.
     * @return the list of all monsters
     */
    public static ArrayList<Monster> createMonsters() {
        ArrayList<Monster> monsters = new ArrayList<>();
        Monster alienoid = new Monster("Alienoid", "Alien");
        alienoid.setEvolutionDeck(DeckFactory.createEvolutionDeck("Alienoid"));
        monsters.add(alienoid);

        Monster gigazaur = new Monster("Gigazaur", "Mutant");
        alienoid.setEvolutionDeck(DeckFactory.createEvolutionDeck("Gigazaur"));
        monsters.add(gigazaur);

        Monster kong = new Monster("Kong", "Mutant");
        alienoid.setEvolutionDeck(DeckFactory.createEvolutionDeck("Kong"));
        monsters.add(kong);

        Monster pumpkinJack = new Monster("Pumpkin Jack", "Halloween");
        alienoid.setEvolutionDeck(DeckFactory.createEvolutionDeck("Pumpkin Jack"));
        monsters.add(pumpkinJack);

        Monster rob = new Monster("Rob", "Robot");
        alienoid.setEvolutionDeck(DeckFactory.createEvolutionDeck("Rob"));
        monsters.add(rob);

        Monster cthulhu = new Monster("Cthulhu", "Alien");
        alienoid.setEvolutionDeck(DeckFactory.createEvolutionDeck("Cthulhu"));
        monsters.add(cthulhu);

        return monsters;
    }
}
