package jarvis.ui;

import jarvis.classes.Task;
import jarvis.exceptions.JarvisException;

/**
 * Runs the Jarvis command-line chatbot and handles user commands.
 *
 * <p>This class is responsible for reading commands from the user,
 * delegating task-related operations to {@link JarvisController}, and displaying
 * the appropriate responses through {@link Ui}.</p>
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
        JarvisController jarvisGui = new JarvisController();

        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();

            try {
                if (userInput.equals("Jarvis, clip that")) {
                    ui.showClipThat();
                } else if (userInput.equals("list")) {
                    ui.listAllTasks(jarvisGui.getTasks());
                } else if (userInput.startsWith("find")) {
                    ui.listAllTasks(jarvisGui.filterTasks(userInput));
                } else if (userInput.startsWith("mark")) {
                    jarvisGui.markTaskAs(
                            userInput,
                            Task.CompletionStatus.DONE);
                } else if (userInput.startsWith("unmark")) {
                    jarvisGui.markTaskAs(
                            userInput,
                            Task.CompletionStatus.UNDONE);
                } else if (userInput.startsWith("todo")) {
                    jarvisGui.createToDoTask(userInput);
                } else if (userInput.startsWith("deadline")) {
                    jarvisGui.createDeadlineTask(userInput);
                } else if (userInput.startsWith("event")) {
                    jarvisGui.createEventTask(userInput);
                } else if (userInput.startsWith("delete")) {
                    jarvisGui.deleteTask(userInput);
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
