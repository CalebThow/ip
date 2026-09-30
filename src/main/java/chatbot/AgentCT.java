package chatbot;

import java.util.Scanner;

/** Runs the AgentCT command-line chatbot. */
public class AgentCT {
    /** Runs the command-line chatbot. */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data/agentct.txt");
        ui.showWelcome();

        TaskList tasks = new TaskList(storage.load());
        Scanner scanner = new Scanner(System.in);
        new CommandLoop(tasks, ui, storage).run(scanner);
        scanner.close();
    }
}
