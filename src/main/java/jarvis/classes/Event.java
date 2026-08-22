package jarvis.classes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Represents a task that occurs between a start and end time. */
public class Event extends Task {
    /** Stores the event start time text. */
    private LocalDateTime startTime;
    /** Stores the event end time text. */
    private LocalDateTime endTime;
    /** Deadline format for toString() to follow. */
    private static final String DATE_TIME_FORMATTER = "MM dd yyyy HH:mm";
    private static final String DATE_FORMATTER = "MM dd yyyy";

    /** Creates an event task.
     *
     * @param taskName Description of the task.
     * @param startTime Event start time text.
     * @param endTime Event end time text.
     */
    public Event(String taskName, LocalDateTime startTime, LocalDateTime endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Return the start of the deadline.
     *
     * @return The date/date and time of the start of the deadline.
     */
    public LocalDateTime getStartDateTime() {
        return startTime;
    }

    /**
     * Return the end of the deadline.
     *
     * @return The date/date and time of the end of the deadline.
     */
    public LocalDateTime getEndDateTime() {
        return endTime;
    }

    /** Formats and returns the event task in its display format.
     *
     * @return Formatted event task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter;

        if(startTime.toLocalTime().equals(LocalTime.MIDNIGHT) && endTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        } else {
            formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMATTER);
        }

        String dateAndTime = String.format(" (from: %s to: %s)", startTime.format(formatter), endTime.format(formatter));
        return "[D]" + super.toString() + dateAndTime;
    }
}
