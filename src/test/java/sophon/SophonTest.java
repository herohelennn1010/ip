package sophon;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class SophonTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    public void getResponse_addCommands_returnsAddResponses() {
        Sophon sophon = new Sophon(temporaryDirectory.toString(), "tasks.txt");

        assertTrue(sophon.getResponse("todo read book")
                .startsWith("Recorded. A new task has entered observation:"));
        assertTrue(sophon.getResponse("deadline return book /by 2019-10-15")
                .startsWith("Recorded. A new deadline has entered observation:"));
        assertTrue(sophon.getResponse("event meeting /from 2019-10-15 /to 2019-10-16")
                .startsWith("Recorded. A new event has entered observation:"));
    }
}
