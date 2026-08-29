package jarvis.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import jarvis.classes.Deadline;
import jarvis.classes.Event;
import jarvis.classes.Task;
import jarvis.classes.ToDo;
import jarvis.storage.Storage;

/** Tests task-list operations. */
public class TaskListTest {
    /** Temporary directory used for test storage. */
    @TempDir
    Path tempDir;

    /** Task list used by each test. */
    private TaskList taskList;

    /**
     * Creates a task list using a temporary storage file before each test.
     */
    @BeforeEach
    public void setUp() {
        Path dataFile = tempDir.resolve("jarvis.txt");
        Storage storage = new Storage(dataFile.toString());
        taskList = new TaskList(Storage.loadTasks());
    }

    /** Verifies that tasks can be added, retrieved, and counted. */
    @Test
    public void addTask_taskIsStored() {
        TaskList taskList = new TaskList();

        ToDo todoTask = new ToDo("Read book");
        taskList.addTask(todoTask);

        assertEquals(1, taskList.size());

        assertEquals(todoTask, taskList.getTask(0));
        assertEquals(todoTask, taskList.getTasks().get(0));

        Deadline deadlineTask = new Deadline("Return book", LocalDateTime.of(2026, 8, 22, 23, 45));
        taskList.addTask(deadlineTask);

        assertEquals(2, taskList.size());

        assertEquals(deadlineTask, taskList.getTask(1));
        assertEquals(deadlineTask, taskList.getTasks().get(1));
    }

    /** Verifies that deleting a task returns it and removes it from the list. */
    @Test
    public void deleteTask_taskIsRemoved() {
        TaskList taskList = new TaskList();

        ToDo todoTask = new ToDo("Read book");
        Deadline deadlineTask = new Deadline("Return book", LocalDateTime.of(2026, 8, 22, 23, 45));

        taskList.addTask(todoTask);
        taskList.addTask(deadlineTask);

        taskList.deleteTask(0);
        assertEquals(1, taskList.size());

        assertEquals(deadlineTask, taskList.getTask(0));
        assertEquals(deadlineTask, taskList.getTasks().get(0));

        taskList.deleteTask(0);
        assertEquals(0, taskList.size());

    }

    /** Verifies that completion status updates are applied to the selected task. */
    @Test
    public void setCompletionStatus_taskStatusIsUpdated() {
        TaskList taskList = new TaskList();

        ToDo todoTask = new ToDo("Read book");
        Deadline deadlineTask = new Deadline("Return book", LocalDateTime.of(2026, 8, 22, 23, 45));

        taskList.addTask(todoTask);
        taskList.addTask(deadlineTask);

        taskList.setCompletionStatus(0, Task.CompletionStatus.DONE);
        taskList.setCompletionStatus(1, Task.CompletionStatus.DONE);

        assertEquals(Task.CompletionStatus.DONE, taskList.getTask(0).getStatus());
        assertEquals(Task.CompletionStatus.DONE, taskList.getTask(1).getStatus());

        taskList.setCompletionStatus(0, Task.CompletionStatus.UNDONE);
        taskList.setCompletionStatus(1, Task.CompletionStatus.UNDONE);

        assertEquals(Task.CompletionStatus.UNDONE, taskList.getTask(0).getStatus());
        assertEquals(Task.CompletionStatus.UNDONE, taskList.getTask(1).getStatus());
    }

    /** Verifies that tasks are filtered correctly based on key word. */
    @Test
    public void filterTasks_tasksAreFilteredCorrectly() {
        TaskList taskList = new TaskList();
        List<Task> testCase1TaskList = new ArrayList<>();
        List<Task> testCase2TaskList = new ArrayList<>();
        List<Task> testCase3TaskList = new ArrayList<>();
        List<Task> testCase4TaskList = new ArrayList<>();

        ToDo todoTask1 = new ToDo("Read book");
        Deadline deadlineTask = new Deadline("Do Coding", LocalDateTime.of(2026, 8, 22, 23, 45));
        ToDo todoTask2 = new ToDo("Check Code");

        taskList.addTask(todoTask1);
        taskList.addTask(deadlineTask);
        taskList.addTask(todoTask2);

        testCase1TaskList.add(deadlineTask);
        testCase2TaskList.add(todoTask1);

        assertEquals(testCase1TaskList, taskList.filterTasks("do"));
        assertEquals(testCase1TaskList, taskList.filterTasks("coding"));

        testCase1TaskList.add(todoTask2);

        assertEquals(testCase1TaskList, taskList.filterTasks("cod"));

        assertEquals(testCase2TaskList, taskList.filterTasks("book"));
        assertEquals(testCase2TaskList, taskList.filterTasks("boo"));

        Event eventTask = new Event("Return book",
                LocalDateTime.of(2026, 8, 21, 9, 45),
                LocalDateTime.of(2026, 8, 22, 16, 45));

        taskList.addTask(eventTask);

        testCase3TaskList.add(todoTask1);
        testCase3TaskList.add(eventTask);

        testCase4TaskList.add(eventTask);

        assertEquals(testCase3TaskList, taskList.filterTasks("boo"));
        assertEquals(testCase3TaskList, taskList.filterTasks("book"));


        assertEquals(testCase4TaskList, taskList.filterTasks("return"));
        assertEquals(testCase4TaskList, taskList.filterTasks("turn"));
    }
}
