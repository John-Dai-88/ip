package jarvis.ui;

import java.time.LocalDateTime;

/** Format user inputted date and time. */
public class DateTimeData {

    private final String task;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;

    /**
     * Create object to store deadline task and its deadline.
     *
     * @param task
     * @param endDateTime
     */
    public DateTimeData(String task, LocalDateTime endDateTime) {
        this.task = task;
        this.startDateTime = null;
        this.endDateTime = endDateTime;
    }

    /**
     * Creates DateTimeData object to store event task,
     * its start and end date&time.
     *
     * @param task
     * @param startDateTime
     * @param endDateTime
     */
    public DateTimeData(String task, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.task = task;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Return the name of task.
     *
     * @return Name of task.
     */
    public String getTask() {
        return task;
    }

    /**
     * Return the starting date and time.
     *
     * @return The starting date and time.
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Return the ending date and time.
     *
     * @return The ending date and time.
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
