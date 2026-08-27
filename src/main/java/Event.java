/**
 * Represents a task that occurs from one date or time to another.
 */
public class Event extends Task {

    private final String fromWhen;
    private final String toWhen;

    /**
     * Creates an event with the given description, start, and end.
     *
     * @param description details of the event
     * @param fromWhen when the event starts
     * @param toWhen when the event ends
     */
    public Event(String description, String fromWhen, String toWhen) {
        super(description, TaskType.EVENT);
        this.fromWhen = fromWhen;
        this.toWhen = toWhen;
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + fromWhen + " to: " + toWhen + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + fromWhen + " | " + toWhen;
    }
}
