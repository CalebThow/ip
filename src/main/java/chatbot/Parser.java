package chatbot;

import chatbot.exception.AgentCTException;
import chatbot.task.Deadline;
import chatbot.task.Event;
import chatbot.task.Todo;

/** Interprets user input and extracts arguments from supported commands. */
public class Parser {
    /** The command categories understood by the chatbot. */
    public enum CommandType {
        LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT
    }

    /** Parses commands that already have executable command objects.
     *
     * @param command raw user command
     * @return executable command, or null when the command still uses the legacy path
     */
    public Command parseCommand(String command) {
        if (command.equals("bye")) {
            return new ExitCommand();
        }
        if (command.equals("list")) {
            return new ListCommand();
        }
        ParsedCommand parsedCommand;
        try {
            parsedCommand = parse(command);
        } catch (AgentCTException exception) {
            return null;
        }
        if (parsedCommand.getType() == CommandType.TODO) {
            return new AddCommand(new Todo(parsedCommand.getDescription()));
        }
        if (parsedCommand.getType() == CommandType.DEADLINE) {
            return new AddCommand(new Deadline(parsedCommand.getDescription(),
                    parsedCommand.getFirstTime()));
        }
        if (parsedCommand.getType() == CommandType.EVENT) {
            return new AddCommand(new Event(parsedCommand.getDescription(),
                    parsedCommand.getFirstTime(), parsedCommand.getSecondTime()));
        }
        return null;
    }

    /** The result of parsing one user command. */
    public static class ParsedCommand {
        private final CommandType type;
        private final int taskNumber;
        private final String description;
        private final String firstTime;
        private final String secondTime;

        private ParsedCommand(CommandType type, int taskNumber, String description,
                String firstTime, String secondTime) {
            this.type = type;
            this.taskNumber = taskNumber;
            this.description = description;
            this.firstTime = firstTime;
            this.secondTime = secondTime;
        }

        public CommandType getType() {
            return type;
        }

        public int getTaskNumber() {
            return taskNumber;
        }

        public String getDescription() {
            return description;
        }

        public String getFirstTime() {
            return firstTime;
        }

        public String getSecondTime() {
            return secondTime;
        }
    }

    /** Parses a command and returns its type and arguments.
     *
     * @param command raw user command
     * @return parsed command
     * @throws AgentCTException when the command is unsupported or incomplete
     */
    public ParsedCommand parse(String command) throws AgentCTException {
        if (command.equals("list")) {
            return new ParsedCommand(CommandType.LIST, 0, null, null, null);
        }
        if (command.matches("mark \\d+")) {
            return numberedCommand(CommandType.MARK, command, 5);
        }
        if (command.matches("unmark \\d+")) {
            return numberedCommand(CommandType.UNMARK, command, 7);
        }
        if (command.matches("delete \\d+")) {
            return numberedCommand(CommandType.DELETE, command, 7);
        }
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring(4).trim();
            if (description.isEmpty()) {
                throw new AgentCTException(missingDescriptionMessage());
            }
            return new ParsedCommand(CommandType.TODO, 0, description, null, null);
        }
        if (command.startsWith("deadline ") && command.contains(" /by ")) {
            int markerIndex = command.indexOf(" /by ");
            String description = command.substring(9, markerIndex).trim();
            if (description.isEmpty()) {
                throw new AgentCTException(missingDescriptionMessage());
            }
            return new ParsedCommand(CommandType.DEADLINE, 0, description,
                    command.substring(markerIndex + 5).trim(), null);
        }
        if (command.startsWith("event ") && command.contains(" /from ")
                && command.contains(" /to ")) {
            int fromIndex = command.indexOf(" /from ");
            int toIndex = command.indexOf(" /to ", fromIndex);
            String description = command.substring(6, fromIndex).trim();
            if (description.isEmpty()) {
                throw new AgentCTException(missingDescriptionMessage());
            }
            return new ParsedCommand(CommandType.EVENT, 0, description,
                    command.substring(fromIndex + 7, toIndex).trim(),
                    command.substring(toIndex + 5).trim());
        }
        throw new AgentCTException(invalidCommandMessage());
    }

    private ParsedCommand numberedCommand(CommandType type, String command, int numberStart) {
        return new ParsedCommand(type, Integer.parseInt(command.substring(numberStart)),
                null, null, null);
    }

    private String missingDescriptionMessage() {
        return "Please provide a task description!\n"
                + "Format: todo <description>\n"
                + "Example: todo Play Video Games";
    }

    private String invalidCommandMessage() {
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
}
