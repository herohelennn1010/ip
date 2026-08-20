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
        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
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

            if (input.equals("list")) {
                System.out.println(indent + "Current tasks under observation:");
                for (int i = 0; i < taskCount; i++) {
                    String status = isDone[i] ? "[X]" : "[ ]";
                    System.out.println(indent + (i + 1) + "." + status + " " + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5));
                int taskIndex = taskNumber - 1;
                isDone[taskIndex] = true;
                System.out.println(indent + "Acknowledged. This task is now complete:");
                System.out.println(indent + "  [X] " + tasks[taskIndex]);
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println(indent + "added: " + input);
            }
            System.out.println(line);
        }
    }
}
