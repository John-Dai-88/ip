package jarvis.ui;

import java.util.ArrayList;
import java.util.List;

import jarvis.classes.Task;
import jarvis.storage.Storage;

/** Stores, updates, and persists the user's tasks. */
public class TaskList {
    /** A list to store tasks added by the user. */
    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this(new ArrayList<>());
    }

    /** Creates a task list containing the supplied tasks.
     *
     * @param tasks Existing tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /** Adds a task and saves the updated list.
     *
     * @param task Task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    /** Removes a task by zero-based index and saves the updated list.
     *
     * @param index Zero-based index of the task to remove.
     * @return Removed task.
     */
    public Task deleteTask(int index) {
        Task removedTask = tasks.remove(index);
        saveTasks();
        return removedTask;
    }

    /** Returns all tasks stored in this list.
     *
     * @return Read-only view of the tasks.
     */
    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    /** Returns the task at a zero-based index.
     *
     * @param index Zero-based index of the task.
     * @return Task at the requested index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /** Updates a task's completion status and saves the updated list.
     *
     * @param index Zero-based index of the task to update.
     * @param status New completion status.
     */
    public void setCompletionStatus(int index, Task.CompletionStatus status) {
        tasks.get(index).setCompletionStatus(status);
        saveTasks();
    }

    /** Returns the number of tasks currently stored.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /** Saves the current tasks to persistent storage. */
    public void saveTasks() {
        Storage.saveTasks(tasks);
    }

    /** Return tasks whose description contain the given key word.
     *
     * @param taskKeyWord The key word to filter tasks by
     * @return List of tasks matching the keyword.
     */
    public List<Task> filterTasks(String taskKeyWord) {
        String filterKeyWord = taskKeyWord.toLowerCase();
        List<Task> filteredTasks = new ArrayList<>();

        for(Task task : tasks) {
            if(task.getTaskName().toLowerCase().contains(filterKeyWord)) {
                filteredTasks.add(task);
            }
        }

        return filteredTasks;
    }
}
