package Classes;

public class Deadline extends Task{
    // String variable to store the deadline for the task
    private String deadline;

    // Constructor to make new deadline task instances
    public Deadline(String taskName, String deadline) {
        // Uses the parent constructor as Deadline is a subclass of Task
        super(taskName);
        // Stores the inputted deadline
        this.deadline = deadline;
    }

    // Override toString() so that String format resembles
    // that of Level-4's sample output for deadline tasks
    @Override
    public String toString() {
        String dateAndTime = String.format(" (by: %s)", this.deadline);
        return "[D]"+super.toString()+dateAndTime;
    }
 }