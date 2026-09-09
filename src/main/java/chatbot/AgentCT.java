package chatbot;

import java.util.ArrayList;
import java.util.List;
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

        List<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        runCommandLoop(scanner, tasks, separator);
        scanner.close();
    }

    /** Processes chatbot commands until the user ends the session. */
    private static void runCommandLoop(Scanner scanner, List<Task> tasks, String separator) {
        int taskCount = 0;
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                printGoodbye(separator);
                break;
            }
            try {
                taskCount = processCommand(command, tasks, taskCount, separator);
            } catch (AgentCTException exception) {
                printErrorMessage(exception.getMessage());
                System.out.println(separator);
            }
        }
    }

    /** Prints the response shown when the user ends the session. */
    private static void printGoodbye(String separator) {
        System.out.println(separator);
        System.out.println("     Goodbye! Hope you have an amazing day!");
        System.out.println(separator);
    }

    /** Processes one non-exit chatbot command and returns the updated task count. */
    private static int processCommand(String command, List<Task> tasks, int taskCount,
            String separator) throws AgentCTException {
        System.out.println(separator);

        if (command.equals("list")) {
            printTaskList(tasks);
        } else if (command.matches("mark \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(5));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks.get(taskIndex).markAsDone();
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       [X] " + tasks.get(taskIndex).getDescription());
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (command.matches("unmark \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(7));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks.get(taskIndex).markAsNotDone();
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       [ ] " + tasks.get(taskIndex).getDescription());
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring(4).trim();
            if (description.isEmpty()) {
                throw new AgentCTException(getMissingDescriptionMessage());
            } else {
                addTask(tasks, taskCount, new Todo(description));
                taskCount++;
            }
        } else if (command.startsWith("deadline ") && command.contains(" /by ")) {
            int markerIndex = command.indexOf(" /by ");
            String description = command.substring(9, markerIndex).trim();
            String by = command.substring(markerIndex + 5).trim();
            if (description.isEmpty()) {
                throw new AgentCTException(getMissingDescriptionMessage());
            } else {
                addTask(tasks, taskCount, new Deadline(description, by));
                taskCount++;
            }
        } else if (command.startsWith("event ") && command.contains(" /from ")
                && command.contains(" /to ")) {
            int fromIndex = command.indexOf(" /from ");
            int toIndex = command.indexOf(" /to ", fromIndex);
            String description = command.substring(6, fromIndex).trim();
            String from = command.substring(fromIndex + 7, toIndex).trim();
            String to = command.substring(toIndex + 5).trim();
            if (description.isEmpty()) {
                throw new AgentCTException(getMissingDescriptionMessage());
            } else {
                addTask(tasks, taskCount, new Event(description, from, to));
                taskCount++;
            }
        } else {
            throw new AgentCTException(getInvalidCommandMessage());
        }
        System.out.println(separator);
        return taskCount;
    }

    /** Returns guidance for a task command without a description. */
    private static String getMissingDescriptionMessage() {
        return "Please provide a task description!\n"
                + "Format: todo <description>\n"
                + "Example: todo Play Video Games";
    }

    /** Returns guidance for input that does not match a supported command. */
    private static String getInvalidCommandMessage() {
        return "Please enter a valid command!\n"
                + "Examples:\n"
                + "  todo <description>\n"
                + "  deadline <description> /by <time>\n"
                + "  event <description> /from <time> /to <time>\n"
                + "  list\n"
                + "  mark <number>\n"
                + "  unmark <number>";
    }

    /** Prints each line of an exception message with the chatbot's indentation. */
    private static void printErrorMessage(String message) {
        for (String line : message.split("\\R")) {
            System.out.println("     " + line);
        }
    }

    /** Prints all tasks in their numbered display format. */
    private static void printTaskList(List<Task> tasks) {
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println("     " + (i + 1) + ".[" + task.getTaskType() + "]["
                    + task.getStatusIcon() + "] " + task.getDisplayText());
        }
    }

    /** Adds a task and prints the confirmation shared by task commands. */
    private static void addTask(List<Task> tasks, int taskCount, Task task) {
        tasks.add(task);
        System.out.println("     Got it. I've added this task:");
        System.out.println("       [" + task.getTaskType() + "][ ] " + task.getDisplayText());
        System.out.println("     Now you have " + (taskCount + 1) + " tasks in the list.");
    }
}
    
