import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Entry point for the Sophon chatbot.
 */
public class Sophon {

    /**
     * Checks whether text can be written safely in the save file format.
     *
     * @param text text to check
     * @throws SophonException if the text contains the file separator
     */
    private static void checkFileSafe(String text) throws SophonException {
        if (text.contains(" | ")) {
            throw new SophonException("Please do not use \" | \" in task details.");
        }
    }

    /**
     * Converts text in yyyy-MM-dd format into a date.
     *
     * @param text date text to convert
     * @return date represented by the text
     * @throws SophonException if the text is not in yyyy-MM-dd format
     */
    private static LocalDate convertDate(String text) throws SophonException {
        try {
            return LocalDate.parse(text);
        } catch (DateTimeParseException e) {
            throw new SophonException("Please enter the date in yyyy-MM-dd format.");
        }
    }

    /**
     * Starts the chatbot and handles user commands until the user exits.
     *
     * @param args command line arguments, currently unused
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data", "sophon.txt");
        ArrayList<Task> tasks;
        String startupWarning = null;
        try {
            tasks = storage.loadTasks();
        } catch (IOException e) {
            startupWarning = "I could not read the saved tasks.";
            tasks = new ArrayList<>();
        } catch (SophonException e) {
            startupWarning = e.getMessage();
            tasks = new ArrayList<>();
        }

        Scanner scanner = new Scanner(System.in);

        ui.showGreeting(startupWarning);

        while (true) {
            String input = scanner.nextLine();
            ui.showLine();

            if (input.equals("bye")) {
                ui.showBye();
                break;
            }

            try {
                if (input.equals("list")) {
                    ui.showList(tasks);
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.length() == 4 ? "" : input.substring(5).trim();
                    if (description.isBlank()) {
                        throw new SophonException("You have given me nothing to observe.\n"
                                + "A todo requires a description.");
                    }
                    checkFileSafe(description);
                    Todo todo = new Todo(description);
                    tasks.add(todo);
                    storage.saveTasks(tasks);
                    ui.showMessage("Recorded. A new task has entered observation:");
                    ui.showMessage("  " + todo);
                    ui.showMessage(tasks.size() + " tasks are currently under observation.");
                } else if (input.equals("deadline") || input.startsWith("deadline ")) {
                    String details = input.length() == 8 ? "" : input.substring(8).trim();
                    int byIndex = details.indexOf("/by");
                    if (details.isBlank()) {
                        throw new SophonException("You have told me neither what must be done nor when.\n"
                                + "A deadline requires both.");
                    } else if (byIndex == -1) {
                        throw new SophonException("I know what must be done, but not when.\n"
                                + "Specify when it is due using /by.");
                    }
                    String description = details.substring(0, byIndex).trim();
                    String by = details.substring(byIndex + 3).trim();
                    if (description.isBlank() && by.isBlank()) {
                        throw new SophonException("You have given me a boundary, but nothing to bind to it.\n"
                                + "Tell me what must be done, and when.");
                    } else if (description.isBlank()) {
                        throw new SophonException("I know when, but not what.\n"
                                + "Give the deadline a description.");
                    } else if (by.isBlank()) {
                        throw new SophonException("I see the task, but its deadline remains unknown.\n"
                                + "Tell me when it is due.");
                    }
                    checkFileSafe(description);
                    checkFileSafe(by);
                    LocalDate byTime = convertDate(by);

                    Deadline deadline = new Deadline(description, byTime);
                    tasks.add(deadline);
                    storage.saveTasks(tasks);
                    ui.showMessage("Recorded. A new deadline has entered observation:");
                    ui.showMessage("  " + deadline);
                    ui.showMessage(tasks.size() + " tasks are currently under observation.");
                } else if (input.equals("event") || input.startsWith("event ")) {
                    String details = input.length() == 5 ? "" : input.substring(5).trim();
                    int fromIndex = details.indexOf("/from");
                    int toIndex = details.indexOf("/to");
                    if (details.isBlank()) {
                        throw new SophonException("You have told me neither what will happen nor when.\n"
                                + "An event requires both.");
                    } else if (fromIndex == -1 && toIndex == -1) {
                        throw new SophonException("I know what will happen, but not when.\n"
                                + "Tell me when it begins and when it ends.");
                    } else if (fromIndex == -1) {
                        throw new SophonException("I see when it ends, but not when it begins.\n"
                                + "Tell me when it begins.");
                    } else if (toIndex == -1 || toIndex < fromIndex) {
                        throw new SophonException("I see when it begins, but not when it ends.\n"
                                + "Specify an end time using /to.");
                    }
                    String description = details.substring(0, fromIndex).trim();
                    String from = details.substring(fromIndex + 5, toIndex).trim();
                    String to = details.substring(toIndex + 3).trim();
                    if (description.isBlank()) {
                        throw new SophonException("I know when, but not what.\n"
                                + "Give the event a description.");
                    } else if (from.isBlank()) {
                        throw new SophonException("I see when it ends, but not when it begins.\n"
                                + "Tell me when it begins.");
                    } else if (to.isBlank()) {
                        throw new SophonException("I see when it begins, but its end remains unknown.\n"
                                + "Tell me when it ends.");
                    }
                    checkFileSafe(description);
                    checkFileSafe(from);
                    checkFileSafe(to);
                    LocalDate fromTime = convertDate(from);
                    LocalDate toTime = convertDate(to);
                    Event event = new Event(description, fromTime, toTime);
                    tasks.add(event);
                    storage.saveTasks(tasks);
                    ui.showMessage("Recorded. A new event has entered observation:");
                    ui.showMessage("  " + event);
                    ui.showMessage(tasks.size() + " tasks are currently under observation.");
                } else if (input.equals("mark") || input.startsWith("mark ")) {
                    String taskNumberText = input.length() == 4 ? "" : input.substring(5).trim();
                    if (taskNumberText.isBlank()) {
                        throw new SophonException("Tell me which task has completed its observation.");
                    }

                    int taskNumber;
                    try {
                        taskNumber = Integer.parseInt(taskNumberText);
                    } catch (NumberFormatException e) {
                        throw new SophonException("Task numbers must be written as numerals.");
                    }

                    int taskIndex = taskNumber - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new SophonException("No task exists at that number.");
                    }

                    tasks.get(taskIndex).markAsDone();
                    storage.saveTasks(tasks);
                    ui.showMessage("Acknowledged. This task is now complete:");
                    ui.showMessage("  " + tasks.get(taskIndex));
                } else if (input.equals("unmark") || input.startsWith("unmark ")) {
                    String taskNumberText = input.length() == 6 ? "" : input.substring(7).trim();
                    if (taskNumberText.isBlank()) {
                        throw new SophonException("Tell me which task has returned to observation.");
                    }

                    int taskNumber;
                    try {
                        taskNumber = Integer.parseInt(taskNumberText);
                    } catch (NumberFormatException e) {
                        throw new SophonException("Task numbers must be written as numerals.");
                    }

                    int taskIndex = taskNumber - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new SophonException("No task exists at that number.");
                    }

                    tasks.get(taskIndex).markAsNotDone();
                    storage.saveTasks(tasks);
                    ui.showMessage("Reverted. This task is once again incomplete:");
                    ui.showMessage("  " + tasks.get(taskIndex));
                } else if (input.equals("delete") || input.startsWith("delete ")) {
                    String taskNumberText = input.length() == 6 ? "" : input.substring(7).trim();
                    if (taskNumberText.isBlank()) {
                        throw new SophonException("Tell me which task to remove.");
                    }

                    int taskNumber;
                    try {
                        taskNumber = Integer.parseInt(taskNumberText);
                    } catch (NumberFormatException e) {
                        throw new SophonException("Task numbers must be written as numerals.");
                    }

                    int taskIndex = taskNumber - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        throw new SophonException("No task exists at that number.");
                    }

                    Task removedTask = tasks.remove(taskIndex);
                    storage.saveTasks(tasks);
                    ui.showMessage("Removed. This task is no longer under observation:");
                    ui.showMessage("  " + removedTask);
                    ui.showMessage(tasks.size() + " tasks remain under observation.");
                } else {
                    ui.showMessage("Your message has been observed.\n"
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
