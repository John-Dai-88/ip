package Classes;

public class Task {
    // String variable to store the name of task (aka user input)
    private String taskName;
    private CompletionStatus completedStatus;

    public enum CompletionStatus {
        DONE,
        UNDONE
    }


    // Constructor to make new Task objects
    public Task(String taskName) {
        // Store user input as task name
        this.taskName = taskName;
        // Set done status of task to be false (by default)
        this.completedStatus = CompletionStatus.UNDONE;
    }

    // Function to set status of task to be true
    public void setCompletionStatus(CompletionStatus status) {
        this.completedStatus = status;
    }

    // Override toString() so that String format resembles
    // that of Level-3's sample output
    @Override
    public String toString() {
        // Checks if task is done, if so print status of task with [X]
        // Else print status of task with []
        if(completedStatus.equals(CompletionStatus.DONE)) {
            return "[X] "+this.taskName;
        }
        else {
            return "[] "+this.taskName;
        }
    }
}
