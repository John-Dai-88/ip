import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Classes.Deadline;
import Classes.Event;
import Classes.Task;
import Classes.ToDo;

import Exceptions.IncompleteCommandException;
import Exceptions.InvalidDateAndTimeException;
import Exceptions.InvalidTaskNumberException;
import Exceptions.JarvisException;

/** Runs the Jarvis chatbot and processes task-management commands. */
public class Jarvis {
    // Declaring String variables for specific String messages
    private static String banner, borderLine, initialMessage, exitMessage, clipThatMessage,
            unknownCommandMessage;
    // Declaring an ArrayList to store Tasks
    private static List<Task> toDoTasks = new ArrayList<>();

    /** Starts the chatbot and reads commands until the user enters {@code bye}. */
    public static void main(String[] args) {

        // Creates a new Scanner instance to allow program to read user input from the terminal
        Scanner scanner = new Scanner(System.in);

        // String banner art of Jarvis
        banner =
                "     ██╗ █████╗ ██████╗ ██╗   ██╗██╗███████╗\n"
                        + "     ██║██╔══██╗██╔══██╗██║   ██║██║██╔════╝\n"
                        + "     ██║███████║██████╔╝██║   ██║██║███████╗\n"
                        + "██   ██║██╔══██║██╔══██╗╚██╗ ██╔╝██║╚════██║\n"
                        + "╚█████╔╝██║  ██║██║  ██║ ╚████╔╝ ██║███████║\n"
                        + " ╚════╝ ╚═╝  ╚═╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝\n";

        // String message that creates a border
        borderLine = "---------------------------------------------------------------------\n";

        // Preset String message to greet the user when program is first executed
        initialMessage = borderLine
                + banner
                + "Good day Sir/Ma' am, I am Jarvis, your friendly AI assistant\n"
                + "How may I be of service to you today ?\n"
                + borderLine;

        // Preset String message to say goodbye to the user when 'bye' is inputted into terminal
        exitMessage = "Goodbye Sir/Ma' am. I hope to be of service to you again next time\n"
                + borderLine;

        // Preset String message in reference to a famous Jarvis meme
        clipThatMessage = "Clipped and Ready to ship Sir\n"
                + borderLine;

        // Preset String message in response to any unknown commands that user inputs
        unknownCommandMessage = "Apologies Sir/Ma' am.\n"
                + "I do not understand your command, please retry with a valid command\n"
                + borderLine;

        // Print the greeting message when program is first run
        System.out.println(initialMessage);

        // Purpose : To read user's terminal input and respond accordingly
        // While() is used as program is to run indefinitely until user quits it via entering 'bye'
        while (true) {
            // Reads and stores the user's input into a temp String variable
            String userInput = scanner.nextLine();

            // Processes the user's input and executes accordingly, based on the if loops below
            try {
                // Print a special response when the phrase below is detected
                if (userInput.equals("Jarvis, clip that")) {
                    System.out.println(clipThatMessage);
                }

                // Run list() when user input is "list"
                else if (userInput.equals("list")) {
                    listAllTasks();
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
                    System.out.println(exitMessage);
                    break;
                }

                // If no valid command is inputted, print default unknown command message
                else {
                    System.out.println(unknownCommandMessage);
                }
            }

            // Catches any JarvisException thrown while processing the user's input
            catch (JarvisException error) {
                System.err.println(error.getMessage());
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
                            + borderLine
            );
        }

        // Checks if task string is empty, if so throw an error
        if (task.isEmpty()) {
            // Throw an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : todo <Task>\n"
                            + borderLine
            );
        }

        // Creates a new todo task instance from text extracted from user's input
        ToDo newToDoTask = new ToDo(task);
        // Store the newly created todo task instance into toDoTasks
        toDoTasks.add(newToDoTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newToDoTask.toString() + "\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + borderLine, toDoTasks.size());
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
                            + borderLine
            );
        }

        // Checks if deadline provided is empty. If yes, throw an error
        if (deadline.trim().isEmpty()) {
            // Throws an invalid deadline error
            throw new InvalidDateAndTimeException(
                    "Error : Your command is missing a deadline.\n"
                            + "Please re-enter your command in the format : deadline <Task> /by <deadline>\n"
                            + borderLine
            );
        }

        // Create a new deadline task from texts extracted from user's input
        Deadline newDeadlineTask = new Deadline(task, deadline);
        // Store the newly created deadline task instance into toDoTasks
        toDoTasks.add(newDeadlineTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newDeadlineTask.toString() + "\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + borderLine, toDoTasks.size());
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
                            + borderLine
            );
        }

        // Checks if startDateTime and/or endDateTime is empty. If yes, throw an error
        if (startDateTime.trim().isEmpty() || endDateTime.trim().isEmpty()) {
            // Throw an invalid date and time error
            throw new InvalidDateAndTimeException(
                    "Error : Your command is missing a startDateTime and/or an endDateTime.\n"
                    + "Please re-enter your command in the format : event <Task> /from "
                    + "<startDateTime> /to <endDateTime>\n"
                            + borderLine
            );
        }

        // Create a new event task from texts extracted from user's input
        Event newEventTask = new Event(task, startDateTime, endDateTime);
        // Store the newly created event task instance into toDoTasks
        toDoTasks.add(newEventTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newEventTask.toString() + "\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + borderLine, toDoTasks.size());
    }


    /** Prints the user's tasks, including each task's category and status. */
    public static void listAllTasks() {
        System.out.println("\nHere are the list of things you had wished to do earlier Sir/Ma' am\n");
        // For loop is to iterate through the toDoTasks and print out all the tasks
        for (int i = 0; i < toDoTasks.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, toDoTasks.get(i).toString());
        }
        System.out.println(borderLine);
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
        // int variable to store task number inputted by user
        int taskNumber, toDoTaskListIndex;

        // Attempt to acquire task number from user's string input
        try {
            // Acquire task number inputted by user
            taskNumber = Integer.parseInt(splitUserInput[1]);
            toDoTaskListIndex = taskNumber - 1;
        }

        // Catches errors resulting from incomplete user command input
        catch (ArrayIndexOutOfBoundsException error) {
            // Throws an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + " Please re-enter your command in the format : \n"
                            + " - mark <Task Number> for marking tasks as done\n"
                            + " - unmark <Task Number> for marking tasks as undone\n"
                            + borderLine
            );
        }

        // Checks if task number extracted from user's input is outside the valid range
        // If yes, throw an invalid task number error
        if (taskNumber < 1 || taskNumber > toDoTasks.size()) {
            // Throws an invalid task number error
            throw new InvalidTaskNumberException(
                    "Error : The task number you inputted is invalid.\n"
                            + String.format("Please re-enter with a valid number ranging "
                                    + "from 1 to %d\n", toDoTasks.size())
                            + borderLine
            );
        }

        // Change the status of the task
        toDoTasks.get(toDoTaskListIndex).setCompletionStatus(status);

        // Print statement showing the user selected task has been marked done and display the task's status
        System.out.println("\nVery well Sir/Ma' am, I have marked the following task as : \n"
                + toDoTasks.get(toDoTaskListIndex).toString() + "\n"
                + borderLine);
    }

    /** Deletes the task selected by the user's command.
     *
     * @param userInput User command containing a task number.
     * @throws JarvisException If the command or task number is invalid.
     */
    public static void deleteTask(String userInput) throws JarvisException {
        // Splits the user input's into two separate strings and store them in an array
        String[] splitUserInput = userInput.split(" ");
        // int variable to store task number inputted by user
        int taskNumber;

        // Attempt to acquire task number from user's string input
        try {
            // Acquire task number inputted by user
            taskNumber = Integer.parseInt(splitUserInput[1]);
        }

        // Catches errors resulting from incomplete user command input
        catch (ArrayIndexOutOfBoundsException error) {
            // Throws an incomplete command error
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + " Please re-enter your command in the format : delete <Task Number>\n"
                            + borderLine
            );
        }

        // Checks if task number extracted from user's input is outside the valid range
        // If yes, throw an invalid task number error
        if (taskNumber < 1 || taskNumber > toDoTasks.size()) {
            // Throws an invalid task number error
            throw new InvalidTaskNumberException(
                    "Error : The task number you inputted is invalid.\n"
                            + String.format("Please re-enter with a valid number ranging "
                                    + "from 1 to %d\n", toDoTasks.size())
                            + borderLine
            );
        }

        // Modify the task number to match its corresponding index number in toDoTasks
        int toDoListIndexNo = taskNumber - 1;

        // Print statement showing the user selected task has been deleted
        // and display the deleted task one more time
        System.out.printf("\nVery good Sir/Ma' am, I have removed the following task from your list of tasks-to-do : \n"
                + toDoTasks.get(toDoListIndexNo).toString()+"\n"
                + "Please do note Sir/Ma' am, now you have %d task(s) awaiting you \n"
                + borderLine, toDoTasks.size() - 1);

        // Delete the corresponding task in the toDoTasks array list
        toDoTasks.remove(toDoListIndexNo);
    }
}