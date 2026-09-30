package chatbot;

import java.util.Scanner;

import chatbot.exception.AgentCTException;

/** Runs the AgentCT command-line chatbot. */
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
        String command;
        Parser parser = new Parser();
        while ((command = ui.readCommand(scanner)) != null) {
            Command executableCommand = null;
            try {
                executableCommand = parser.parseCommand(command);
                if (!executableCommand.isExit()) {
                    ui.showSeparator();
                }
                executableCommand.execute(tasks, ui, storage);
            } catch (AgentCTException exception) {
                ui.showSeparator();
                ui.showError(exception.getMessage());
            } finally {
                if (executableCommand == null || !executableCommand.isExit()) {
                    ui.showSeparator();
                }
            }
            if (executableCommand != null && executableCommand.isExit()) {
                break;
            }
        }
    }
}
