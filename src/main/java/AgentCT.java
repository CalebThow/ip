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
        boolean[] completed = new boolean[tasks.length];
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
                    String status = completed[i] ? "[X]" : "[ ]";
                    System.out.println("     " + (i + 1) + "." + status + " " + tasks[i]);
                }
            } else if (command.matches("mark \\d+")) {
                int taskNumber = Integer.parseInt(command.substring(5));
                if (taskNumber >= 1 && taskNumber <= taskCount) {
                    int taskIndex = taskNumber - 1;
                    completed[taskIndex] = true;
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       [X] " + tasks[taskIndex]);
                } else {
                    System.out.println("     Sorry, that task number does not exist.");
                }
            } else if (command.matches("unmark \\d+")) {
                int taskNumber = Integer.parseInt(command.substring(7));
                if (taskNumber >= 1 && taskNumber <= taskCount) {
                    int taskIndex = taskNumber - 1;
                    completed[taskIndex] = false;
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       [ ] " + tasks[taskIndex]);
                } else {
                    System.out.println("     Sorry, that task number does not exist.");
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
