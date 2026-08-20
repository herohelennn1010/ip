import java.util.Scanner;

/**
 * Entry point for the Sophon chatbot.
 */
public class Sophon {
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
        Task[] tasks = new Task[100];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.print(banner);
        System.out.println(indent + greeting.replace("\n", "\n" + indent));
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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(indent + (i + 1) + "." + tasks[i]);
                    }
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.length() == 4 ? "" : input.substring(5).trim();
                    if (description.isBlank()) {
                        throw new SophonException("You have given me nothing to observe.\n"
                                + "A todo requires a description.");
                    }
                    Todo todo = new Todo(description);
                    tasks[taskCount] = todo;
                    taskCount++;
                    System.out.println(indent + "Recorded. A new task has entered observation:");
                    System.out.println(indent + "  " + todo);
                    System.out.println(indent + taskCount + " tasks are currently under observation.");
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
                    Deadline deadline = new Deadline(description, by);
                    tasks[taskCount] = deadline;
                    taskCount++;
                    System.out.println(indent + "Recorded. A new deadline has entered observation:");
                    System.out.println(indent + "  " + deadline);
                    System.out.println(indent + taskCount + " tasks are currently under observation.");
                } else if (input.startsWith("event ")) {
                    int fromIndex = input.indexOf(" /from ");
                    int toIndex = input.indexOf(" /to ");
                    if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex
                            || input.substring(6, fromIndex).isBlank()
                            || input.substring(fromIndex + 7, toIndex).isBlank()
                            || input.substring(toIndex + 5).isBlank()) {
                        System.out.println(indent + "Event format: event DESCRIPTION /from START /to END");
                        System.out.println(line);
                        continue;
                    }
                    String description = input.substring(6, fromIndex);
                    String from = input.substring(fromIndex + 7, toIndex);
                    String to = input.substring(toIndex + 5);
                    Event event = new Event(description, from, to);
                    tasks[taskCount] = event;
                    taskCount++;
                    System.out.println(indent + "Recorded. A new event has entered observation:");
                    System.out.println(indent + "  " + event);
                    System.out.println(indent + taskCount + " tasks are currently under observation.");
                } else if (input.startsWith("mark ")) {
                    int taskNumber = Integer.parseInt(input.substring(5));
                    int taskIndex = taskNumber - 1;
                    tasks[taskIndex].markAsDone();
                    System.out.println(indent + "Acknowledged. This task is now complete:");
                    System.out.println(indent + "  " + tasks[taskIndex]);
                } else if (input.startsWith("unmark ")) {
                    int taskNumber = Integer.parseInt(input.substring(7));
                    int taskIndex = taskNumber - 1;
                    tasks[taskIndex].markAsNotDone();
                    System.out.println(indent + "Reverted. This task is once again incomplete:");
                    System.out.println(indent + "  " + tasks[taskIndex]);
                } else {
                    tasks[taskCount] = new Task(input);
                    taskCount++;
                    System.out.println(indent + "added: " + input);
                }
            } catch (SophonException e) {
                System.out.println(indent + e.getMessage().replace("\n", "\n" + indent));
            }
            System.out.println(line);
        }
    }
}
