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
     * The function `quickSort` implements the quicksort algorithm to sort an array
     * of Player objects.
     * 
     * @param arr  The `arr` parameter is an array of `Player` objects that you want
     *             to sort using the
     *             quickSort algorithm.
     * @param low  The `low` parameter in the `quickSort` method represents the
     *             index of the first
     *             element in the array or subarray that you want to sort. It
     *             indicates the starting point for the
     *             sorting process within the array.
     * @param high The `high` parameter in the `quickSort` method represents the
     *             index of the last
     *             element in the array or subarray that you want to sort. It
     *             indicates the upper bound of the
     *             array or subarray that needs to be sorted.
     */
    public static void quickSort(Player[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    /**
     * The partition function takes an array of Player objects, selects a pivot
     * element based on
     * points, and rearranges the elements such that elements with higher points are
     * placed before the
     * pivot.
     * 
     * @param arr  An array of Player objects that you want to partition based on
     *             their points.
     * @param low  The `low` parameter represents the starting index of the array
     *             `arr` for the
     *             partitioning process. It indicates the lower bound of the
     *             subarray that needs to be partitioned.
     * @param high The `high` parameter in the `partition` method represents the
     *             index of the last
     *             element in the array or subarray that you want to partition. It
     *             indicates the upper bound of the
     *             range within which the partitioning should occur.
     * @return The partition method is returning the index of the pivot element
     *         after rearranging the
     *         array such that all elements with points greater than the pivot are
     *         on the left side and all
     *         elements with points less than the pivot are on the right side.
     */
    public static int partition(Player[] arr, int low, int high) {
        int pivot = arr[high].points;
        int i = low - 1;

        for (int j = low; i <= high; j++) {
            if (arr[j].points > pivot) {
                i++;
                Player tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        Player tmp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = tmp;

        return i + 1;
    }

    /**
     * The `listPlayers` function sorts and displays player information stored in an array.
     */
    static void listPlayers() {
        Player[] data = new Player[users.size()];
        data = users.toArray(data);

        quickSort(data, 0, users.size() - 1);

        for (Player i : users) {
            System.out.println(i.getId());
            System.out.println(i.username);
            System.out.println(i.points);
            System.out.println(" ");
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
        clearScreen();
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
                selected.points = userInput;
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

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}