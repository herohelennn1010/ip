package sophon;

import java.io.IOException;
import java.util.Scanner;

import sophon.exception.SophonException;
import sophon.logic.Command;
import sophon.logic.Parser;
import sophon.model.Task;
import sophon.model.TaskList;
import sophon.storage.Storage;
import sophon.ui.Ui;

/**
 * Entry point for the Sophon chatbot.
 */
public class Sophon {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final String startupWarning;

    /**
     * Creates a Sophon chatbot that saves tasks at the given path.
     *
     * @param first first part of the save file path.
     * @param more remaining parts of the save file path.
     */
    public Sophon(String first, String... more) {
        ui = new Ui();
        storage = new Storage(first, more);

        TaskList loadedTasks;
        String warning = null;
        try {
            loadedTasks = storage.loadTasks();
        } catch (IOException e) {
            warning = "I could not read the saved tasks.";
            loadedTasks = new TaskList();
        } catch (SophonException e) {
            warning = e.getMessage();
            loadedTasks = new TaskList();
        }

        tasks = loadedTasks;
        startupWarning = warning;
    }

    private void markTask(int taskIndex) throws SophonException, IOException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new SophonException("No task exists at that number.");
        }

        tasks.get(taskIndex).markAsDone();
        storage.saveTasks(tasks);
        ui.showMessage("Acknowledged. This task is now complete:\n"
                + "  " + tasks.get(taskIndex));
    }

    private void unmarkTask(int taskIndex) throws SophonException, IOException {
        if (!tasks.isValidIndex(taskIndex)) {
            throw new SophonException("No task exists at that number.");
        }

        tasks.get(taskIndex).markAsNotDone();
        storage.saveTasks(tasks);
        ui.showMessage("Reverted. This task is once again incomplete:");
        ui.showMessage("  " + tasks.get(taskIndex));
    }

    private void deleteTask(int taskIndex) throws SophonException, IOException {
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
     */
    public void run() {
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
                case FIND:
                    ui.showMatchingTasks(tasks.find(command.getKeyword()));
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
                    markTask(command.getTaskIndex());
                    break;
                case UNMARK:
                    unmarkTask(command.getTaskIndex());
                    break;
                case DELETE:
                    deleteTask(command.getTaskIndex());
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
                ui.showError("I could not save the task list.");
            }
            ui.showLine();
        }
    }

    /**
     * Starts Sophon with the default save file location.
     *
     * @param args command line arguments, currently unused.
     */
    public static void main(String[] args) {
        new Sophon("data", "sophon.txt").run();
    }
}
