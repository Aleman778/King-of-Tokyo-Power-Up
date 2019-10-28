package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.Game;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.state.GameState;

import java.util.ArrayList;

/**
 * Event class holds the owner of the event and the general game state.
 */
public class Event {
    /**
     * The monster who owns this event i.e. has the current active effects.
     */
    private final Monster owner;
    private final Game game;

    /**
     * Creates a new event with an owner and the game state.
     * @param owner the monster owner
     * @param game the game state.
     */
    public Event(Monster owner, Game game) {
        this.owner = owner;
        this.game = game;
    }

    /**
     * Get the monster who owns this event
     * @return the owner
     */
    public Monster getOwner() {
        return owner;
    }


    /**
     * Get the monstes based on the given target.
     * @param target target specific monsters
     * @return the targeted monsters
     */
    public Monster[] getMonsters(Target target) {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (Monster monster : game.getMonsters())
        switch (target) {
            case SELF:
                if (monster == owner) {
                    monsters.add(monster);
                }
                break;

            case OTHERS:
                if (monster != owner) {
                    monsters.add(monster);
                }
                break;

            case ALL:
                monsters.add(monster);
                break;

            case IN_TOKYO:


        }
        return monsters.toArray(new Monster[monsters.size()]);
    }


    /**
     *
     * @return
     */
    public Game getGame() {
        return game;
    }
}
