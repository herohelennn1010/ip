/**
 * Represents the type of task shown in the task list.
 */
public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String icon;

    /**
     * Creates a task type with the icon shown to the user.
     *
     * @param icon short label for this task type
     */
    TaskType(String icon) {
        this.icon = icon;
    }

    /**
     * Returns the icon shown before tasks of this type.
     *
     * @return task type icon
     */
    public String getIcon() {
        return icon;
    }
}
