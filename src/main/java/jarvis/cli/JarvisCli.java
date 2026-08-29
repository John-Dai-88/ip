package jarvis.cli;

import jarvis.classes.Task;
import jarvis.exceptions.JarvisException;
import jarvis.ui.JarvisController;

/**
 * Runs the Jarvis command-line chatbot and handles user commands.
 *
 * This class is responsible for reading commands from the user,
 * delegating task-related operations to {@link JarvisController}, and displaying
 * the appropriate responses through {@link Ui}.
 */
public class JarvisCli {
    /** Handles input and output for the command-line interface. */
    private static final Ui ui = new Ui();

    /**
     * Starts the Jarvis chatbot and continuously processes user commands
     * until the user enters {@code bye}.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        JarvisController jarvisController = new JarvisController();

        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();

            try {
                if (userInput.equals("Jarvis, clip that")) {
                    ui.showClipThat();
                } else if (userInput.equals("list")) {
                    ui.listAllTasks(jarvisController.getTasks());
                } else if (userInput.startsWith("find")) {
                    ui.listAllTasks(jarvisController.filterTasks(userInput));
                } else if (userInput.startsWith("mark")) {
                    jarvisController.markTaskAs(
                            userInput,
                            Task.CompletionStatus.DONE);
                } else if (userInput.startsWith("unmark")) {
                    jarvisController.markTaskAs(
                            userInput,
                            Task.CompletionStatus.UNDONE);
                } else if (userInput.startsWith("todo")) {
                    jarvisController.createToDoTask(userInput);
                } else if (userInput.startsWith("deadline")) {
                    jarvisController.createDeadlineTask(userInput);
                } else if (userInput.startsWith("event")) {
                    jarvisController.createEventTask(userInput);
                } else if (userInput.startsWith("delete")) {
                    jarvisController.deleteTask(userInput);
                } else if (userInput.equals("bye")) {
                    ui.sayGoodbye();
                    break;
                } else {
                    ui.showUnknownCommand();
                }
            } catch (JarvisException error) {
                ui.showError(error.getMessage());
            }
        }
    }
}
