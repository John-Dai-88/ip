import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jarvis {
    // Declaring String variables for specific String messages
    private static String banner, borderLine, initialMsg, exitMsg, clipThatMsg;
    // Declaring a List (of type String) to store user's inputs
    private static List<String> toDoTasks = new ArrayList<String>();

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

        // Purpose : To read user's terminal input and echo it back to them and exit when 'bye' is entered
        // While() is used as program is to run indefinitely until user quits it via entering 'bye'
        while (true) {
            // Reads and stores the user's input into a temp String variable
            String userInput = scanner.nextLine();

            // Print a special response when the phrase below is detected
            if(userInput.equals("Jarvis, clip that")) {
                System.out.println(clipThatMsg);
            }

            // Print and display a list of the user's previous input(s) when "list" is detected
            else if(userInput.equals("list")) {
                System.out.println("\nHere are the list of things you had wished to do earlier Sir\n");
                // For loop is to iterate through the toDoTasks and print out all the user's inputs
                for(int i = 0; i< toDoTasks.size(); i++) {
                    System.out.printf("%d. %s\n",i+1, toDoTasks.get(i));
                }
                System.out.println(borderLine);
            }

            // Marks the corresponding task in the toDoTasks List as being done and reprint its
            // status for the user when input starting with "mark" is detected
            else if(userInput.startsWith("mark")) {
                // Splits the user input's into two separate strings and store them in an array
                String[] splitUserInput = userInput.split(" ");
                // Acquire task number inputted by user
                int taskNumber = Integer.parseInt(splitUserInput[1]);
                // Modify the number to match its corresponding index number in toDoTasks
                int toDoListindexNo = taskNumber - 1;

                // Acquire the current stored String at toDoTasks
                String taskToBeMarked = toDoTasks.get(toDoListindexNo);
                // Replace the status of the user selected task
                taskToBeMarked = taskToBeMarked.replace("[]","[X]");
                // Store the updated task into the toDoTask list at the corresponding index position
                toDoTasks.set(toDoListindexNo, taskToBeMarked);

                // Print message that the user selected task has been marked done and display the task's status
                System.out.println("\nVery well Sir/Ma' am, I have marked the following task as done : \n"
                        + toDoTasks.get(toDoListindexNo)+"\n"+borderLine);
            }

            // Unmarks the corresponding task in the toDoTasks List as being done and reprint its
            // status for the user when input starting with "unmark" is detected
            else if(userInput.startsWith("unmark")) {
                // Splits the user input's into two separate strings and store them in an array
                String[] splitUserInput = userInput.split(" ");
                // Acquire task number inputted by user
                int taskNumber = Integer.parseInt(splitUserInput[1]);
                // Modify the number to match its corresponding index number in toDoTasks
                int toDoListindexNo = taskNumber - 1;

                // Acquire the current stored String at toDoTasks
                String taskToBeMarked = toDoTasks.get(toDoListindexNo);
                // Replace the status of the user selected task
                taskToBeMarked = taskToBeMarked.replace("[X]","[]");
                // Store the updated task into the toDoTask list at the corresponding index position
                toDoTasks.set(toDoListindexNo, taskToBeMarked);

                // Print message that the user selected task has been marked undone and display the task's status
                System.out.println("\nVery well Sir/Ma' am, I have marked the following task as undone : \n"
                        + toDoTasks.get(toDoListindexNo)+"\n"+borderLine);

            }

            // Prints exit message and exit the while loop, when 'bye' is detected
            else if(userInput.equals("bye")) {
                System.out.println(exitMsg);
                break;
            }

            // If no special phrase(s) or word(s) is entered.
            // Echo user's input back in terminal and store them into the String List, toDoTasks
            else {
                toDoTasks.add("[] "+userInput);
                System.out.printf("added: %s\n",userInput);
                System.out.println(borderLine);
            }
        }
    }
}
