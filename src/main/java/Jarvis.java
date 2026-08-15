import Exceptions.JarvisException;
import Exceptions.IncompleteCommandException;
import Exceptions.InvalidTaskNumberException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jarvis {
    // Declaring String variables for specific String messages
    private static String banner, borderLine, initialMsg, exitMsg, clipThatMsg, unknownCMDMsg;
    // Declaring an ArrayList to store Tasks
    private static List<Task> toDoTasks = new ArrayList<Task>();

    // Static void main(...) is the entry point function where the Java program begins execution
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
        initialMsg = borderLine
                + banner
                + "Good day Sir/Ma' am, I am Jarvis, your friendly AI assistant\n"
                + "How may I be of service to you today ?\n"
                + borderLine;

        // Preset String message to say goodbye to the user when 'bye' is inputted into terminal
        exitMsg = "Goodbye Sir/Ma' am. I hope to be of service to you again next time\n"
                + borderLine;

        // Preset String message in reference to a famous Jarvis meme
        clipThatMsg = "Clipped and Ready to ship Sir\n"
                    + borderLine;

        // Preset String message in response to any unknown commands that user inputs
        unknownCMDMsg = "Apologies Sir/Ma' am.\n"
                      + "I do not understand your command, please retry with a valid command\n"
                      + borderLine;

        // Print the greeting message when program is first run
        System.out.println(initialMsg);

        // Purpose : To read user's terminal input and respond accordingly
        // While() is used as program is to run indefinitely until user quits it via entering 'bye'
        while (true) {
            // Reads and stores the user's input into a temp String variable
            String userInput = scanner.nextLine();

            // Processes the user's input and executes accordingly, based on the if loops below
            try {
                // Print a special response when the phrase below is detected
                if(userInput.equals("Jarvis, clip that")) {
                    System.out.println(clipThatMsg);
                }

                // Run list() when user input is "list"
                else if(userInput.equals("list")) {
                    list();
                }

                // Run markDone(...) when user input starts with "mark"
                else if(userInput.startsWith("mark")) {
                    markDoneOrUndone(userInput,"Done");
                }

                // Run markDoneOrUndone(...) when user input starts with "unmark"
                else if(userInput.startsWith("unmark")) {
                    markDoneOrUndone(userInput,"Undone");
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

                // Prints exit message and exit the while loop, when 'bye' is detected
                else if(userInput.equals("bye")) {
                    System.out.println(exitMsg);
                    break;
                }

                // If no valid command is inputted, print default unknown command message
                else {
                    System.out.println(unknownCMDMsg);
                }
            }

            // Catches any JarvisException thrown while processing the user's input
            catch (JarvisException error) {
                System.err.println(error.getMessage());
            }
        }
    }



    // Creates a new To_Do Task from user's input and stores them into the Task ArrayList, toDoTasks
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
        if(task.isEmpty()) {
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
                + newToDoTask.toString()+"\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + borderLine, toDoTasks.size());
    }



    // Creates a new deadline task from user's input and stores them into the Task ArrayList, toDoTasks
    public static void createDeadlineTask(String userInput) {
        // Extract starting index position of text "/by" in user's string input
        int positionOfBy = userInput.indexOf("/by");
        // Extract the deadline substring from user's string input
        String deadline = userInput.substring(positionOfBy + 4);
        // Extract the task substring from user's string input
        String task = userInput.substring(9, positionOfBy).trim();

        // Create a new deadline task from texts extracted from user's input
        Deadline newDeadlineTask = new Deadline(task,deadline);
        // Store the newly created deadline task instance into toDoTasks
        toDoTasks.add(newDeadlineTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newDeadlineTask.toString()+"\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + borderLine, toDoTasks.size());
    }



    // Creates a new event task from user's input and stores them into the Task ArrayList, toDoTasks
    public static void createEventTask(String userInput) {
        // Extract starting index position of "/from" from user's input
        int positionOfFrom = userInput.indexOf("/from");
        // Extract starting index position of "/to " from user's input
        int positionOfTo = userInput.indexOf("/to");

        // Extract the start date and time substring from user's string input
        String startDAT = userInput.substring(positionOfFrom + 6, positionOfTo);
        // Extract the end date and time substring from user's string input
        String endDAT = userInput.substring(positionOfTo + 4);
        // Extract the task substring from user's string input
        String task = userInput.substring(6, positionOfFrom).trim();

        // Create a new event task from texts extracted from user's input
        Event newEventTask = new Event(task,startDAT,endDAT);
        // Store the newly created event task instance into toDoTasks
        toDoTasks.add(newEventTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newEventTask.toString()+"\n"
                + "Please do note Sir/Ma' am, that you now currently have %d task(s) awaiting you \n"
                + borderLine, toDoTasks.size());
    }



    // Print and display a list of the user's previous inputted tasks, their category and status
    public static void list() {
        System.out.println("\nHere are the list of things you had wished to do earlier Sir/Ma' am\n");
        // For loop is to iterate through the toDoTasks and print out all tasks
        for(int i = 0; i< toDoTasks.size(); i++) {
            System.out.printf("%d. %s\n",i+1, toDoTasks.get(i).toString());
        }
        System.out.println(borderLine);
    }



    // Marks/Unmarks the corresponding task in the toDoTasks ArrayList as being done and reprint its
    // updated status for the user
    public static void markDoneOrUndone(String userInput, String completionStatus) throws JarvisException {
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
                            + " Please re-enter your command in the format : \n"
                            + " - mark <Task Number> for marking tasks as done\n"
                            + " - unmark <Task Number> for marking tasks as undone\n"
                            + borderLine
            );
        }

        // Checks if task number extracted from user's input is outside the valid range
        // If yes, throw an invalid task number error
        if(taskNumber < 1 || taskNumber > toDoTasks.size()) {
            // Throws an invalid task number error
            throw new InvalidTaskNumberException(
                    "Error : The task number you inputted is invalid.\n"
                            + String.format("Please re-enter with a valid number ranging from 1 to %d\n", toDoTasks.size())
                            + borderLine
            );
        }

        // Modify the number to match its corresponding index number in toDoTasks
        int toDoListIndexNo = taskNumber - 1;

        if(completionStatus.equals("Done")) {
            // Set done status of user selected task to be true
            toDoTasks.get(toDoListIndexNo).markAsDone();

            // Print message that the user selected task has been marked done and display the task's status
            System.out.println("\nVery well Sir/Ma' am, I have marked the following task as done : \n"
                    + toDoTasks.get(toDoListIndexNo).toString()+"\n"
                    +borderLine);
        }

        else if(completionStatus.equals("Undone")) {
            // Set done status of user selected task to be false
            toDoTasks.get(toDoListIndexNo).markAsUndone();

            // Print message that the user selected task has been marked undone and display the task's status
            System.out.println("\nVery well Sir/Ma' am, I have marked the following task as undone : \n"
                    + toDoTasks.get(toDoListIndexNo).toString()+"\n"
                    +borderLine);
        }
    }
}