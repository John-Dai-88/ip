package Classes;

/** Represents a task that must be completed by a specified deadline. */
public class Deadline extends Task {
    /** Stores the deadline text. */
    private String deadline;

    /** Creates a deadline task.
     *
     * @param taskName Description of the task.
     * @param deadline Deadline text.
     */
    public Deadline(String taskName, String deadline) {
        super(taskName);
        this.deadline = deadline;
    }

    /** Returns the deadline task in its display format.
     *
     * @return Formatted deadline task.
     */
    @Override
    public String toString() {
        String dateAndTime = String.format(" (by: %s)", deadline);
        return "[D]" + super.toString() + dateAndTime;
    }
}
