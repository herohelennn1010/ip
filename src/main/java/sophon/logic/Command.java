package sophon.logic;

import sophon.model.Task;

/**
 * Represents a user command after it has been parsed.
 */
public class Command {
    /**
     * Categories of commands that Sophon can execute.
     */
    public enum Type {
        BYE,
        LIST,
        ADD_TODO,
        ADD_DEADLINE,
        ADD_EVENT,
        MARK,
        UNMARK,
        DELETE,
        UNKNOWN
    }

    private final Type type;
    private final Task task;
    private final int taskIndex;

    /**
     * Creates a command without extra data.
     *
     * @param type category of command.
     */
    public Command(Type type) {
        this(type, null, -1);
    }

    /**
     * Creates a command that contains a task.
     *
     * @param type category of command.
     * @param task task carried by the command.
     */
    public Command(Type type, Task task) {
        this(type, task, -1);
    }

    /**
     * Creates a command that refers to an existing task.
     *
     * @param type category of command.
     * @param taskIndex zero-based index of the task.
     */
    public Command(Type type, int taskIndex) {
        this(type, null, taskIndex);
    }

    private Command(Type type, Task task, int taskIndex) {
        this.type = type;
        this.task = task;
        this.taskIndex = taskIndex;
    }

    public Type getType() {
        return type;
    }

    public Task getTask() {
        return task;
    }

    public int getTaskIndex() {
        return taskIndex;
    }
}
