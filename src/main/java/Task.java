package ip;

/**
 * Represents a task in the task list.
 */
public class Task {
    private final String taskType;
    private final String description;
    private final String timing;
    private boolean isDone;

    /**
     * Creates a pending task with the given description.
     *
     * @param description the task description
     */
    public Task(String description) {
        this("T", description, "");
    }

    /**
     * Creates a task with a type and optional timing information.
     *
     * @param taskType the one-letter task type
     * @param description the task description
     * @param timing the formatted date/time information
     */
    public Task(String taskType, String description, String timing) {
        this.taskType = taskType;
        this.description = description;
        this.timing = timing;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the status icon used when displaying this task.
     *
     * @return X for a done task, or a space for a pending task
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the task description.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the task type and timing information used for display.
     *
     * @return the formatted task description
     */
    public String getDisplayText() {
        return description + timing;
    }

    /**
     * Returns the task type icon.
     *
     * @return the one-letter task type
     */
    public String getTaskType() {
        return taskType;
    }
}
