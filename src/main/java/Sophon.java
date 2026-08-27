import java.io.IOException;
import java.util.Scanner;

/**
 * Entry point for the Sophon chatbot.
 */
public class Sophon {
    private static void markTask(TaskList tasks, Storage storage, Ui ui, int taskIndex)
            throws SophonException, IOException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new SophonException("No task exists at that number.");
        }

        tasks.get(taskIndex).markAsDone();
        storage.saveTasks(tasks);
        ui.showMessage("Acknowledged. This task is now complete:\n"
                + "  " + tasks.get(taskIndex));
    }

    private static void unmarkTask(TaskList tasks, Storage storage, Ui ui, int taskIndex)
            throws SophonException, IOException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new SophonException("No task exists at that number.");
        }

        tasks.get(taskIndex).markAsNotDone();
        storage.saveTasks(tasks);
        ui.showMessage("Reverted. This task is once again incomplete:");
        ui.showMessage("  " + tasks.get(taskIndex));
    }

    private static void deleteTask(TaskList tasks, Storage storage, Ui ui, int taskIndex)
            throws SophonException, IOException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new SophonException("No task exists at that number.");
        }

        Task removedTask = tasks.remove(taskIndex);
        storage.saveTasks(tasks);
        ui.showMessage("Removed. This task is no longer under observation:");
        ui.showMessage("  " + removedTask);
        ui.showMessage(tasks.size() + " tasks remain under observation.");
    }

    /**
     * Starts the chatbot and handles user commands until the user exits.
     *
     * @param args command line arguments, currently unused
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data", "sophon.txt");
        TaskList tasks;
        String startupWarning = null;
        try {
            tasks = storage.loadTasks();
        } catch (IOException e) {
            startupWarning = "I could not read the saved tasks.";
            tasks = new TaskList();
        } catch (SophonException e) {
            startupWarning = e.getMessage();
            tasks = new TaskList();
        }

        Scanner scanner = new Scanner(System.in);

        ui.showGreeting(startupWarning);

        while (true) {
            String input = scanner.nextLine();
            ui.showLine();

            try {
                Command command = Parser.parse(input);

                switch (command.getType()) {
                case BYE:
                    ui.showBye();
                    return;
                case LIST:
                    ui.showList(tasks);
                    break;
                case ADD_TODO:
                    tasks.add(command.getTask());
                    storage.saveTasks(tasks);
                    ui.showMessage("Recorded. A new task has entered observation:");
                    ui.showMessage("  " + command.getTask());
                    ui.showMessage(tasks.size() + " tasks are currently under observation.");
                    break;
                case ADD_DEADLINE:
                    tasks.add(command.getTask());
                    storage.saveTasks(tasks);
                    ui.showMessage("Recorded. A new deadline has entered observation:");
                    ui.showMessage("  " + command.getTask());
                    ui.showMessage(tasks.size() + " tasks are currently under observation.");
                    break;
                case ADD_EVENT:
                    tasks.add(command.getTask());
                    storage.saveTasks(tasks);
                    ui.showMessage("Recorded. A new event has entered observation:");
                    ui.showMessage("  " + command.getTask());
                    ui.showMessage(tasks.size() + " tasks are currently under observation.");
                    break;
                case MARK:
                    markTask(tasks, storage, ui, command.getTaskIndex());
                    break;
                case UNMARK:
                    unmarkTask(tasks, storage, ui, command.getTaskIndex());
                    break;
                case DELETE:
                    deleteTask(tasks, storage, ui, command.getTaskIndex());
                    break;
                case UNKNOWN:
                    ui.showMessage("Your message has been observed.\n"
                            + "Its meaning, however, remains unknown.");
                    break;
                default:
                    throw new SophonException("Your message has been observed.\n"
                            + "Its meaning, however, remains unknown.");
                }
            } catch (SophonException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError( "I could not save the task list.");
            }
            ui.showLine();
        }
    }
}
