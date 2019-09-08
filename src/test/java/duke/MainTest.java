package duke;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.exception.DukeException;
import duke.storage.StorageTest;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskList;
import duke.task.TaskListTest;
import duke.task.TaskTest;
import duke.task.Todo;
import duke.ui.UiTest;

class MainTest {

    @Test
    void testTask() throws DukeException {
        Todo todo = Todo.create("test todo");
        Event event = Event.create("test event national day /at 9/8/2019 1234");
        Deadline deadline = Deadline.create("test deadline submission /by 12/9/2019 1314");

        todo.markDone();
        deadline.markDone();

        assertEquals(todo.toString(), "[T][✓] test todo");
        assertEquals(event.toString(), "[E][✘] test event national day "
                + "(at: Friday 09 August 2019 12:34 PM)");
        assertEquals(deadline.toString(), "[D][✓] test deadline submission "
                + "(by: Thursday 12 September 2019 01:14 PM)");
        assertEquals(todo.export(), "T | 1 | 9 | test todo");
        assertEquals(event.export(), "E | 0 | 23 | test event national day | 13 | 9/8/2019 1234");
        assertEquals(deadline.export(), "D | 1 | 24 | test deadline submission | 14 | 12/9/2019 1314");
    }

    @Test
    void testTaskList() {
        TaskList tasks = new TaskList();

        assertEquals(tasks.size(), 0);
        assertThrows(IndexOutOfBoundsException.class, () -> tasks.delete(5));

        for (int i = 0; i < 10; i++) {
            tasks.add(new TaskTest());
        }

        assertEquals(tasks.size(), 10);

        tasks.markDone(5);
        tasks.markDone(10);

        assertEquals(tasks.get(5).toString(), "TaskTest: task done");
        assertEquals(tasks.get(10).toString(), "TaskTest: task done");

        tasks.delete(10);

        assertEquals(tasks.size(), 9);

    }

    @Test
    void testCommand() throws DukeException {
        StorageTest storageTest = new StorageTest();
        UiTest uiTest = new UiTest();
        TaskListTest taskListTest = new TaskListTest();

        Command command = new AddCommand(new TaskTest());
        command.execute(taskListTest, uiTest, storageTest);
        assertEquals(taskListTest.size(), 1);

        command = new DeleteCommand("1");
        command.execute(taskListTest, uiTest, storageTest);
        assertEquals(taskListTest.size(), 0);

    }
}