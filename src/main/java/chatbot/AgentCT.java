package chatbot;

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

        TaskList tasks = new TaskList(storage.load());
        Scanner scanner = new Scanner(System.in);
        runCommandLoop(scanner, tasks, ui, storage);
        scanner.close();
    }

    /** Processes chatbot commands until the user ends the session. */
    private static void runCommandLoop(Scanner scanner, TaskList tasks, Ui ui, Storage storage) {
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
    private static int processCommand(String command, TaskList tasks, int taskCount, Ui ui,
            Storage storage)
            throws AgentCTException {
        ui.showSeparator();
        Parser.ParsedCommand parsedCommand = new Parser().parse(command);

        if (parsedCommand.getType() == Parser.CommandType.LIST) {
            ui.showTaskList(tasks.asList());
        } else if (parsedCommand.getType() == Parser.CommandType.MARK) {
            int taskNumber = parsedCommand.getTaskNumber();
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks.get(taskIndex).markAsDone();
                storage.save(tasks.asList());
                ui.showTaskMarked(tasks.get(taskIndex));
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (parsedCommand.getType() == Parser.CommandType.UNMARK) {
            int taskNumber = parsedCommand.getTaskNumber();
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                tasks.get(taskIndex).markAsNotDone();
                storage.save(tasks.asList());
                ui.showTaskUnmarked(tasks.get(taskIndex));
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (parsedCommand.getType() == Parser.CommandType.DELETE) {
            int taskNumber = parsedCommand.getTaskNumber();
            if (taskNumber >= 1 && taskNumber <= taskCount) {
                int taskIndex = taskNumber - 1;
                Task deletedTask = tasks.remove(taskIndex);
                taskCount--;
                storage.save(tasks.asList());
                ui.showTaskDeleted(deletedTask, taskCount);
            } else {
                System.out.println("     Sorry, that task number does not exist.");
            }
        } else if (parsedCommand.getType() == Parser.CommandType.TODO) {
            addTask(tasks, taskCount, new Todo(parsedCommand.getDescription()), ui);
            storage.save(tasks.asList());
            taskCount++;
        } else if (parsedCommand.getType() == Parser.CommandType.DEADLINE) {
            addTask(tasks, taskCount,
                    new Deadline(parsedCommand.getDescription(), parsedCommand.getFirstTime()), ui);
            storage.save(tasks.asList());
            taskCount++;
        } else if (parsedCommand.getType() == Parser.CommandType.EVENT) {
            addTask(tasks, taskCount, new Event(parsedCommand.getDescription(),
                    parsedCommand.getFirstTime(), parsedCommand.getSecondTime()), ui);
            storage.save(tasks.asList());
            taskCount++;
        }
        ui.showSeparator();
        return taskCount;
    }

    /** Adds a task and prints the confirmation shared by task commands. */
    private static void addTask(TaskList tasks, int taskCount, Task task, Ui ui) {
        tasks.add(task);
        ui.showTaskAdded(task, taskCount + 1);
    }

}
    
