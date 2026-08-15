import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jarvis {
    // Declaring String variables for specific String messages
    private static String banner, borderLine, initialMsg, exitMsg, clipThatMsg;
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
        borderLine = "---------------------------------------------------------\n";

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

        // Print the greeting message when program is first run
        System.out.println(initialMsg);

        // Purpose : To read user's terminal input and respond accordingly
        // While() is used as program is to run indefinitely until user quits it via entering 'bye'
        while (true) {
            // Reads and stores the user's input into a temp String variable
            String userInput = scanner.nextLine();

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
            else if(userInput.startsWith("todo")) {
                createToDoTask(userInput);
            }

            // Prints exit message and exit the while loop, when 'bye' is detected
            else if(userInput.equals("bye")) {
                System.out.println(exitMsg);
                break;
            }

            // If no special phrase(s) or word(s) is entered, run echo(...)
            else {
                echo(userInput);
            }
        }
    }


    // Echo user's input back in terminal and store them into the Task ArrayList, toDoTasks
    public static void echo(String userInput) {
        // Creates a new task instance using user input
        Task newTask = new Task(userInput);
        // Store the newly created task instance into toDoTasks
        toDoTasks.add(newTask);
        // Print statement to show that user's input has been added
        System.out.printf("added: %s\n" + borderLine,userInput);
    }


    // Creates a new To_Do Task from user's input and stores them into the Task ArrayList, toDoTasks
    public static void createToDoTask(String userInput) {
        //
        String task = userInput.substring(5).trim();

        // Creates a new task instance using user input
        ToDo newToDoTask = new ToDo(task);
        // Store the newly created task instance into toDoTasks
        toDoTasks.add(newToDoTask);
        // Print statement to show that user's input has been added
        System.out.printf("Very well Sir/Ma' am, I have added the following task below : \n"
                + newToDoTask.toString()+"\n"
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
    public static void markDoneOrUndone(String userInput, String completionStatus) {
        // Splits the user input's into two separate strings and store them in an array
        String[] splitUserInput = userInput.split(" ");
        // Acquire task number inputted by user
        int taskNumber = Integer.parseInt(splitUserInput[1]);
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
