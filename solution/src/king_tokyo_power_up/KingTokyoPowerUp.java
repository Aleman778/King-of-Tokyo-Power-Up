package king_tokyo_power_up;

import king_tokyo_power_up.game.DiceRoll;
import king_tokyo_power_up.game.card.DeckFactory;
import king_tokyo_power_up.game.monster.Monster;
import king_tokyo_power_up.game.server.GameServer;
import king_tokyo_power_up.game.util.Terminal;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * The main application class, contains the entry point.
 * The main menu is defined in this class.
 */
public class KingTokyoPowerUp {

    /**
     * Singleton instance to the main application class.
     */
    private static KingTokyoPowerUp application;

    /**
     * Menu option: hosts a new server.
     */
    private static final int MENU_HOST_SERVER = 1;

    /**
     * Menu option: starts a new client.
     */
    private static final int MENU_START_CLIENT = 2;

    /**
     * Menu option: starts a new bot client.
     */
    private static final int MENU_START_BOT_CLIENT = 3;

    /**
     * Menu option: exits the game.
     */
    private static final int MENU_EXIT_GAME = 4;

    /**
     * Scanner object is used to take user input.
     */
    private Terminal terminal;


    /**
     * Singleton class should not be instantiated outside class.
     */
    private KingTokyoPowerUp() {
        Scanner scanner = new Scanner(System.in);
        terminal = new Terminal("Main Menu", scanner);
    }


    /**
     * Entry point of the application, prints logo and opens the main menu.
     * @param args program arguments
     */
    public static void main(String[] args) {
        application = new KingTokyoPowerUp();
        //application.printLogo();
        //application.mainMenu();
        application.sandbox();
    }

    public void sandbox() {
        DeckFactory.createStoreDeck();
    }


    /**
     * Prints the King of Tokyo: Power Up logo.
     */
    public void printLogo() {
        String logo =
                "D7032E - Home Exam 2019                           %@@@@&&&&*                   \n" +
                "                                    .@@@@@@@@@&@@@@........%@@&                \n" +
                "                             .@@@@@@@@@.....*@@@..............@@               \n" +
                "                         @@@@@*.....(@@,.....@@.......@@......&@*              \n" +
                "                     @@@@#..@@#.......@%.....@@......*@%......@@,              \n" +
                "                 /@@@&......&@@........@.....@@......(@@@@@%*.@@               \n" +
                "              @@@@..@@/......@@&.............@@......@.....#&@@/               \n" +
                "           (@@@.....@@@,......@@,....,.......@@......@.......@@.               \n" +
                "         @@@(@@.....@@@@,.....&@@..../,......@@.....,@@,.,,,(@&                \n" +
                "       @@@....@.....@@@@@,...,,@@,,,,,@,,,,,,&@,,,,,/@%,,,,,@@                 \n" +
                "    .@@#,,,,,,,,,,,,,,%@@@,,,,/%@@@@@,@@,,,,,&@@,,,,,,,,,,,/@@                 \n" +
                "      *@@/,,,,,,,,,,,,,,*@@,@@,,**,%@#%@@&&&&@@@@%,,,,,*,,,@@*                 \n" +
                "        (@@*,,,,,,%&,,,,,,*@@@,&,@,,@@@@@@@@@@@@@@@@@@@@@(*@@@#                \n" +
                "          (@@,,,,,,*@@/@@@@@@@,#,#,/@,,*@@******@%*****&@@%***&@@@@@@@@*       \n" +
                "            @@@***&@@#*******%@@@@@@/**%@******@@@*****@@******/@&*****#@@@@.  \n" +
                "            &@@@@@(***********&@@******@******@@@@*****@******@********//((,,#,\n" +
                "        /@@@&**@@*****@@******#@@***********/@@@@@/****/****/@/*****///(#/%#&  \n" +
                "     @@@@/*****&******@@******%@@**********/@@@@@@%////////%@@//////@@(//((#%%%\n" +
                "  @@@&*****/@@@@//////&@//////%@@///////////(@@@@@@///////@@@//////@@%///(((@@&\n" +
                "@@%/////////@@@@//////#@#/////&@@/////#//////@@@@@@/////(@@@//////(@%/////(&@@ \n" +
                "@@//#@//////#@@@&//////@&/////@@@/////&&/////&@@@@#/////@@@&///(((@@((((((%@@, \n" +
                "*@@@@@&//////@@@@//////@%////(@@@/////@@((((((@@@@(((((@@@@((((((@@((((((%@@.  \n" +
                " @(. @@/////(%@@@&(((((((((&@@@@@@@@@@@@@@@@@@@&(((((@@@@(((((((@@((((((%@@.   \n" +
                "     ,@&((((((@@,@@@@@@@@@@@*              */&@@@@@@@&@@#@@((((#@((((((@@@.    \n" +
                "      @@((((((@@/                                  .*&@@.,@@((((((((((%@@.     \n" +
                "      ,@@%@@@@#*,               POWER UP                  .@@@#######@@&       \n" +
                "       @@@/,      Code Refactored by Alexander Mennborg      /@@@@@@@@     \n\n\n";
        System.out.println(logo);
    }


    /**
     * Main menu function prints the menu options and lets the user decide
     * what to do, can either start game as a server, client, bot client or exit.
     */
    public void mainMenu() {
        terminal.writeString("MainMenu: Select one option from 1 through 4\n");
        terminal.writeString("\t- 1: Host a new server\n");
        terminal.writeString("\t- 2: Connect to the server\n");
        terminal.writeString("\t- 3: Connect a bot the the server\n");
        terminal.writeString("\t- 4: Exit the game\n");
        int option = -1;
        while (option < 1) {
            option = terminal.readInt(1, 4, -1, "Not a valid option, please enter either 1, 2, 3 or 4.\n");
            if (option == -1)
                terminal.writeString("Please enter something, has to be either 1, 2, 3 or 4.\n");
        }

        switch(option) {
            case MENU_HOST_SERVER:
                GameServer server = new GameServer(terminal.getScanner());
                server.configure();
                server.start();
                break;
            case MENU_START_CLIENT:
                System.out.println("Starting client...");

                break;
            case MENU_START_BOT_CLIENT:
                System.out.println("Starting bot client... beep boop...");

                break;
            case MENU_EXIT_GAME:
                System.out.println("Bye bye");
                System.exit(0);
                break;
        }
    }
}
