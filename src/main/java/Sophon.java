import java.util.ArrayList;
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
        ArrayList<Task> tasks = new ArrayList<>();

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
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println(indent + (i + 1) + "." + tasks.get(i));
                    }
                } else if (input.equals("todo") || input.startsWith("todo ")) {
                    String description = input.length() == 4 ? "" : input.substring(5).trim();
                    if (description.isBlank()) {
                        throw new SophonException("You have given me nothing to observe.\n"
                                + "A todo requires a description.");
                    }
                    Todo todo = new Todo(description);
                    tasks.add(todo);
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
                    Deadline deadline = new Deadline(description, by);
                    tasks.add(deadline);
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
                    Event event = new Event(description, from, to);
                    tasks.add(event);
                    System.out.println(indent + "Recorded. A new event has entered observation:");
                    System.out.println(indent + "  " + event);
                    System.out.println(indent + tasks.size() + " tasks are currently under observation.");
                } else if (input.startsWith("mark ")) {
                    int taskNumber = Integer.parseInt(input.substring(5));
                    int taskIndex = taskNumber - 1;
                    tasks.get(taskIndex).markAsDone();
                    System.out.println(indent + "Acknowledged. This task is now complete:");
                    System.out.println(indent + "  " + tasks.get(taskIndex));
                } else if (input.startsWith("unmark ")) {
                    int taskNumber = Integer.parseInt(input.substring(7));
                    int taskIndex = taskNumber - 1;
                    tasks.get(taskIndex).markAsNotDone();
                    System.out.println(indent + "Reverted. This task is once again incomplete:");
                    System.out.println(indent + "  " + tasks.get(taskIndex));
                } else {
                    System.out.println(indent + "Your message has been observed.\n"
                            + indent + "Its meaning, however, remains unknown.");
                }
            } catch (SophonException e) {
                System.out.println(indent + e.getMessage().replace("\n", "\n" + indent));
            }
            System.out.println(line);
        }
    }
}
