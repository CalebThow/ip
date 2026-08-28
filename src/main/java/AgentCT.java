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

        String[] tasks = new String[100];
        int taskCount = 0;
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

            if (command.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("     " + (i + 1) + ". " + tasks[i]);
                }
            } else if (taskCount < tasks.length) {
                tasks[taskCount] = command;
                taskCount++;
                System.out.println("     added: " + command);
            }
            System.out.println(line);
        }
        scanner.close();
    }
}
