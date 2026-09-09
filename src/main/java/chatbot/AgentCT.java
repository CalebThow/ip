package chatbot;

import java.util.Scanner;

/**
 * Runs the AgentCT command-line chatbot.
 */
public class AgentCT {
    /** Runs the command-line chatbot. */
    public static void main(String[] args) {
        final String separator = "____________________________________________________________";
        String banner =
                    "    _                    _    ____ _____\n" +
                    "   / \\   __ _  ___ _ __ | |_ / ___|_   _|\n" +
                    "  / _ \\ / _` |/ _ \\ '_ \\| __| |     | |\n" +
                    " / ___ \\ (_| |  __/ | | | |_  |___  | |\n" +
                    "/_/   \\_\\__, |\\___|_| |_|\\__|\\____| |_|\n" +
                    "        |___/";

        System.out.println(separator);
        System.out.println(banner);
        System.out.println(separator);
        System.out.println("Welcome! I'm AgentCT.");
        System.out.println("How may I help you?");
        System.out.println(separator);

        Task[] tasks = new Task[100];
        Scanner scanner = new Scanner(System.in);
        runCommandLoop(scanner, tasks, separator);
        scanner.close();
    }

    /** Processes chatbot commands until the user ends the session. */
    private static void runCommandLoop(Scanner scanner, Task[] tasks, String separator) {
        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            taskCount = processCommand(command, tasks, taskCount, separator);
            if (taskCount < 0) {
                break;
            }
        }
    }

    /** Processes one chatbot command and returns the updated task count. */
    private static int processCommand(String command, Task[] tasks, int taskCount, String separator) {
        System.out.println("     " + command);
        System.out.println(separator);

        if (command.equals("bye")) {
            System.out.println("     Goodbye! Hope you have an amazing day!");
            System.out.println(separator);
            return -1;
        }

        if (command.equals("list")) {
            System.out.println("     Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println("     " + (i + 1) + ".[" + tasks[i].getTaskType() + "]["
                        + tasks[i].getStatusIcon() + "] " + tasks[i].getDisplayText());
            }
        } else if (command.matches("mark \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(5));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       [X] " + tasks[taskIndex].getDescription());
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (command.matches("unmark \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(7));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks[taskIndex].markAsNotDone();
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       [ ] " + tasks[taskIndex].getDescription());
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (taskCount < tasks.length && command.startsWith("todo ")) {
            addTask(tasks, taskCount, new Todo(command.substring(5)));
            taskCount++;
        } else if (taskCount < tasks.length && command.startsWith("deadline ")
                && command.contains(" /by ")) {
            int markerIndex = command.indexOf(" /by ");
            String description = command.substring(9, markerIndex);
            String by = command.substring(markerIndex + 5);
            addTask(tasks, taskCount, new Deadline(description, by));
            taskCount++;
        } else if (taskCount < tasks.length && command.startsWith("event ")
                && command.contains(" /from ") && command.contains(" /to ")) {
            int fromIndex = command.indexOf(" /from ");
            int toIndex = command.indexOf(" /to ", fromIndex);
            String description = command.substring(6, fromIndex);
            String from = command.substring(fromIndex + 7, toIndex);
            String to = command.substring(toIndex + 5);
            addTask(tasks, taskCount, new Event(description, from, to));
            taskCount++;
        } else if (taskCount < tasks.length) {
            tasks[taskCount] = new Task(command);
            taskCount++;
            System.out.println("     added: " + command);
        }
        System.out.println(separator);
        return taskCount;
    }

    /** Adds a task and prints the confirmation shared by task commands. */
    private static void addTask(Task[] tasks, int taskCount, Task task) {
        tasks[taskCount] = task;
        System.out.println("     Got it. I've added this task:");
        System.out.println("       [" + task.getTaskType() + "][ ] " + task.getDisplayText());
        System.out.println("     Now you have " + (taskCount + 1) + " tasks in the list.");
    }
}
    
