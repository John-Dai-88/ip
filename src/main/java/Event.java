public class Event extends Task {
    // String variable to store the start time for the task
    private String startTime;
    // String variable to store the end time for the task
    private String endTime;

    // Constructor to make new event task instances
    public Event(String taskName, String startTime, String endTime) {
        // Uses the parent constructor as Event is a subclass of Task
        super(taskName);
        // Stores the inputted start time for the event
        this.startTime = startTime;
        // Stores the inputted end time for the event
        this.endTime = endTime;
    }

    // Override toString() so that String format resembles
    // that of Level-4's sample output for event tasks
    @Override
    public String toString() {
        String dateAndTime = String.format(" (from: %s to: %s)", this.startTime, this.endTime);
        return "[E]"+super.toString()+dateAndTime;
    }
}