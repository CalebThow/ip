package chatbot.task;

/** Represents a task with a start time and an end time. */
public class Event extends Task {
    private final String from;
    private final String to;

    /** Creates a pending event task. */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /** Returns the event start time for persistence.
     *
     * @return the event start time
     */
    public String getFrom() {
        return from;
    }

    /** Returns the event end time for persistence.
     *
     * @return the event end time
     */
    public String getTo() {
        return to;
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String getDisplayText() {
        return super.getDisplayText() + " (from: " + from + " to: " + to + ")";
    }
}
