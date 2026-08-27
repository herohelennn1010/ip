import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Entry point for the Sophon chatbot.
 */
public class Sophon {

    /**
     * Saves the current task list to the hard disk.
     *
     * @param tasks tasks to save
     * @throws IOException if the file cannot be written
     */
    private static void saveTasks(ArrayList<Task> tasks) throws IOException {
        Path filePath = Path.of("data", "sophon.txt");
        Files.createDirectories(filePath.getParent());

        ArrayList<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(task.toFileString());
        }

        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    /**
     * Parses the given tasks from saved tasks in disk.
     *
     * @param line line from the save file
     * @return task parsed from text
     * @throws SophonException if the line does not match the save file format
     */
    private static Task parseTask(String line) throws SophonException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new SophonException("The save file contains an incomplete task.");
        }

        String type = parts[0];
        String status = parts[1];
        String description = parts[2];

        if (!status.equals("0") && !status.equals("1")) {
            throw new SophonException("The save file contains an invalid task status.");
        } else if (description.isBlank()) {
            throw new SophonException("The save file contains an empty task description.");
        }

        Task task;
        if (type.equals("T")) {
            if (parts.length != 3) {
                throw new SophonException("The save file contains an invalid todo.");
            }
            task = new Todo(description);
        } else if (type.equals("D")) {
            if (parts.length != 4) {
                throw new SophonException("The save file contains an invalid deadline.");
            } else if (parts[3].isBlank()) {
                throw new SophonException("The save file contains an empty deadline time.");
            }
            task = new Deadline(description, parts[3]);
        } else if (type.equals("E")) {
            if (parts.length != 5) {
                throw new SophonException("The save file contains an invalid event.");
            } else if (parts[3].isBlank() || parts[4].isBlank()) {
                throw new SophonException("The save file contains an empty event time.");
            }
            task = new Event(description, parts[3], parts[4]);
        } else {
            throw new SophonException("The save file contains an unknown task type.");
        }

        if (status.equals("1")) {
            task.markAsDone();
        }

        return task;
    }

    /**
     * Loads tasks written in the disk.
     *
     * @return task lists parsed from disk file.
     * @throws IOException if the file cannot be read
     * @throws SophonException if the save file content is invalid
     */
    private static ArrayList<Task> loadTasks() throws IOException, SophonException {
        Path filePath = Path.of("data", "sophon.txt");
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks;
        }

        for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
            if (line.isBlank()) {
                continue;
            }

            tasks.add(parseTask(line));
        }

        return tasks;
    }

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
     * Starts the chatbot and handles user commands until the user exits.
     *
     * @param args command line arguments, currently unused
     */
    public static void main(String[] args) {
        String indent = "     ";
        String line = "____________________________________________________________";
        String banner = " ____              _                 \n"
                + "/ ___|  ___  _ __ | |__   ___  _ __ \n"
                + "\\___ \\ / _ \\| '_ \\| '_ \\ / _ \\| '_ \\\n"
                + " ___) | (_) | |_) | | | | (_) | | | |\n"
                + "|____/ \\___/| .__/|_| |_|\\___/|_| |_|\n"
                + "            |_|                       \n";
        String greeting = "你好! I'm Sophon.\n"
                + "I'm listening.\n"
                + "What do you wish to communicate?";
        String bye = "Our conversation ends here.\n"
                + "Until we meet again.";

        ArrayList<Task> tasks;
        String startupWarning = null;
        try {
            tasks = loadTasks();
        } catch (IOException e) {
            startupWarning = "I could not read the saved tasks.";
            tasks = new ArrayList<>();
        } catch (SophonException e) {
            startupWarning = e.getMessage();
            tasks = new ArrayList<>();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.print(banner);
        System.out.println(indent + greeting.replace("\n", "\n" + indent));
        if (startupWarning != null) {
            System.out.println(indent + startupWarning);
        }
        System.out.println(line);
        while (true) {
            String input = scanner.nextLine();

            System.out.println(line);

            if (input.equals("bye")) {
                System.out.println(indent + bye.replace("\n","\n" + indent));
                System.out.println(line);
                break;
            }

            try {
                if (input.equals("list")) {
                    System.out.println(indent + "Current tasks under observation:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(indent + (i + 1) + "." + tasks.get(i));
                    }
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.length() == 4 ? "" : input.substring(5).trim();
                    if (description.isBlank()) {
                        throw new SophonException("You have given me nothing to observe.\n"
                                + "A todo requires a description.");
                    }
                    checkFileSafe(description);
                    Todo todo = new Todo(description);
                    tasks.add(todo);
                    saveTasks(tasks);
                    System.out.println(indent + "Recorded. A new task has entered observation:");
                    System.out.println(indent + "  " + todo);
                    System.out.println(indent + tasks.size() + " tasks are currently under observation.");
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
                    Deadline deadline = new Deadline(description, by);
                    tasks.add(deadline);
                    saveTasks(tasks);
                    System.out.println(indent + "Recorded. A new deadline has entered observation:");
                    System.out.println(indent + "  " + deadline);
                    System.out.println(indent + tasks.size() + " tasks are currently under observation.");
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
                    Event event = new Event(description, from, to);
                    tasks.add(event);
                    saveTasks(tasks);
                    System.out.println(indent + "Recorded. A new event has entered observation:");
                    System.out.println(indent + "  " + event);
                    System.out.println(indent + tasks.size() + " tasks are currently under observation.");
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
                    saveTasks(tasks);
                    System.out.println(indent + "Acknowledged. This task is now complete:");
                    System.out.println(indent + "  " + tasks.get(taskIndex));
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
                    saveTasks(tasks);
                    System.out.println(indent + "Reverted. This task is once again incomplete:");
                    System.out.println(indent + "  " + tasks.get(taskIndex));
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
                    saveTasks(tasks);
                    System.out.println(indent + "Removed. This task is no longer under observation:");
                    System.out.println(indent + "  " + removedTask);
                    System.out.println(indent + tasks.size() + " tasks remain under observation.");
                } else {
                    System.out.println(indent + "Your message has been observed.\n"
                            + indent + "Its meaning, however, remains unknown.");
                }
            } catch (SophonException e) {
                System.out.println(indent + e.getMessage().replace("\n", "\n" + indent));
            } catch (IOException e) {
                System.out.println(indent + "I could not save the task list.");
            }
            System.out.println(line);
        }
    }
}
