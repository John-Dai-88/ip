import java.util.Scanner;

public class Jarvis {
    // Declaring String variables for specific String messages
    private static String banner, borderLine, initialMsg, exitMsg, clipThatMsg;

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

            // Switch case provides special responses when special phrase(s) or word(s) are detected
            // otherwise it echo the user's input back to them
            switch(userInput) {
                // Prints exit message when user keys in 'bye'
                case "bye":
                    System.out.println(exitMsg);
                    break;
                // Print a special response when detecting the phrase below
                case "Jarvis, clip that":
                    System.out.println(clipThatMsg);
                    break;
                // If no special phrase(s) or word(s) is entered. Echo user's input back in terminal
                default:
                    System.out.println(userInput+"\n"+borderLine);
            }

            // Checks if user inputs in 'bye' into the terminal
            // If yes, exit the while loop and exits the program
            if(userInput.equals("bye")) {
                break;
            }
        }
    }
}
