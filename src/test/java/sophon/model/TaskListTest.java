package sophon.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

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

    @Test
    public void find_keywordInTaskDescriptions_returnsMatchingTasks() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("read book"));
        tasks.add(new Deadline("return book", LocalDate.parse("2019-10-15")));
        tasks.add(new Event("project meeting", LocalDate.parse("2019-10-15"), LocalDate.parse("2019-10-16")));

        TaskList matchingTasks = tasks.find("book");

        assertEquals(2, matchingTasks.size());
        assertEquals("[T][ ] read book", matchingTasks.get(0).toString());
        assertEquals("[D][ ] return book (by: Oct 15 2019)", matchingTasks.get(1).toString());
    }
}
