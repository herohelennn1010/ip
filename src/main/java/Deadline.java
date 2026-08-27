/**
 * Represents a task that should be completed by a specific date or time.
 */
public class Deadline extends Task {

    /** Date or time by which the task should be completed. */
    private final String by;

    /**
     * Creates a deadline with the given description and due date or time.
     *
     * @param description details of the deadline
     * @param by when the deadline should be completed by
     */
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }

    /**
     * Returns this deadline in the format shown to the user.
     *
     * @return display representation of this deadline
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }

    /**
     * Returns this deadline in the format used by the save file.
     *
     * @return save file representation of this deadline
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
