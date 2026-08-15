/**
 * Runs the Jarvis chatbot and displays its welcome and goodbye messages.
 */
public class Jarvis {
    private static String banner, borderLine, initialMsg;

    public static void main(String[] args) {
        banner =
                  "     ██╗ █████╗ ██████╗ ██╗   ██╗██╗███████╗\n"
                + "     ██║██╔══██╗██╔══██╗██║   ██║██║██╔════╝\n"
                + "     ██║███████║██████╔╝██║   ██║██║███████╗\n"
                + "██   ██║██╔══██║██╔══██╗╚██╗ ██╔╝██║╚════██║\n"
                + "╚█████╔╝██║  ██║██║  ██║ ╚████╔╝ ██║███████║\n"
                + " ╚════╝ ╚═╝  ╚═╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝\n";

        borderLine = "____________________________________________________________\n";

        initialMsg = borderLine
                + banner
                + "Hello, I am Jarvis, your friendly AI assistant!\n"
                + "What can I do for you ?\n"
                + borderLine
                + "Goodbye, I await for your next visit !\n"
                + borderLine;

        System.out.println(initialMsg);
    }
}
