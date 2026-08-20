/**
 * Represents a task tracked by Sophon.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    private final TaskType type;

    /**
     * Creates a task with the given description.
     *
     * @param description details of the task
     * @param type type of task being tracked
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.type = type;
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
     * Returns the icon shown for this task's completion status.
     *
     * @return X if the task is done, or a space otherwise
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns this task in the format shown to the user.
     *
     * @return task status and description
     */
    @Override
    public String toString() {
        return "[" + type.getIcon() + "][" + getStatusIcon() + "] " + description;
    }
}
