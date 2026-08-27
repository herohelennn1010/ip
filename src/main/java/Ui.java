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

    public void showLine() {
        System.out.println(LINE);
    }

    public void showIndented(String text) {
        System.out.println(INDENT + text.replace("\n", "\n" + INDENT));
    }

    public void showRaw(String text) {
        System.out.print(text);
    }

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

    public void showBye() {
        String bye = "Our conversation ends here.\n"
                + "Until we meet again.";

        showIndented(bye);
        showLine();
    }

    public void showError(String message) {
        showIndented(message);
    }

    public void showMessage(String message) {
        showIndented(message);
    }

    public void showList(TaskList tasks) {
        showIndented("Current tasks under observation:");
        for (int i = 0; i < tasks.size(); i++) {
            showIndented((i + 1) + "." + tasks.get(i));
        }
    }
}
