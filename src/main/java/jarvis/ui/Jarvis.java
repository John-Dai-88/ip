package jarvis.ui;

import jarvis.classes.Deadline;
import jarvis.classes.Event;
import jarvis.classes.Task;
import jarvis.classes.ToDo;

import jarvis.exceptions.InvalidTaskNumberException;
import jarvis.exceptions.JarvisException;

import jarvis.storage.Storage;

/** Runs the Jarvis chatbot and processes task-management commands. */
public class Jarvis {
    private static final String BORDER_LINE = "---------------------------------------------------------------------\n";
    private static final UI ui = new UI();
    private static TaskList taskList;

    /** Starts the chatbot and reads commands until the user enters {@code bye}. */
    public static void main(String[] args) {
        taskList = new TaskList(Storage.loadTasks());
        ui.showWelcome();

        while (true) {
            // Read and store user's terminal input
            String userInput = ui.readCommand();

            try {
                if (userInput.equals("Jarvis, clip that")) {
                    ui.showClipThat();
                } else if (userInput.equals("list")) {
                    ui.listAllTasks(taskList.getTasks());
                } else if (userInput.startsWith("mark")) {
                    markTaskAs(userInput, Task.CompletionStatus.DONE);
                } else if (userInput.startsWith("unmark")) {
                    markTaskAs(userInput, Task.CompletionStatus.UNDONE);
                } else if (userInput.startsWith("todo")) {
                    createToDoTask(userInput);
                } else if (userInput.startsWith("deadline")) {
                    createDeadlineTask(userInput);
                } else if (userInput.startsWith("event")) {
                    createEventTask(userInput);
                } else if (userInput.startsWith("delete")) {
                    deleteTask(userInput);
                } else if (userInput.equals("bye")) {
                    ui.sayGoodbye();
                    break;
                } else {
                    ui.showUnknownCommand();
                }
            } catch (JarvisException error) {
                ui.showError(error.getMessage());
            }
        }
    }

    /** Creates and stores a to-do task from the user's command.
     *
     * @param userInput User command containing the task description.
     * @throws JarvisException If the command is incomplete.
     */
    public static void createToDoTask(String userInput) throws JarvisException {
        ToDo toDoTask = Parser.parseToDo(userInput);
        taskList.addTask(toDoTask);
        printAddedTask(toDoTask);
        Storage.saveTasks(taskList.getTasks());
    }

    /** Creates and stores a deadline task from the user's command.
     *
     * @param userInput User command containing the task and deadline.
     * @throws JarvisException If the command is invalid.
     */
    public static void createDeadlineTask(String userInput) throws JarvisException {
        Deadline deadlineTask = Parser.parseDeadline(userInput);
        taskList.addTask(deadlineTask);
        printAddedTask(deadlineTask);
        Storage.saveTasks(taskList.getTasks());
    }

    /** Creates and stores an event task from the user's command.
     *
     * @param userInput User command containing the task and event times.
     * @throws JarvisException If the command is invalid.
     */
    public static void createEventTask(String userInput) throws JarvisException {
        Event eventTask = Parser.parseEvent(userInput);
        taskList.addTask(eventTask);
        printAddedTask(eventTask);
        Storage.saveTasks(taskList.getTasks());
    }

    /** Sets the completion status of the selected task.
     *
     * @param userInput User command containing a task number.
     * @param status New completion status.
     * @throws JarvisException If the command or task number is invalid.
     */
    public static void markTaskAs(String userInput, Task.CompletionStatus status)
            throws JarvisException {
        int taskNumber = Parser.parseTaskNumber(userInput);
        int taskIndex = taskNumber - 1;
        validateTaskNumber(taskNumber);
        taskList.setCompletionStatus(taskIndex, status);

        System.out.println("\nVery well Sir/Ma' am, I have marked the following task as: \n"
                + taskList.getTask(taskIndex) + "\n"
                + BORDER_LINE);

        Storage.saveTasks(taskList.getTasks());
    }

    /** Deletes the task selected by the user's command.
     *
     * @param userInput User command containing a task number.
     * @throws JarvisException If the command or task number is invalid.
     */
    public static void deleteTask(String userInput) throws JarvisException {
        int taskNumber = Parser.parseTaskNumber(userInput);
        int taskIndex = taskNumber - 1;
        validateTaskNumber(taskNumber);
        Task deletedTask = taskList.deleteTask(taskIndex);

        System.out.printf("\nVery good Sir/Ma' am, I have removed the following task "
                + "from your list of tasks-to-do: \n%s\n"
                + "Please do note Sir/Ma' am, now you have %d task(s) awaiting you \n"
                + BORDER_LINE, deletedTask, taskList.size());

        Storage.saveTasks(taskList.getTasks());
    }

    /** Prints a confirmation after adding a task.
     *
     * @param task Newly added task.
     */
    private static void printAddedTask(Task task) {
        System.out.printf("Very well Sir/Ma' am, I have added the following task below: \n"
                + "%s\nPlease do note Sir/Ma' am, that you now currently have %d task(s) "
                + "awaiting you \n%s", task, taskList.size(), BORDER_LINE);
    }

    /** Validates a one-based task number.
     *
     * @param taskNumber One-based task number.
     * @throws InvalidTaskNumberException If the number is outside the task-list range.
     */
    private static void validateTaskNumber(int taskNumber)
            throws InvalidTaskNumberException {
        if (taskNumber < 1 || taskNumber > taskList.size()) {
            throw new InvalidTaskNumberException(
                    "Error: The task number you inputted is invalid.\n"
                            + String.format("Please re-enter with a valid number ranging from "
                            + "1 to %d\n", taskList.size())
                            + BORDER_LINE);
        }
    }
}
