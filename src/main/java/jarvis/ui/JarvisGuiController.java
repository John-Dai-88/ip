package jarvis.ui;

import java.util.List;
import java.util.function.Consumer;

import jarvis.backend.JarvisController;
import jarvis.backend.Parser;
import jarvis.classes.Task;
import jarvis.exceptions.InvalidTaskNumberException;
import jarvis.exceptions.JarvisException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for the Jarvis GUI.
 * Handles user interactions and displays tasks in the JavaFX interface.
 */
public class JarvisGuiController {

    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String LIST_COMMAND = "list";
    private static final String MARK_COMMAND = "mark";
    private static final String UNMARK_COMMAND = "unmark";
    private static final String DELETE_COMMAND = "delete";
    private static final String FIND_COMMAND = "find";
    private static final String BYE_COMMAND = "bye";
    private static final String UNKNOWN_COMMAND = "I'm sorry, I don't understand that command.\n"
            + "Available commands: todo, deadline, event, list, "
            + "mark, unmark, delete, find, bye";

    private static final String ADD_TASK_MSG_HEADER = "Got it. I've added this task:\n" + "  ";
    private static final String BYE_MESSAGE = "Goodbye! Your tasks have been saved.";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private JarvisController jarvisController;
    private Consumer<String> outputHandler;

    private final Image jarvisImage = new Image(this.getClass().getResourceAsStream("/images/jarvisPicture.png"));
    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/blankProfilePicture.png"));

    /**
     * Initializes the GUI controller.
     * Sets up the Jarvis controller and dialog display.
     */
    @FXML
    public void initialize() {
        jarvisController = new JarvisController();
        outputHandler = this::displayMessage;

        // Set up scroll pane to auto-scroll to bottom
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Set up send button action
        sendButton.setOnAction(event -> handleUserInput());

        // Set up enter key on text field
        userInput.setOnAction(event -> handleUserInput());

        // Display welcome message
        displayWelcomeMessage();
    }

    /**
     * Handles user input from the text field.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        // Display user input
        displayUserMessage(input);

        // Clear input field
        userInput.clear();

        // Process command
        processCommand(input);
    }

    /**
     * Processes the user command and executes the appropriate action.
     *
     * @param input User command.
     */
    private void processCommand(String input) {
        String lowerInput = input.toLowerCase().trim();

        try {
            if (lowerInput.startsWith(TODO_COMMAND)) {
                displayTodoTaskCreation(lowerInput);
            } else if (lowerInput.startsWith(DEADLINE_COMMAND)) {
                jarvisController.createDeadlineTask(input);
                displayMessage("Got it. I've added this task:\n"
                        + "  " + getLastTask().toString());
                displayTaskCount();
            } else if (lowerInput.startsWith(EVENT_COMMAND)) {
                jarvisController.createEventTask(input);
                displayMessage("Got it. I've added this task:\n"
                        + "  " + getLastTask().toString());
                displayTaskCount();
            } else if (lowerInput.equals(LIST_COMMAND)) {
                displayTasks();
            } else if (lowerInput.startsWith(MARK_COMMAND)) {
                jarvisController.markTaskAs(input, Task.CompletionStatus.DONE);
                displayMessage("Nice! I've marked this task as done:\n"
                        + "  " + getTaskFromCommand(input).toString());
            } else if (lowerInput.startsWith(UNMARK_COMMAND)) {
                jarvisController.markTaskAs(input, Task.CompletionStatus.UNDONE);
                displayMessage("OK, I've marked this task as not done yet:\n"
                        + "  " + getTaskFromCommand(input).toString());
            } else if (lowerInput.startsWith(DELETE_COMMAND)) {
                Task deletedTask = getTaskFromCommand(input);
                jarvisController.deleteTask(input);
                displayMessage("Noted. I've removed this task:\n"
                        + "  " + deletedTask.toString());
                displayTaskCount();
            } else if (lowerInput.startsWith(FIND_COMMAND)) {
                List<Task> matchingTasks = jarvisController.filterTasks(input);
                displayFindResults(matchingTasks);
            } else if (lowerInput.equals(BYE_COMMAND)) {
                displayByeCommand();
            } else {
                displayMessage(UNKNOWN_COMMAND);
            }
        } catch (JarvisException e) {
            displayMessage(e.getMessage());
        } catch (Exception e) {
            displayMessage("An unexpected error occurred: " + e.getMessage());
        }
    }

    /**
     * Create a new todo task and displays it to the user
     *
     * @param userInput User's command for todo task
     * @throws JarvisException If there is issue in creating and displaying todo task
     */
    private void displayTodoTaskCreation(String userInput) throws JarvisException {
        jarvisController.createToDoTask(userInput);
        displayMessage(ADD_TASK_MSG_HEADER + getLastTask().toString());
        displayTaskCount();
    }

    /**
     * Displays bye message and disables user input controls.
     */
    private void displayByeCommand() {
        displayMessage(BYE_MESSAGE);
        userInput.setDisable(true);
        sendButton.setDisable(true);
    }

    /**
     * Displays all tasks in the dialog container.
     */
    private void displayTasks() {
        List<Task> tasks = jarvisController.getTasks();
        if (tasks.isEmpty()) {
            displayMessage("You have no tasks in your list.");
            return;
        }

        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, tasks.get(i).toString()));
        }
        displayMessage(sb.toString());
    }

    /**
     * Displays find results.
     *
     * @param tasks Matching tasks.
     */
    private void displayFindResults(List<Task> tasks) {
        if (tasks.isEmpty()) {
            displayMessage("No matching tasks found.");
            return;
        }

        StringBuilder sb = new StringBuilder("Here are the matching tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, tasks.get(i).toString()));
        }
        displayMessage(sb.toString());
    }

    /**
     * Displays the task count.
     */
    private void displayTaskCount() {
        int size = jarvisController.size();
        displayMessage("Now you have " + size + " task" + (size > 1 ? "s" : "") + " in the list.");
    }

    /**
     * Displays a welcome message.
     */
    private void displayWelcomeMessage() {
        displayMessage("Hello! I'm Jarvis\n"
                + "What would you like me to do?\n"
                + "Type 'bye' to exit.");
    }

    /**
     * Displays a user message in the dialog container.
     *
     * @param message User message.
     */
    private void displayUserMessage(String message) {
        MessageBox userMessageBox = new MessageBox(message, userImage, true);
        dialogContainer.getChildren().add(userMessageBox);
    }

    /**
     * Displays a system message in the dialog container.
     *
     * @param message System message.
     */
    private void displayMessage(String message) {
        MessageBox jarvisMessageBox = new MessageBox(message, jarvisImage, false);
        dialogContainer.getChildren().add(jarvisMessageBox);
    }

    /**
     * Gets the last task from the task list.
     *
     * @return Last task.
     * @throws InvalidTaskNumberException If the number is invalid.
     */
    private Task getLastTask() throws InvalidTaskNumberException {
        assert jarvisController.size() > 0
                : "getLastTask should only be called when at least one task exists";

        return jarvisController.getTask(jarvisController.size() - 1);
    }

    /**
     * Gets a task from a command that includes a task number.
     *
     * @param input Command input.
     * @return Task at the specified index.
     */
    private Task getTaskFromCommand(String input) throws JarvisException {
        int taskNumber = Parser.parseTaskNumber(input);
        return jarvisController.getTask(taskNumber - 1);
    }
}
