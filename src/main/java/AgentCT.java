import java.util.Scanner;

/**
 * Runs the AgentCT command-line chatbot.
 */
public class AgentCT {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String banner =
                    "    _                    _    ____ _____\n" +
                    "   / \\   __ _  ___ _ __ | |_ / ___|_   _|\n" +
                    "  / _ \\ / _` |/ _ \\ '_ \\| __| |     | |\n" +
                    " / ___ \\ (_| |  __/ | | | |_  |___  | |\n" +
                    "/_/   \\_\\__, |\\___|_| |_|\\__|\\____| |_|\n" +
                    "        |___/";

        System.out.println(line);
        System.out.println(banner);
        System.out.println(line);
        System.out.println("Welcome! I'm AgentCT.");
        System.out.println("How may I help you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println("     " + command);
            System.out.println(line);

            if (command.equals("bye")) {
                System.out.println("     Goodbye! Hope you have an amazing day!");
                System.out.println(line);
                break;
            }
        }
        scanner.close();
    }
}
