package jarvis.cli;

import java.util.List;
import java.util.Scanner;

import jarvis.backend.Parser;
import jarvis.classes.Task;
import jarvis.exceptions.JarvisException;
import jarvis.backend.JarvisController;

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

    private static final String BORDER_LINE =
            "---------------------------------------------------------------------\n";

    private final JarvisController jarvisController;

    /**
     * Test
     */
    public JarvisCli() {
        jarvisController = new JarvisController();
    }

    /**
     * Starts the Jarvis Chatbot CLI interface
     *
     * @param args
     */
    public static void main(String[] args) {
        JarvisCli cli = new JarvisCli();
        cli.runCli();
    }

    /**
     * Test
     */
    public void runCli() {
        Scanner scanner = new Scanner(System.in);

        ui.showWelcome();

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Goodbye! Your tasks have been saved.");
                break;
            }

            processCommand(input);
        }

        scanner.close();
    }

    /**
     * Starts the Jarvis chatbot and continuously processes user commands
     * until the user enters {@code bye}.
     *
     * @param userInput
     */
    public void processCommand(String userInput) {

        try {
            if (userInput.equals("Jarvis, clip that")) {
                ui.showClipThat();
            } else if (userInput.equals("list")) {
                printAllTasks();
            } else if (userInput.startsWith("find")) {
                printFindResults(userInput);
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
                printLastAddedTask();
            } else if (userInput.startsWith("deadline")) {
                jarvisController.createDeadlineTask(userInput);
                printLastAddedTask();
            } else if (userInput.startsWith("event")) {
                jarvisController.createEventTask(userInput);
                printLastAddedTask();
            } else if (userInput.startsWith("delete")) {
                deleteTask(userInput);
            } else if (userInput.equals("bye")) {
                ui.sayGoodbye();
            } else {
                ui.showUnknownCommand();
            }
        } catch (JarvisException error) {
            ui.showError(error.getMessage());
            ui.showError(BORDER_LINE);
        }
    }

    /**
     * Test
     */
    private void printLastAddedTask() {
        Task task = jarvisController.getTask(
                jarvisController.size() - 1);

        System.out.printf(
                "Very well Sir/Ma' am, I have added the following task below:%n"
                        + "%s%n"
                        + "Please do note Sir/Ma' am, that you now currently "
                        + "have %d task(s) awaiting you%n"
                        + "%s",
                task,
                jarvisController.size(),
                BORDER_LINE);
    }

    /**
     * Test
     */
    private void printAllTasks() {
        List<Task> tasks = jarvisController.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("You have no tasks in your list.");
            System.out.println(BORDER_LINE);
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, tasks.get(i));
        }

        System.out.println(BORDER_LINE);
    }

    /**
     * Test
     *
     * @param input
     * @throws JarvisException
     */
    private void deleteTask(String input) throws JarvisException {
        int taskNumber = Parser.parseTaskNumber(input);
        Task task = jarvisController.getTask(taskNumber - 1);

        jarvisController.deleteTask(input);

        System.out.printf(
                "Very good Sir/Ma' am, I have removed the following task "
                        + "from your list of tasks-to-do:%n"
                        + "%s%n"
                        + "Please do note Sir/Ma' am, now you have %d "
                        + "task(s) awaiting you%n"
                        + "%s",
                task,
                jarvisController.size(),
                BORDER_LINE);
    }

    /**
     * Test
     *
     * @param input
     * @throws JarvisException
     */
    private void printFindResults(String input) throws JarvisException {
        List<Task> tasks = jarvisController.filterTasks(input);

        if (tasks.isEmpty()) {
            System.out.println("No matching tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, tasks.get(i));
            }
        }

        System.out.println(BORDER_LINE);
    }
}
