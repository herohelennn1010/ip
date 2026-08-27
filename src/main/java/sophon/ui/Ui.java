package sophon.ui;

import sophon.model.TaskList;

/**
 * Handles console output shown to the user.
 */
public class Ui {
    private static final String INDENT = "     ";
    private static final String LINE = "____________________________________________________________";
    private static final String BANNER = " ____              _                 \n"
            + "/ ___|  ___  _ __ | |__   ___  _ __ \n"
            + "\\___ \\ / _ \\| '_ \\| '_ \\ / _ \\| '_ \\\n"
            + " ___) | (_) | |_) | | | | (_) | | | |\n"
            + "|____/ \\___/| .__/|_| |_|\\___/|_| |_|\n"
            + "            |_|                       \n";

    /**
     * Shows a horizontal divider line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Shows text using the standard Sophon indentation.
     *
     * @param text text to show.
     */
    public void showIndented(String text) {
        System.out.println(INDENT + text.replace("\n", "\n" + INDENT));
    }

    /**
     * Shows text exactly as given.
     *
     * @param text text to show.
     */
    public void showRaw(String text) {
        System.out.print(text);
    }

    /**
     * Shows Sophon's greeting and any startup warning.
     *
     * @param startupWarning warning to show after the greeting, or null if startup succeeded.
     */
    public void showGreeting(String startupWarning) {
        String greeting = "你好! I'm Sophon.\n"
                + "I'm listening.\n"
                + "What do you wish to communicate?";

        showLine();
        showRaw(BANNER);
        showIndented(greeting);
        if (startupWarning != null) {
            showIndented(startupWarning);
        }
        showLine();
    }

    /**
     * Shows Sophon's farewell message.
     */
    public void showBye() {
        String bye = "Our conversation ends here.\n"
                + "Until we meet again.";

        showIndented(bye);
        showLine();
    }

    /**
     * Shows an error message.
     *
     * @param message error message to show.
     */
    public void showError(String message) {
        showIndented(message);
    }

    /**
     * Shows a normal message.
     *
     * @param message message to show.
     */
    public void showMessage(String message) {
        showIndented(message);
    }

    /**
     * Shows all tasks currently tracked by Sophon.
     *
     * @param tasks tasks to show.
     */
    public void showList(TaskList tasks) {
        showIndented("Current tasks under observation:");
        for (int i = 0; i < tasks.size(); i++) {
            showIndented((i + 1) + "." + tasks.get(i));
        }
    }

    public void showMatchingTasks(TaskList tasks) {
        showIndented("These signals match your search:");
        for (int i = 0; i < tasks.size(); i++) {
            showIndented((i + 1) + "." + tasks.get(i));
        }
    }
}
