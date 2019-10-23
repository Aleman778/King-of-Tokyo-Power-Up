package king_tokyo_powerup.game;

public class DiceResult {
    public int ones;
    public int twos;
    public int threes;
    public int hearts;
    public int claws;
    public int energies;

    @Override
    public String toString() {
        String result = "";
        if (ones > 0)
            result += "ONE = " + ones + ", ";
        if (twos > 0)
            result += "TWO = " + twos + ", ";
        if (threes > 0)
            result += "THREE = " + threes + ", ";
        if (hearts > 0)
            result += "HEART = " + hearts + ", ";
        if (claws > 0)
            result += "CLAW = " + claws + ", ";
        if (energies > 0)
            result += "ENERGY = " + energies + ", ";
        if (result.length() > 2)
            result = result.substring(0, result.length() - 2);
        return "You rolled {" + result + "}";
    }
}
