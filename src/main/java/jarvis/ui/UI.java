package jarvis.ui;

import jarvis.classes.Task;

import java.util.List;
import java.util.Scanner;

public class UI {
    /** Creates a new Scanner instance to allow program to read user input from the terminal. */
    private Scanner scanner;

    // String BANNER art of jarvis.Jarvis
    private static final String BANNER =
            "     ██╗ █████╗ ██████╗ ██╗   ██╗██╗███████╗\n"
            + "     ██║██╔══██╗██╔══██╗██║   ██║██║██╔════╝\n"
            + "     ██║███████║██████╔╝██║   ██║██║███████╗\n"
            + "██   ██║██╔══██║██╔══██╗╚██╗ ██╔╝██║╚════██║\n"
            + "╚█████╔╝██║  ██║██║  ██║ ╚████╔╝ ██║███████║\n"
            + " ╚════╝ ╚═╝  ╚═╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝\n";

    /** String message that creates a border. */
    private static final String BORDER_LINE = "---------------------------------------------------------------------\n";

    /** Preset String message to greet the user when program is first executed. */
    private static final String initialMessage = BORDER_LINE
                + BANNER
                + "Good day Sir/Ma' am, I am jarvis.Jarvis, your friendly AI assistant\n"
                        + "How may I be of service to you today ?\n"
                        + BORDER_LINE;

    /** Preset String message to say goodbye to the user when 'bye' is inputted into terminal. */
    private static final String EXIT_MESSAGE = "Goodbye Sir/Ma' am. I hope to be of service to you again next time\n"
            + BORDER_LINE;

    /** Preset String message in reference to a famous jarvis.Jarvis meme. */
    private static final String CLIP_THAT_MESSAGE = "Clipped and Ready to ship Sir\n"
            + BORDER_LINE;

    /** Preset String message in response to any unknown commands that user inputs. */
    private static final String UNKNOWN_COMMAND_MESSAGE = "Apologies Sir/Ma' am.\n"
            + "I do not understand your command, please retry with a valid command\n"
            + BORDER_LINE;

    /** Creates a UI instance. */
    public UI() {
        this.scanner = new Scanner(System.in);
    }

    /** Print the welcome message. */
    public void showWelcome() {
        System.out.println(
                BORDER_LINE
                + BANNER
                + "Good day Sir/Ma' am, I am jarvis.Jarvis, your friendly AI assistant\n"
                + "How may I be of service to you today ?\n"
                + BORDER_LINE
        );
    }

    /** Print the goodbye message. */
    public void sayGoodbye() {
        System.out.println(
                "Goodbye Sir/Ma' am. I hope to be of service to you again next time\n"
                + BORDER_LINE
        );
    }

    /** Print all tasks. */
    public void listAllTasks(List<Task> taskList) {
        System.out.println("\nHere are the list of things you had wished to do earlier Sir/Ma' am\n");
        // For loop is to iterate through the toDoTasks and print out all the tasks
        for (int i = 0; i < taskList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, taskList.get(i).toString());
        }
        System.out.println(BORDER_LINE);
    }
}
