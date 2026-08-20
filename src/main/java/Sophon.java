/**
 * Entry point for the Sophon chatbot.
 */
public class Sophon {
    /**
     * Prints the chatbot banner.
     *
     * @param args command line arguments, currently unused
     */
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String banner = " ____              _                 \n"
                + "/ ___|  ___  _ __ | |__   ___  _ __ \n"
                + "\\___ \\ / _ \\| '_ \\| '_ \\ / _ \\| '_ \\\n"
                + " ___) | (_) | |_) | | | | (_) | | | |\n"
                + "|____/ \\___/| .__/|_| |_|\\___/|_| |_|\n"
                + "            |_|                       \n";
        String greeting = "你好! I'm Sophon.\n"
                + "I'm listening.";
        String bye = "Our conversation ends here.\n"
                + "Until we meet again.";
        System.out.println(line);
        System.out.print(banner);
        System.out.println(greeting);
        System.out.println(line);
        System.out.println(bye);
        System.out.println(line);
    }
}
