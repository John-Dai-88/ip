package jarvis.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jarvis.classes.Deadline;
import jarvis.classes.Event;
import jarvis.classes.ToDo;
import jarvis.exceptions.IncompleteCommandException;
import jarvis.exceptions.InvalidDateAndTimeException;
import jarvis.exceptions.InvalidStartAndEndTimeException;
import jarvis.exceptions.TooSimpleArgumentException;


/** Parses user commands and converts them into tasks or command parameters. */
public class Parser {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String FROM_COMMAND = "/from";
    private static final String TO_COMMAND = "/to";
    private static final String BY_COMMAND = "/by";
    private static final String TODO_COMMAND = "todo ";
    private static final String DEADLINE_COMMAND = "deadline ";
    private static final String EVENT_COMMAND = "event ";
    private static final String FIND_COMMAND = "find";

    /**
     * Extracts the description of a todo task.
     *
     * @param userInput User's command.
     * @return A ToDo containing the task.
     * @throws IncompleteCommandException If the command is incomplete.
     */
    public static ToDo parseToDo(String userInput)
            throws IncompleteCommandException {

        if (userInput.length() < 5) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : todo <Task>\n"
            );
        }

        String task = userInput.substring(TODO_COMMAND.length()).trim();

        if (task.isEmpty()) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : todo <Task>\n"
            );
        }

        return new ToDo(task);
    }

    /**
     * Extracts the task and deadline from a deadline command.
     *
     * @param userInput User's command.
     * @return A Deadline containing the task and deadline.
     * @throws IncompleteCommandException If the command is incomplete.
     * @throws InvalidDateAndTimeException If the date/date and time is incomplete
     */
    public static Deadline parseDeadline(String userInput)
            throws IncompleteCommandException, InvalidDateAndTimeException {

        int positionOfBy = userInput.indexOf(BY_COMMAND);

        if (positionOfBy == -1) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : "
                            + "deadline <Task> /by <yyyy-MM-DD [HH:mm]>\n"
            );
        }

        String task = userInput.substring(DEADLINE_COMMAND.length(), positionOfBy).trim();
        String deadline = userInput.substring(positionOfBy + BY_COMMAND.length()).trim();

        if (task.isEmpty() || deadline.isEmpty()) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : "
                            + "deadline <Task> /by <yyyy-MM-DD [HH:mm]>\n"
            );
        }

        LocalDateTime deadlineDateAndTime;

        try {
            // Attempt parsing date and time first
            deadlineDateAndTime = LocalDateTime.parse(deadline, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException error1) {

            try {
                // Retry to see user only inputted date
                LocalDate deadlineDate = LocalDate.parse(deadline, DATE_FORMATTER);

                // Converts a date-only deadline to a date-time deadline.
                deadlineDateAndTime = deadlineDate.atStartOfDay();
            } catch (DateTimeParseException error2) {
                throw new InvalidDateAndTimeException(
                        "Error : Invalid date/ date & time format.\n"
                                + "Please use either one of the format below : \n"
                                + "For date only : deadline <Task> /by yyyy-MM-dd \n"
                                + "For date and time : deadline <Task> /by yyyy-MM-dd HH:mm\n"
                                + "Ex : deadline <Task> /by 2026-08-22 18:00\n"
                );
            }
        }

        return new Deadline(task, deadlineDateAndTime);
    }

    /**
     * Extracts the task, start date/time and end date/time from an event command.
     *
     * @param userInput User's command.
     * @return An Event containing the task, start date or/and time and end date or/and time.
     * @throws IncompleteCommandException If the command is incomplete.
     * @throws InvalidDateAndTimeException If the date/time is incomplete
     * @throws InvalidStartAndEndTimeException If the end datetime is before the start date/time
     */
    public static Event parseEvent(String userInput)
            throws IncompleteCommandException,
            InvalidDateAndTimeException,
            InvalidStartAndEndTimeException {

        int positionOfFrom = userInput.indexOf(FROM_COMMAND);
        int positionOfTo = userInput.indexOf(TO_COMMAND);

        if (positionOfFrom == -1
                || positionOfTo == -1
                || positionOfFrom >= positionOfTo) {

            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : "
                            + "event <Task> /from <yyyy-MM-DD [HH:mm]> /to <yyyy-MM-DD [HH:mm]>\n"
            );
        }

        String task = userInput.substring(EVENT_COMMAND.length(), positionOfFrom).trim();

        String startDateTime =
                userInput.substring(positionOfFrom + FROM_COMMAND.length(), positionOfTo).trim();

        String endDateTime =
                userInput.substring(positionOfTo + TO_COMMAND.length()).trim();

        if (task.isEmpty()
                || startDateTime.isEmpty()
                || endDateTime.isEmpty()) {

            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command in the format : "
                            + "event <Task> /from <yyyy-MM-DD [HH:mm]> /to <yyyy-MM-DD [HH:mm]>\n"
            );
        }

        LocalDateTime eventStartDateAndTime;
        LocalDateTime eventEndDateAndTime;

        try {
            // Attempt parsing date and time first
            eventStartDateAndTime = LocalDateTime.parse(startDateTime, DATE_TIME_FORMATTER);
            eventEndDateAndTime = LocalDateTime.parse(endDateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException error1) {

            try {
                // Retry to see user only inputted date
                LocalDate eventStartDate;
                LocalDate eventEndDate;

                eventStartDate = LocalDate.parse(startDateTime, DATE_FORMATTER);
                eventEndDate = LocalDate.parse(endDateTime, DATE_FORMATTER);

                // Converts a date-only event to a date-time event.
                eventStartDateAndTime = eventStartDate.atStartOfDay();
                eventEndDateAndTime = eventEndDate.atStartOfDay();
            } catch (DateTimeParseException error2) {
                throw new InvalidDateAndTimeException(
                        "Error : Invalid date/date & time format.\n"
                                + "Please use either one of the format below : \n"
                                + "For date only : event <Task> /from yyyy-MM-dd /to yyyy-MM-dd\n"
                                + "For date and time : event <Task> /from yyyy-MM-dd HH:mm /to yyyy-MM-dd HH:mm\n"
                                + "Ex : event <Task> /from 2026-08-27 12:00 /to 2026-08-28 12:00\n"
                );
            }
        }

        if (eventEndDateAndTime.isBefore(eventStartDateAndTime)) {
            throw new InvalidStartAndEndTimeException(
                    "Error : Invalid start/end date/date & time. \n"
                            + "Please ensure that the start date is before the end date \n"
                            + "and the end date is after the start date, respectively. \n"
            );
        }

        return new Event(task, eventStartDateAndTime, eventEndDateAndTime);
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

        String[] splitUserInput = userInput.trim().split("\\s+");

        try {
            return Integer.parseInt(splitUserInput[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException error) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command with a valid task number in the form.\n"
                            + "mark 1 / unmark 2 / delete 3\n"
            );
        }
    }

    /**
     * Extracts the keyword from a find command.
     *
     * @param userInput User's command containing the keyword.
     * @return The keyword to filter tasks by.
     * @throws IncompleteCommandException If no key words are inputted by user
     * @throws TooSimpleArgumentException If the key words are too general
     */
    public static String parseTaskKeyWord(String userInput)
            throws IncompleteCommandException, TooSimpleArgumentException {
        String taskKeyWord = userInput.substring(FIND_COMMAND.length()).trim().toLowerCase();

        if (taskKeyWord.isEmpty()) {
            throw new IncompleteCommandException(
                    "Error : Your command is missing certain parameters.\n"
                            + "Please re-enter your command with a key word in the format : find <key word>.\n"
            );
        } else if (taskKeyWord.length() == 1) {
            throw new TooSimpleArgumentException(
                    "Error : Your <Key Word> is too general.\n"
                            + "Please re-enter your command with a key word of minimum 2 characters.\n"
            );
        }

        return taskKeyWord;
    }


}
