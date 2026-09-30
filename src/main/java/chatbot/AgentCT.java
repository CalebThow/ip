package chatbot;

import java.util.List;
import java.util.Scanner;

import chatbot.exception.AgentCTException;
import chatbot.task.Task;
import chatbot.task.Todo;
import chatbot.task.Deadline;
import chatbot.task.Event;

/**
 * Runs the AgentCT command-line chatbot.
 */
public class AgentCT {
    /** Runs the command-line chatbot. */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data/agentct.txt");
        ui.showWelcome();

        List<Task> tasks = storage.load();
        Scanner scanner = new Scanner(System.in);
        runCommandLoop(scanner, tasks, ui, storage);
        scanner.close();
    }

    /** Processes chatbot commands until the user ends the session. */
    private static void runCommandLoop(Scanner scanner, List<Task> tasks, Ui ui, Storage storage) {
        int taskCount = tasks.size();
        String command;
        while ((command = ui.readCommand(scanner)) != null) {
            if (command.equals("bye")) {
                ui.showGoodbye();
                break;
            }
            try {
                taskCount = processCommand(command, tasks, taskCount, ui, storage);
            } catch (AgentCTException exception) {
                ui.showError(exception.getMessage());
                ui.showSeparator();
            }
        }
    }

    /** Processes one non-exit chatbot command and returns the updated task count. */
    private static int processCommand(String command, List<Task> tasks, int taskCount, Ui ui,
            Storage storage)
            throws AgentCTException {
        ui.showSeparator();

        if (command.equals("list")) {
            ui.showTaskList(tasks);
        } else if (command.matches("mark \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(5));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks.get(taskIndex).markAsDone();
                storage.save(tasks);
                ui.showTaskMarked(tasks.get(taskIndex));
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (command.matches("unmark \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(7));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks.get(taskIndex).markAsNotDone();
                storage.save(tasks);
                ui.showTaskUnmarked(tasks.get(taskIndex));
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (command.matches("delete \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(7));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                Task deletedTask = tasks.remove(taskIndex);
                taskCount--;
                storage.save(tasks);
                ui.showTaskDeleted(deletedTask, taskCount);
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring(4).trim();
            if (description.isEmpty()) {
                throw new AgentCTException(getMissingDescriptionMessage());
            } else {
                addTask(tasks, taskCount, new Todo(description), ui);
                storage.save(tasks);
                taskCount++;
            }
        } else if (command.startsWith("deadline ") && command.contains(" /by ")) {
            int markerIndex = command.indexOf(" /by ");
            String description = command.substring(9, markerIndex).trim();
            String by = command.substring(markerIndex + 5).trim();
            if (description.isEmpty()) {
                throw new AgentCTException(getMissingDescriptionMessage());
            } else {
                addTask(tasks, taskCount, new Deadline(description, by), ui);
                storage.save(tasks);
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
                addTask(tasks, taskCount, new Event(description, from, to), ui);
                storage.save(tasks);
                taskCount++;
            }
        } else {
            throw new AgentCTException(getInvalidCommandMessage());
        }
        ui.showSeparator();
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
                + "  unmark <number>\n"
                + "  delete <number>";
    }

    /** Adds a task and prints the confirmation shared by task commands. */
    private static void addTask(List<Task> tasks, int taskCount, Task task, Ui ui) {
        tasks.add(task);
        ui.showTaskAdded(task, taskCount + 1);
    }

}
    
