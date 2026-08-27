/**
 * Represents a task that occurs from one date or time to another.
 */
public class Event extends Task {

    /** Date or time when the event starts. */
    private final String fromWhen;

    /** Date or time when the event ends. */
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

    /**
     * Returns this event in the format shown to the user.
     *
     * @return display representation of this event
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + fromWhen + " to: " + toWhen + ")";
    }

    /**
     * Returns this event in the format used by the save file.
     *
     * @return save file representation of this event
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + fromWhen + " | " + toWhen;
    }
}
