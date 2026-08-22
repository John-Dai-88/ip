package jarvis.ui;

import jarvis.exceptions.IncompleteCommandException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    private static final String BORDER_LINE =
            "---------------------------------------------------------------------\n";

    // To format date&Time based on the example in Level-8
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Extracts the description of a todo task.
     *
     * @param userInput User's command.
     * @return The todo task description.
     * @throws IncompleteCommandException If the command is incomplete.
     */
    public static String parseToDo(String userInput)
            throws IncompleteCommandException {

        String task = userInput.substring(5).trim();

        if (task.isEmpty()) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : todo <Task>\n"
                            + BORDER_LINE
            );
        }

        return task;
    }

    /**
     * Extracts the task and deadline from a deadline command.
     *
     * @param userInput User's command.
     * @return An array containing task and deadline.
     * @throws IncompleteCommandException If the command is incomplete.
     */
    public static DateTimeData parseDeadline(String userInput)
            throws IncompleteCommandException {

        int positionOfBy = userInput.indexOf("/by");

        if (positionOfBy == -1) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : "
                            + "deadline <Task> /by <yyyy-MM-DD [HH:mm]>\n"
                            + BORDER_LINE
            );
        }

        String task = userInput.substring(9, positionOfBy).trim();
        String deadline = userInput.substring(positionOfBy + 3).trim();

        LocalDateTime deadlineDateAndTime;

        try {
            // Attempt parsing date and time first
            deadlineDateAndTime = LocalDateTime.parse(deadline, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException error1) {

            try {
                // Retry to see user only inputted date
                LocalDate deadlineDate = LocalDate.parse(deadline, DATE_FORMATTER);

                // Convert date only deadline to date and time deadline
                deadlineDateAndTime = deadlineDate.atStartOfDay();
            } catch (DateTimeParseException error2) {
                throw new IncompleteCommandException(
                        "Error : Invalid date/ date & time format.\n"
                                + "Please use either one of the format below : \n"
                                + "For date only : yyyy-MM-dd \n"
                                + "For date and time : yy-MM-dd HH:mm\n"
                                + "Ex : deadline <Task> /by 2026-08-22 18:00\n"
                                + BORDER_LINE
                );
            }
        }

        if (task.isEmpty() || deadline.isEmpty()) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : "
                            + "deadline <Task> /by <yyyy-MM-DD [HH:mm]>\n"
                            + BORDER_LINE
            );
        }

        return new DateTimeData(task, deadlineDateAndTime);
    }

    /**
     * Extracts the task, start date/time and end date/time from an event command.
     *
     * @param userInput User's command.
     * @return An array containing task, start date/time and end date/time.
     * @throws IncompleteCommandException If the command is incomplete.
     */
    public static String[] parseEvent(String userInput)
            throws IncompleteCommandException {

        int positionOfFrom = userInput.indexOf("/from");
        int positionOfTo = userInput.indexOf("/to");

        if (positionOfFrom == -1
                || positionOfTo == -1
                || positionOfFrom >= positionOfTo) {

            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : "
                            + "event <Task> /from <yyyy-MM-DD [HH:mm]> /to <yyyy-MM-DD [HH:mm]>\n"
                            + BORDER_LINE
            );
        }

        String task = userInput.substring(6, positionOfFrom).trim();

        String startDateTime =
                userInput.substring(positionOfFrom + 5, positionOfTo).trim();

        String endDateTime =
                userInput.substring(positionOfTo + 3).trim();

        if (task.isEmpty()
                || startDateTime.isEmpty()
                || endDateTime.isEmpty()) {

            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : "
                            + "event <Task> /from <yyyy-MM-DD [HH:mm]> /to <yyyy-MM-DD [HH:mm]>\n"
                            + BORDER_LINE
            );
        }

        return new String[]{task, startDateTime, endDateTime};
    }

    /**
     * Extracts the task number from a mark/unmark/delete command.
     *
     * @param userInput User's command.
     * @return The task number.
     * @throws IncompleteCommandException If no valid number is provided.
     */
    public static int parseTaskNumber(String userInput)
            throws IncompleteCommandException {

        String[] splitUserInput = userInput.split(" ");

        try {
            return Integer.parseInt(splitUserInput[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException error) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command with a valid task number.\n"
                            + BORDER_LINE
            );
        }
    }
}
