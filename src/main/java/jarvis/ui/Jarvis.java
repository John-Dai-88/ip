package jarvis.ui;

import jarvis.classes.Deadline;
import jarvis.classes.Event;
import jarvis.classes.Task;
import jarvis.classes.ToDo;

import jarvis.exceptions.IncompleteCommandException;
import jarvis.exceptions.InvalidDateAndTimeException;
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

        // Load previously saved tasks from the hard disk
        taskList = new TaskList(Storage.loadTasks());
        ui.showWelcome();

        // Purpose : To read user's terminal input and respond accordingly
        // While() is used as program is to run indefinitely until user quits it via entering 'bye'
        while (true) {
            // Reads and stores the user's input into a temp String variable
            String userInput = ui.readCommand();

            // Processes the user's input and executes accordingly, based on the if loops below
            try {
                // Print a special response when the phrase below is detected
                if (userInput.equals("Jarvis, clip that")) {
                    ui.showClipThat();
                }

                // Run list() when user input is "list"
                else if (userInput.equals("list")) {
                    ui.listAllTasks(taskList.getTasks());
                }

                // Run markDone(...) when user input starts with "mark"
                else if (userInput.startsWith("mark")) {
                    markTaskAs(userInput, Task.CompletionStatus.DONE);
                }

                // Run markTaskAs(...) when user input starts with "unmark"
                else if (userInput.startsWith("unmark")) {
                    markTaskAs(userInput, Task.CompletionStatus.UNDONE);
                }

                // Run createToDoTask(...) when user input starts with "todo";
                else if (userInput.startsWith("todo")) {
                    createToDoTask(userInput);
                }

                // Run createDeadlineTask(...) when user input starts with "deadline"
                else if (userInput.startsWith("deadline")) {
                    createDeadlineTask(userInput);
                }

                // Run createEventTask(...) when user input starts with "event"
                else if (userInput.startsWith("event")) {
                    createEventTask(userInput);
                }

                // Run deleteTask(...) when user input starts with "delete"
                else if (userInput.startsWith("delete")) {
                    deleteTask(userInput);
                }

                // Prints exit message and exit the while loop, when 'bye' is detected
                else if (userInput.equals("bye")) {
                    ui.sayGoodbye();
                    break;
                }

                // If no valid command is inputted, print default unknown command message
                else {
                    ui.showUnknownCommand();
                }
            }

            // Catches any JarvisException thrown while processing the user's input
            catch (JarvisException error) {
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
        // Temp String variable to store task substring from user's input
        String task;

        // Attempt to acquire task from user's string input
        try {
            // Extracts the task substring from user's string input
            task = userInput.substring(5).trim();
        }

        // Catches errors resulting from incomplete user command input
        catch (StringIndexOutOfBoundsException error) {
            // Throw an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : todo <Task>\n"
                            + BORDER_LINE
            );
        }

        // Checks if task string is empty, if so throw an error
        if (task.isEmpty()) {
            // Throw an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : todo <Task>\n"
                            + BORDER_LINE
            );
        }

        // Creates a new todo task instance from text extracted from user's input
        ToDo newToDoTask = new ToDo(task);
        // Store the newly created todo task instance in the task list
        taskList.addTask(newToDoTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newToDoTask.toString() + "\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + BORDER_LINE, taskList.size());
    }


    /** Creates and stores a deadline task from the user's command.
     *
     * @param userInput User command containing the task and deadline.
     * @throws JarvisException If the command is invalid.
     */
    public static void createDeadlineTask(String userInput) throws JarvisException {
        // int variable to store starting index position of "/by" in user's input
        int positionOfBy;
        // String variables to store the extracted deadline and task from user's input
        String deadline, task;

        // Attempt to acquire task and deadline from user's string input
        try {
            // Extract starting index position of text "/by" in user's string input
            positionOfBy = userInput.indexOf("/by");
            // Extract the deadline substring from user's string input
            deadline = userInput.substring(positionOfBy + 4).trim();
            // Extract the task substring from user's string input
            task = userInput.substring(9, positionOfBy).trim();
        }

        // Catches errors resulting from incomplete user command input
        catch (StringIndexOutOfBoundsException error) {
            // Throws an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : deadline <Task> /by <deadline>\n"
                            + BORDER_LINE
            );
        }

        // Checks if deadline provided is empty. If yes, throw an error
        if (deadline.trim().isEmpty()) {
            // Throws an invalid deadline error
            throw new InvalidDateAndTimeException(
                    "Error : Your command is missing a deadline.\n"
                            + "Please re-enter your command in the format : deadline <Task> /by <deadline>\n"
                            + BORDER_LINE
            );
        }

        // Create a new deadline task from texts extracted from user's input
        Deadline newDeadlineTask = new Deadline(task, deadline);
        // Store the newly created deadline task instance in the task list
        taskList.addTask(newDeadlineTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newDeadlineTask.toString() + "\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + BORDER_LINE, taskList.size());
    }


    /** Creates and stores an event task from the user's command.
     *
     * @param userInput User command containing the task and event times.
     * @throws JarvisException If the command is invalid.
     */
    public static void createEventTask(String userInput) throws JarvisException {
        // int variables to store the starting index position of "/from" and "/to" from user's input
        int positionOfFrom, positionOfTo;
        // String variables to store the start/end date and time and task from user's input
        String startDateTime, endDateTime, task;

        // Attempt to acquire start/end date and time and task from user's string input
        try {
            // Extract starting index position of "/from" from user's input
            positionOfFrom = userInput.indexOf("/from");
            // Extract starting index position of "/to " from user's input
            positionOfTo = userInput.indexOf("/to");

            // Extract the start date and time substring from user's string input
            startDateTime = userInput.substring(positionOfFrom + 6, positionOfTo);
            // Extract the end date and time substring from user's string input
            endDateTime = userInput.substring(positionOfTo + 4);
            // Extract the task substring from user's string input
            task = userInput.substring(6, positionOfFrom).trim();
        }

        // Catches errors resulting from incomplete user command input
        catch (StringIndexOutOfBoundsException error) {
            // Throw an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : event <Task> /from "
                            + "<startDateTime> /to <endDateTime>\n"
                            + BORDER_LINE
            );
        }

        // Checks if startDateTime and/or endDateTime is empty. If yes, throw an error
        if (startDateTime.trim().isEmpty() || endDateTime.trim().isEmpty()) {
            // Throw an invalid date and time error
            throw new InvalidDateAndTimeException(
                    "Error : Your command is missing a startDateTime and/or an endDateTime.\n"
                    + "Please re-enter your command in the format : event <Task> /from "
                    + "<startDateTime> /to <endDateTime>\n"
                            + BORDER_LINE
            );
        }

        // Create a new event task from texts extracted from user's input
        Event newEventTask = new Event(task, startDateTime, endDateTime);
        // Store the newly created event task instance in the task list
        taskList.addTask(newEventTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newEventTask.toString() + "\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + BORDER_LINE, taskList.size());
    }


    /** Prints the user's tasks, including each task's category and status. */
    public static void listAllTasks() {
        ui.listAllTasks(taskList.getTasks());
    }


    /** Sets the completion status of the task selected by the user's command.
     *
     * @param userInput User command containing a task number.
     * @param status New completion status.
     * @throws JarvisException If the command or task number is invalid.
     */
    public static void markTaskAs(String userInput, Task.CompletionStatus status) throws JarvisException {
        // Splits the user input's into two separate strings and store them in an array
        String[] splitUserInput = userInput.split(" ");
        // int variable to store task number and its corresponding index number
        int taskNumber, toDoTaskListIndex;

        // Attempt to acquire task number from user's string input
        try {
            // Acquire task number inputted by user
            taskNumber = Integer.parseInt(splitUserInput[1]);
            // Modify the task number to match its corresponding index number in toDoTasks
            toDoTaskListIndex = taskNumber - 1;
        }

        // Catches errors resulting from incomplete user command or non-numerical input
        catch (ArrayIndexOutOfBoundsException | NumberFormatException error) {
            // Throws an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + " Please re-enter your command in the format : \n"
                            + " - mark <Task Number> for marking tasks as done\n"
                            + " - unmark <Task Number> for marking tasks as undone\n"
                            + BORDER_LINE
            );
        }

        // Checks if task number extracted from user's input is outside the valid range
        // If yes, throw an invalid task number error
        if (taskNumber < 1 || taskNumber > taskList.size()) {
            // Throws an invalid task number error
            throw new InvalidTaskNumberException(
                    "Error : The task number you inputted is invalid.\n"
                            + String.format("Please re-enter with a valid number ranging "
                                    + "from 1 to %d\n", taskList.size())
                            + BORDER_LINE
            );
        }

        // Change the status of the task
        taskList.setCompletionStatus(toDoTaskListIndex, status);

        // Print statement showing the user selected task has been marked done and display the task's status
        System.out.println("\nVery well Sir/Ma' am, I have marked the following task as : \n"
                + taskList.getTask(toDoTaskListIndex).toString() + "\n"
                + BORDER_LINE);
    }

    /** Deletes the task selected by the user's command.
     *
     * @param userInput User command containing a task number.
     * @throws JarvisException If the command or task number is invalid.
     */
    public static void deleteTask(String userInput) throws JarvisException {
        // Splits the user input's into two separate strings and store them in an array
        String[] splitUserInput = userInput.split(" ");
        // int variable to store task number and its corresponding index number
        int taskNumber, toDoListIndexNo;

        // Attempt to acquire task number from user's string input
        try {
            // Acquire task number inputted by user
            taskNumber = Integer.parseInt(splitUserInput[1]);
            // Modify the task number to match its corresponding index number in toDoTasks
            toDoListIndexNo = taskNumber - 1;
        }

        // Catches errors resulting from incomplete user command or non-numerical input
        catch (ArrayIndexOutOfBoundsException | NumberFormatException error) {
            // Throws an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + " Please re-enter your command in the format : delete <Task Number>\n"
                            + BORDER_LINE
            );
        }

        // Checks if task number extracted from user's input is outside the valid range
        // If yes, throw an invalid task number error
        if (taskNumber < 1 || taskNumber > taskList.size()) {
            // Throws an invalid task number error
            throw new InvalidTaskNumberException(
                    "Error : The task number you inputted is invalid.\n"
                            + String.format("Please re-enter with a valid number ranging "
                                    + "from 1 to %d\n", taskList.size())
                            + BORDER_LINE
            );
        }

        // Print statement showing the user selected task has been deleted
        // and display the deleted task one more time
        System.out.printf("\nVery good Sir/Ma' am, I have removed the following task from your list of tasks-to-do : \n"
                + taskList.getTask(toDoListIndexNo).toString() + "\n"
                + "Please do note Sir/Ma' am, now you have %d task(s) awaiting you \n"
                + BORDER_LINE, taskList.size() - 1);

        // Delete the corresponding task in the task list
        taskList.deleteTask(toDoListIndexNo);
    }
}
