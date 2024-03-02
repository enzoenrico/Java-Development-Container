import java.util.ArrayList;
import java.util.Scanner;

/**
 * The `Runner` class in Java manages a list of players, allows users to add
 * players, see scores,
 * update scores, or quit the program based on user input.
 */
class Runner {
    public static ArrayList<Player> users;

    public static boolean playing = true;

    public static void main() {
        users = new ArrayList<Player>();
        Scanner input = new Scanner(System.in);
        while (playing) {
            System.out.println(
                    "[!]To add a player type N\n[+]To see scores type S\n[+]To update a user score type A\n[+]Type Q to quit");
            String command = input.nextLine();
            processInput(command, input);
        }
        input.close();
    }

    /**
     * The `listPlayers` function iterates through a list of players and prints
     * their ID, username, and
     * points.
     */
    static void listPlayers() {
        for (Player pl : users) {
            System.out.println(
                    String.format("[Player %d] - %s | Points: %d\n", pl.getId(), pl.username,
                            pl.points));
        }
    }

    // The `processInput` method in the `Runner` class takes two parameters:
    // `command` which represents
    // the user input command and `input` which is a `Scanner` object for reading
    // user input.
    static void processInput(String command, Scanner input) {
        if (command.strip() == null) {
            System.out.println("[!]Write a valid string!!!!");
            return;
        }
        switch (command.toUpperCase()) {
            case "N": {
                System.out.println("[+]Insert player's name");
                String newUsername = input.nextLine();
                // created player here
                try {
                    Player thispl = new Player(newUsername);
                    users.add(thispl);

                    System.out.println(
                            String.format("[+]Added player %s...\nPoints: %d\n", thispl.username, thispl.points));

                    for (Player pl : users) {
                        System.out.println(
                                String.format("[Player %d] - %s | Points: %d\n", pl.getId(), pl.username,
                                        pl.points));
                    }

                    return;

                } catch (Exception e) {
                    System.err.println(e);
                }
            }

            case "S": {
                listPlayers();
                break;
            }

            case "A": {
                System.out.println("[+]Select a user ID from the list below to update it's points");
                listPlayers();
                Integer userInput = input.nextInt();
                Player selected = null;
                for (Player s : users) {
                    if (s.getId() == userInput) {
                        selected = s;
                        break;
                    }
                }
                if (selected != null) {
                    System.out.println(
                            String.format("[Selected] ->[UserID %d] - %s | Points: %d\n", selected.getId(),
                                    selected.username,
                                    selected.points));
                }
                System.out.println("[+]Select new amount of point to assign to user");
                userInput = input.nextInt();
                ArrayList<Integer> h = selected.appendHistory(userInput);
                System.out.println(
                        String.format("[User %d 's history of points] \n", selected.getId()));
                for (Integer i : h) {
                    System.out.println(String.format("[Score]: %d", i));
                }
                break;
            }

            case "Q": {
                playing = false;
                break;
            }
        }
    }
}