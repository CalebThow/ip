package chatbot.task;

/** Represents a task that must be completed by a specified time. */
public class Deadline extends Task {
    private final String by;

    /** Creates a pending deadline task. */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /** Returns the deadline time for persistence.
     *
     * @return the deadline time
     */
    public String getBy() {
        return by;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String getDisplayText() {
        return super.getDisplayText() + " (by: " + by + ")";
    }
}
