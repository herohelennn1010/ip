package sophon.model;

/**
 * Represents a task tracked by Sophon.
 */
public class Task {
    /** Details of the task shown to the user and saved to disk. */
    protected String description;

    /** Whether the task has been marked as completed. */
    protected boolean isDone;

    /** Category of task, used to choose the display icon. */
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
     * Checks whether this task's description contains the given keyword.
     *
     * @param keyword keyword to search for
     * @return true if the description contains the keyword
     */
    public boolean containsKeyword(String keyword) {
        return description.contains(keyword);
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

    /**
     * Returns this task in the format used by the save file.
     *
     * @return save file representation of this task
     */
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
