/**
 * Runs the Jarvis chatbot and displays its welcome and goodbye messages.
 */

import java.lang.classfile.instruction.SwitchCase;
import java.util.Scanner;

public class Jarvis {
    private static String banner, borderLine, initialMsg, exitMsg, clipThatMsg;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
                + "Good day Sir/Ma' am, I am Jarvis, your friendly AI assistant\n"
                + "How may I be of service to you today ?\n"
                + borderLine;

        exitMsg = borderLine
                + "Goodbye Sir/Ma' am. I hope to be of service to you again next time\n"
                + borderLine;

        clipThatMsg = borderLine
                    + "Clipped and Ready to ship Sir\n"
                    + borderLine;

        System.out.println(initialMsg);

        while (true) {
            String userInput = scanner.nextLine();

            switch(userInput) {
                case "bye":
                    System.out.println(exitMsg);
                    break;
                case "Jarvis, clip that":
                    System.out.println(clipThatMsg);
                    break;
                default:
                    System.out.println(userInput+"\n");
            }

            if(userInput.equals("bye")) {
                break;
            }
        }
    }
}
