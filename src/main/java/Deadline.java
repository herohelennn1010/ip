/**
 * Represents a task that should be completed by a specific date or time.
 */
public class Deadline extends Task {

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

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by;
    }
}
