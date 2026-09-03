package ip;

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

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String getDisplayText() {
        return super.getDisplayText() + " (from: " + from + " to: " + to + ")";
    }
}
