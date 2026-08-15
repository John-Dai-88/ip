import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jarvis {
    // Declaring String variables for specific String messages
    private static String banner, borderLine, initialMsg, exitMsg, clipThatMsg;
    // Declaring a List (of type String) to store user's inputs
    private static List<String> toDoList = new ArrayList<String>();

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

            // Print a special response when detecting the phrase below
            if(userInput.equals("Jarvis, clip that")) {
                System.out.println(clipThatMsg);
            }

            // Print and display a list of the user's previous input(s)
            else if(userInput.equals("list")) {
                System.out.println("\nHere are the list of things you had wished to do earlier Sir\n");
                // For loop is to iterate through the toDoList and print out all the user's inputs
                for(int i=0; i<toDoList.size(); i++) {
                    System.out.printf("%d. %s\n",i+1,toDoList.get(i));
                }
                System.out.println(borderLine);
            }

            // Checks if user inputs in 'bye' into the terminal
            // If yes, prints exit message, exit the while loop
            else if(userInput.equals("bye")) {
                System.out.println(exitMsg);
                break;
            }

            // If no special phrase(s) or word(s) is entered.
            // Echo user's input back in terminal and store them into the String List, toDoList
            else {
                toDoList.add(userInput);
                System.out.printf("added: %s\n",userInput);
                System.out.println(borderLine);
            }
        }
    }
}
