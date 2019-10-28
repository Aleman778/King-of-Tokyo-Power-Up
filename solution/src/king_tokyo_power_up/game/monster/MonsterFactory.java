package king_tokyo_power_up.game.monster;

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
        monsters.add(alienoid);

        Monster gigazaur = new Monster("Gigazaur", "Mutant");
        monsters.add(gigazaur);

        Monster kong = new Monster("Kong", "Mutant");
        monsters.add(kong);

        Monster pumpkinJack = new Monster("Pumpkin Jack", "Halloween");
        monsters.add(pumpkinJack);

        Monster rob = new Monster("Rob", "Robot");
        monsters.add(rob);

        Monster cthulhu = new Monster("Cthulhu", "Alien");
        monsters.add(cthulhu);

        return monsters;
    }
}
