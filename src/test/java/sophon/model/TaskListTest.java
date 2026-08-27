package sophon.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void isValidIndex_emptyList_returnsFalse() {
        TaskList empty = new TaskList();
        assertFalse(empty.isValidIndex(0));
        assertFalse(empty.isValidIndex(1));
        assertFalse(empty.isValidIndex(-1));
    }

    @Test
    public void isValidIndex_indexWithinList_returnsTrue() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("test1"));
        tasks.add(new Todo("test2"));

        assertTrue(tasks.isValidIndex(0));
        assertTrue(tasks.isValidIndex(1));
    }
}
