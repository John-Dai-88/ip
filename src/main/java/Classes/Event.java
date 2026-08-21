package classes;

/** Represents a task that occurs between a start and end time. */
public class Event extends Task {
    /** Stores the event start time text. */
    private String startTime;
    /** Stores the event end time text. */
    private String endTime;

    /** Creates an event task.
     *
     * @param taskName Description of the task.
     * @param startTime Event start time text.
     * @param endTime Event end time text.
     */
    public Event(String taskName, String startTime, String endTime) {
        super(taskName);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /** Returns the event task in its display format.
     *
     * @return Formatted event task.
     */
    @Override
    public String toString() {
        String dateAndTime = String.format(" (from: %s to: %s)", startTime, endTime);
        return "[E]" + super.toString() + dateAndTime;
    }
}
