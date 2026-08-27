package sophon.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import sophon.exception.SophonException;

public class ParserTest {
    @Test
    public void parseTaskIndex_validTaskNumbers_returnsZeroBasedIndex() throws SophonException {
        assertEquals(0, Parser.parseTaskIndex("mark 1", "mark", "Missing task number."));
        assertEquals(9, Parser.parseTaskIndex("mark 10", "mark", "Missing task number."));
        assertEquals(2, Parser.parseTaskIndex("mark     3     ", "mark", "Missing task number."));
    }

    @Test
    public void parseTaskIndex_missingTaskNumber_throwsExceptionWithGivenMessage() {
        SophonException exception = assertThrows(SophonException.class,
                () -> Parser.parseTaskIndex("mark", "mark", "Missing task number."));

        assertEquals("Missing task number.", exception.getMessage());
    }

    @Test
    public void parseTaskIndex_nonNumericTaskNumber_throwsException() {
        SophonException exception = assertThrows(SophonException.class,
                () -> Parser.parseTaskIndex("mark one", "mark", "Missing task number."));

        assertEquals("Task numbers must be written as numerals.", exception.getMessage());
    }

    @Test
    public void parse_findCommand_returnsFindCommandWithKeyword() throws SophonException {
        Command command = Parser.parse("find book");

        assertEquals(Command.Type.FIND, command.getType());
        assertEquals("book", command.getKeyword());
    }

    @Test
    public void parse_findCommandWithoutKeyword_throwsException() {
        SophonException exception = assertThrows(SophonException.class, () -> Parser.parse("find"));

        assertEquals("Tell me what signal to search for.", exception.getMessage());
    }
}
