package jarvis.ui;

import java.util.List;
import java.util.function.Consumer;

import jarvis.classes.Task;
import jarvis.exceptions.JarvisException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Controller for the Jarvis GUI.
 * Handles user interactions and displays tasks in the JavaFX interface.
 */
public class JarvisGuiController {

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
            if (lowerInput.startsWith("todo ")) {
                jarvisController.createToDoTask(input);
                displayMessage("Got it. I've added this task:\n"
                        + "  " + getLastTask().toString());
                displayTaskCount();
            } else if (lowerInput.startsWith("deadline ")) {
                jarvisController.createDeadlineTask(input);
                displayMessage("Got it. I've added this task:\n"
                        + "  " + getLastTask().toString());
                displayTaskCount();
            } else if (lowerInput.startsWith("event ")) {
                jarvisController.createEventTask(input);
                displayMessage("Got it. I've added this task:\n"
                        + "  " + getLastTask().toString());
                displayTaskCount();
            } else if (lowerInput.startsWith("list")) {
                displayTasks();
            } else if (lowerInput.startsWith("mark ")) {
                jarvisController.markTaskAs(input, Task.CompletionStatus.DONE);
                displayMessage("Nice! I've marked this task as done:\n"
                        + "  " + getTaskFromCommand(input).toString());
            } else if (lowerInput.startsWith("unmark ")) {
                jarvisController.markTaskAs(input, Task.CompletionStatus.UNDONE);
                displayMessage("OK, I've marked this task as not done yet:\n"
                        + "  " + getTaskFromCommand(input).toString());
            } else if (lowerInput.startsWith("delete ")) {
                Task deletedTask = getTaskFromCommand(input);
                jarvisController.deleteTask(input);
                displayMessage("Noted. I've removed this task:\n"
                        + "  " + deletedTask.toString());
                displayTaskCount();
            } else if (lowerInput.startsWith("find ")) {
                List<Task> matchingTasks = jarvisController.filterTasks(input);
                displayFindResults(matchingTasks);
            } else if (lowerInput.equals("bye")) {
                displayMessage("Goodbye! Your tasks have been saved.");
                userInput.setDisable(true);
                sendButton.setDisable(true);
            } else {
                displayMessage("I'm sorry, I don't understand that command.\n"
                        + "Available commands: todo, deadline, event, list, "
                        + "mark, unmark, delete, find, bye");
            }
        } catch (JarvisException e) {
            displayMessage(e.getMessage());
        } catch (Exception e) {
            displayMessage("An unexpected error occurred: " + e.getMessage());
        }
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
        Label userLabel = new Label(message);
        userLabel.setStyle("-fx-background-color: #DCF8C6; "
                + "-fx-padding: 10px; "
                + "-fx-border-radius: 10px; "
                + "-fx-background-radius: 10px;");
        dialogContainer.getChildren().add(userLabel);
    }

    /**
     * Displays a system message in the dialog container.
     *
     * @param message System message.
     */
    private void displayMessage(String message) {
        Label jarvisLabel = new Label(message);
        jarvisLabel.setStyle("-fx-background-color: #E8E8E8; "
                + "-fx-padding: 10px; "
                + "-fx-border-radius: 10px; "
                + "-fx-background-radius: 10px; "
                + "-fx-wrap-text: true;");
        jarvisLabel.setMaxWidth(500);
        dialogContainer.getChildren().add(jarvisLabel);
    }

    /**
     * Gets the last task from the task list.
     *
     * @return Last task.
     */
    private Task getLastTask() {
        return jarvisController.getTask(jarvisController.size() - 1);
    }

    /**
     * Gets a task from a command that includes a task number.
     *
     * @param input Command input.
     * @return Task at the specified index.
     * @throws JarvisException If the task number is invalid.
     */
    private Task getTaskFromCommand(String input) throws JarvisException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new JarvisException("Please specify a task number.");
        }
        try {
            int taskNumber = Integer.parseInt(parts[1]);
            return jarvisController.getTask(taskNumber - 1);
        } catch (NumberFormatException e) {
            throw new JarvisException("Invalid task number. Please enter a number.");
        } catch (IndexOutOfBoundsException e) {
            throw new JarvisException("Task number out of range.");
        }
    }

    /**
     * Gets the Jarvis controller instance.
     *
     * @return JarvisController instance.
     */
    public JarvisController getJarvisController() {
        return jarvisController;
    }
}