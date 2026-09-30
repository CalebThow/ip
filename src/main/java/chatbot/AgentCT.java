package chatbot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chatbot.exception.AgentCTException;
import chatbot.task.Deadline;
import chatbot.task.Event;
import chatbot.task.Task;
import chatbot.task.Todo;

/**
 * Runs the AgentCT command-line chatbot.
 */
public class AgentCT {
    private static final Path SAVE_FILE = Path.of("data", "agentct.txt");

    /** Runs the command-line chatbot. */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        List<Task> tasks = loadTasks();
        Scanner scanner = new Scanner(System.in);
        runCommandLoop(scanner, tasks, ui);
        scanner.close();
    }

    /** Processes chatbot commands until the user ends the session. */
    private static void runCommandLoop(Scanner scanner, List<Task> tasks, Ui ui) {
        int taskCount = tasks.size();
        String command;
        while ((command = ui.readCommand(scanner)) != null) {
            if (command.equals("bye")) {
                ui.showGoodbye();
                break;
            }
            try {
                taskCount = processCommand(command, tasks, taskCount, ui);
            } catch (AgentCTException exception) {
                ui.showError(exception.getMessage());
                ui.showSeparator();
            }
        }
    }

    /** Processes one non-exit chatbot command and returns the updated task count. */
    private static int processCommand(String command, List<Task> tasks, int taskCount, Ui ui)
            throws AgentCTException {
        ui.showSeparator();

        if (command.equals("list")) {
            ui.showTaskList(tasks);
        } else if (command.matches("mark \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(5));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks.get(taskIndex).markAsDone();
                saveTasks(tasks);
                ui.showTaskMarked(tasks.get(taskIndex));
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (command.matches("unmark \\d+")) {
            int taskNumber = Integer.parseInt(command.substring(7));
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks.get(taskIndex).markAsNotDone();
                saveTasks(tasks);
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
                saveTasks(tasks);
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
                saveTasks(tasks);
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
                saveTasks(tasks);
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
                saveTasks(tasks);
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

    /** Saves the current task list after a successful change.
     *
     * @param tasks the current task list
     */
    private static void saveTasks(List<Task> tasks) {
        try {
            Files.createDirectories(SAVE_FILE.getParent());
            List<String> lines = tasks.stream().map(AgentCT::formatTaskForStorage).toList();
            Files.write(SAVE_FILE, lines, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            System.err.println("Warning: Unable to save tasks to " + SAVE_FILE + ".");
        }
    }

    /** Loads saved tasks when the chatbot starts. Missing or malformed files are ignored.
     *
     * @return the saved tasks, or an empty list when no usable save data exists
     */
    private static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(SAVE_FILE)) {
            return tasks;
        }
        try {
            for (String line : Files.readAllLines(SAVE_FILE, StandardCharsets.UTF_8)) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException exception) {
            return tasks;
        }
        return tasks;
    }

    /** Converts one saved line into a task.
     *
     * @param line the saved task line
     * @return the parsed task, or null for malformed data
     */
    private static Task parseTask(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] fields = splitStorageLine(line);
        if (fields.length < 3 || fields[2].isBlank()
                || (!fields[1].equals("0") && !fields[1].equals("1"))) {
            return null;
        }
        Task task;
        if (fields[0].equals("T") && fields.length == 3) {
            task = new Todo(fields[2]);
        } else if (fields[0].equals("D") && fields.length == 4 && !fields[3].isBlank()) {
            task = new Deadline(fields[2], fields[3]);
        } else if (fields[0].equals("E") && fields.length == 5
                && !fields[3].isBlank() && !fields[4].isBlank()) {
            task = new Event(fields[2], fields[3], fields[4]);
        } else {
            return null;
        }
        if (fields[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    /** Splits a storage line while allowing escaped pipes and backslashes in text.
     *
     * @param line the storage line
     * @return the decoded fields
     */
    private static String[] splitStorageLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean isEscaped = false;
        for (char character : line.toCharArray()) {
            if (isEscaped) {
                field.append(character);
                isEscaped = false;
            } else if (character == '\\') {
                isEscaped = true;
            } else if (character == '|') {
                fields.add(field.toString().trim());
                field.setLength(0);
            } else {
                field.append(character);
            }
        }
        if (isEscaped) {
            field.append('\\');
        }
        fields.add(field.toString().trim());
        return fields.toArray(String[]::new);
    }

    /** Formats a task as one pipe-delimited line for the save file.
     *
     * @param task the task to format
     * @return the storage representation of the task
     */
    private static String formatTaskForStorage(Task task) {
        StringBuilder line = new StringBuilder(task.getTaskType())
                .append(" | ").append(task.isDone() ? "1" : "0")
                .append(" | ").append(escapeStorageValue(task.getDescription()));
        if (task instanceof Deadline deadline) {
            line.append(" | ").append(escapeStorageValue(deadline.getBy()));
        } else if (task instanceof Event event) {
            line.append(" | ").append(escapeStorageValue(event.getFrom()))
                    .append(" | ").append(escapeStorageValue(event.getTo()));
        }
        return line.toString();
    }

    /** Escapes characters that have meaning in the storage format.
     *
     * @param value the value to escape
     * @return the escaped value
     */
    private static String escapeStorageValue(String value) {
        return value.replace("\\", "\\\\").replace("|", "\\|");
    }
}
    
