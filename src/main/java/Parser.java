/**
 * Makes sense of user command text.
 */
public class Parser {
    /**
     * Parses a one-based task number from a command and returns its zero-based index.
     *
     * @param input full user command
     * @param commandWord command word before the task number
     * @param missingTaskMessage message to show if the task number is missing
     * @return zero-based task index
     * @throws SophonException if the task number is missing or not a numeral
     */
    public static int parseTaskIndex(String input, String commandWord, String missingTaskMessage) throws SophonException {
        String taskNumberText = input.substring(commandWord.length()).trim();
        if (taskNumberText.isBlank()) {
            throw new SophonException(missingTaskMessage);
        }

        try {
            return Integer.parseInt(taskNumberText) - 1;
        } catch (NumberFormatException e) {
            throw new SophonException("Task numbers must be written as numerals.");
        }
    }
}
