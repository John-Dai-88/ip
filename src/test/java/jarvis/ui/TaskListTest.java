package jarvis.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jarvis.classes.Deadline;
import org.junit.jupiter.api.Test;

import jarvis.classes.Task;
import jarvis.classes.ToDo;

import java.time.LocalDateTime;
import java.util.ArrayList;

/** Tests task-list operations. */
public class TaskListTest {
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
}
