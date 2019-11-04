package king_tokyo_power_up.game.card;

import king_tokyo_power_up.game.card.effects.*;
import king_tokyo_power_up.game.dice.Dice;
import king_tokyo_power_up.game.dice.DiceResult;
import king_tokyo_power_up.game.dice.DiceRoll;
import king_tokyo_power_up.game.event.AttackEvent;
import king_tokyo_power_up.game.event.Event;
import king_tokyo_power_up.game.event.EventType;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.state.ActionState;
import king_tokyo_power_up.game.state.DiceRollState;

import java.io.IOException;
import java.util.Random;

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
                "Gain +1★ star when you attack",
                new Effect() {
                    // Should only give one star when attacking any number of monsters.
                    private boolean disabled = false;
                    @Override
                    public void effect(Event event) {
                        if (event.type == EventType.ATTACKED && !disabled) {
                            event.owner.changeStars(1);
                            event.sendMessage(event.owner, "Gain 1★ star because you attacked!");
                            // Prevents monster from earning multipe stars if attacking multiple monsters.
                            disabled = true;
                        } else if (event.type == EventType.END_TURN) {
                            // Enable the card again, can gain 1 star next attack.
                            disabled = false;
                        }
                    }
                })
        );
        deck.add(new StoreCard(
                "Apartment Building", 5, true,
                "Gain +3★ stars",
                new StatsChangeEffect(0,0,3,0))
        );
        deck.add(new StoreCard(
                "Armour Plating", 5, false,
                "When attacked ignore 1 damage",
                new AttackChangeEffect(1, 0))
        );
        deck.add(new StoreCard(
                "Background Dweller", 4, false,
                "You can always reroll any [3] you have",
                new Effect() {
                    @Override
                    public void effect(Event event) {
                        if (event.type == EventType.DICE_ROLL || event.type == EventType.DICE_REROLL) {
                            DiceRollState state = (DiceRollState) event.game.getState();
                            Dice[] dice = state.diceRoll.getDice();
                            for (int i = 0; i < dice.length; i++) {
                                if (dice[i] == Dice.THREE) {
                                    event.sendMessage(event.owner, "You rolled a three do you want to reroll the dice? (enter Yes or No)?");
                                    event.sendMessage(event.owner, "QUERY:PLAY_CARD");
                                    try {
                                        if (event.owner.getTerminal().readBoolean("Yes", "No", "Please enter Yes or No\n")) {
                                            dice[i] = Dice.roll(new Random());
                                            i = 0;
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        event.game.exit();
                                    }
                                }
                            }
                        }
                    }
                })
        );
        deck.add(new StoreCard(
                "Burrowing", 5, false,
                "Deal 1 extra damage on Tokyo. Deal 1 damage when yielding Tokyo to the monster taking it.",
                new Effect() {
                    private Monster target;
                    @Override
                    public void effect(Event event) {
                        if (event instanceof AttackEvent) {
                            AttackEvent attackEvent = (AttackEvent) event;
                            if (event.type == EventType.ATTACK) {
                                if (event.game.inTokyo != event.owner) {
                                    attackEvent.other.changeHealth(-1);
                                    attackEvent.sendMessage(attackEvent.other, "You took 1 extra damage");
                                    attackEvent.sendMessage(attackEvent.owner, "You dealt 1 extra damage");
                                }

                            } else if (event.type == EventType.ATTACKED) {
                                if (event.game.inTokyo == event.owner) {
                                    target = attackEvent.other;
                                }
                            }
                        } else if (event.type == EventType.LEAVE_TOKYO) {
                            if (target != null) {
                                target.changeHealth(-1);
                                event.sendMessage(target, "You took 1 damage for entering Tokyo");
                                event.sendMessage(event.owner, "You dealt 1 damage to monster entering Tokyo");
                                target = null;
                            }
                        }
                    }
                })
        );
        deck.add(new StoreCard(
                "Commuter Train", 4, true,
                "Gain +2★ stars",
                new StatsChangeEffect(0,0,2,0))
        );
        deck.add(new StoreCard(
                "Camouflage", 3, false,
                "If you take damage roll a die for each damage point. On a [Heart] you do not take that damage point.",
                new Effect() {
                    @Override
                    public void effect(Event event) {
                        if (event.type == EventType.ATTACKED) {
                            ActionState state = (ActionState) event.game.getState();
                            int damage = state.result.claws;
                            DiceRoll roll = new DiceRoll(new Random(), damage);
                            roll.rollAll();
                            int ignoreDamage = roll.getResult().hearts;
                            state.result.claws -= ignoreDamage;
                            if (state.result.claws < 0)
                                state.result.claws = 0;
                            event.sendMessage(event.owner, "You are about to take " + damage + " damage" +
                                    ", you rolled " + damage + " dice:\n" + roll.toString() + "\nYou shielded " + ignoreDamage + " damage point(s)!");
                        }
                    }
                }
        ));
        deck.add(new StoreCard(
                "Complete Destruction", 3, true,
                "If you roll [1][2][3][Heart][Attack][Energy] gain +9★ stars in addition to the regular results.",
                new Effect() {
                    @Override
                    public void effect(Event event) {
                        if (event.type == EventType.DICE_ROLL) {
                            DiceRollState state = (DiceRollState) event.game.getState();
                            DiceResult result = state.diceRoll.getResult();
                            if (result.ones >= 1 && result.twos >= 1 && result.threes >= 1 &&
                                result.hearts >= 1 &&result.claws >= 1 &&result.energies >= 1) {
                                event.owner.changeStars(9);
                                event.sendMessage(event.owner, "You rolled [1][2][3][Heart][Attack][Energy] and gained +9★ stars!");
                            }
                        }
                    }
                })
        );
        deck.add(new StoreCard(
                "Corner Stone", 3, true,
                "Gain +1★ stars",
                new StatsChangeEffect(0,0,1,0))
        );
        return deck;
    }


    /**
     * Creates the evolution card deck for the Alienoid monster.
     * @return the evolution card deck
     */
    public static void createAlienoidDeck(Monster monster) {
        Deck<EvolutionCard> deck = monster.getEvolutions();
        deck.add(new EvolutionCard(
                "Alien Scourge", true, EvolutionType.TEMPORARY_EVOLUTION,
                "Gain 2★ stars",
                new StatsChangeEffect(0, 0, 2,0))
        );
    }


    /**
     * Creates the evolution card deck for the Gigazaur monster.
     * @return the evolution card deck
     */
    public static void createGigazaurDeck(Monster monster) {
        Deck<EvolutionCard> deck = monster.getEvolutions();
        deck.add(new EvolutionCard(
                "Radioactive Waste", true, EvolutionType.TEMPORARY_EVOLUTION,
                "Gain 2⚡ energy and 1♥ heart",
                new StatsChangeEffect(0, 1, 0,2))
        );
    }


    /**
     * Creates the evolution card deck for the Kong monster.
     * @return the evolution card deck
     */
    public static void createKongDeck(Monster monster) {
        Deck<EvolutionCard> deck = monster.getEvolutions();
        deck.add(new EvolutionCard(
                "Red Dawn", true, EvolutionType.TEMPORARY_EVOLUTION,
                "All other Monsters lose 2♥ hearts",
                new StatsChangeEffect(Target.OTHERS, EventType.IMMEDIATE, 0, -2, 0,0))
        );
    }


    /**
     * Creates the evolution card deck for the Pumpkin Jack monster.
     * @return the evolution card deck
     */
    public static void createPumpkinJackDeck(Monster monster) {
        Deck<EvolutionCard> deck = monster.getEvolutions();
        deck.add(new EvolutionCard(
                "Trick or Threat", false, EvolutionType.PERMANENT_EVOLUTION,
                "When you roll [ONE] [ONE] [ONE] all other Monsters must pay you 1⚡ energy or take 2 damage.",
                new Effect() {
                    @Override
                    public void effect(Event event) {
                        if (event.type == EventType.DICE_ROLL || event.type == EventType.DICE_REROLL) {
                            DiceRollState state = (DiceRollState) event.game.getState();
                            DiceResult result = state.diceRoll.getResult();
                            if (result.ones >= 3) {
                                for (Monster mon : event.game.getMonsters(Target.OTHERS)) {
                                    event.sendMessage(mon, "Pay 1 energy or take 2 damage? (enter trick [pay] or threat [take damage])");
                                    event.sendMessage(mon, "QUERY:TRICK_OR_THREAT");
                                    try {
                                        int energy = mon.getEnergy();
                                        boolean trick = mon.getTerminal().readBoolean("trick", "threat",
                                                "Please enter trick or threat!");
                                        if (trick && energy > 0) {
                                            mon.changeEnergy(-1);
                                            event.owner.changeEnergy(+1);
                                        } else {
                                            if (energy == 0) {
                                                event.sendMessage(mon, "You could not afford to pay 1⚡ energy, you took 2 damage instead!");
                                            }
                                            mon.changeHealth(-2);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        event.game.exit();
                                    }
                                }
                            }
                        }
                    }
                })
        );
    }


    /**
     * Creates the evolution card deck for the Rob monster.
     * @return the evolution card deck
     */
    public static void createRobDeck(Monster monster) {
        Deck<EvolutionCard> deck = monster.getEvolutions();
        deck.add(new EvolutionCard(
                "Infinite Loop", false, EvolutionType.TEMPORARY_EVOLUTION,
                "Play before rolling dice. During your Rolls, each time you roll [ENERGY], immediately gain 1⚡ energy for each [ENERGY] rolled and roll them again freely",
                new PlayableEffect(EventType.DICE_ROLL) {
                    @Override
                    public void play(Event event) {
                        DiceRollState state = (DiceRollState) event.game.getState();
                        Dice[] roll = state.diceRoll.getDice();
                        int energy = 0;
                        for (int i = 0; i < roll.length; i++) {
                            if (roll[i] == Dice.ENERGY) {
                                energy++;
                                roll[i] = Dice.roll(state.diceRoll.getRandom());
                                i = 0;
                            }
                        }
                        if (energy > 0) {
                            event.owner.changeEnergy(energy);
                            event.sendMessage(event.owner, "You rolled " + energy + " [ENERGY], gained " + energy + "⚡ energy, those dice were rerolled...");
                        }
                        event.owner.discardCard(event.card);
                    }
                })
        );
    }


    /**
     * Creates the evolution card deck for the Cthulhu monster.
     * @return the evolution card deck
     */
    public static void createCthulhuDeck(Monster monster) {
        Deck<EvolutionCard> deck = monster.getEvolutions();
        deck.add(new EvolutionCard(
                "Sunken R'lyeh", false, EvolutionType.PERMANENT_EVOLUTION,
                "At the start of your turn, gain 1 star if you are not in Tokyo",
                new Effect() {
                    @Override
                    public void effect(Event event) {
                        if (event.type == EventType.START_TURN) {
                            if (event.owner != event.game.inTokyo) {
                                event.owner.changeStars(1);
                                event.sendMessage(event.owner, "You gained 1★ star for not being in Tokyo");
                            }
                        }
                    }
                })
        );
    }
}
