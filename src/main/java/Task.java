public class Task {
    // String variable to store the name of task (aka user input)
    private String taskName;
    // Boolean status to keep track if task is done
    private boolean completedStatus;

    // Constructor to make new Task objects
    public Task(String taskName) {
        // Store user input as task name
        this.taskName = taskName;
        // Set done status of task to be false (by default)
        this.completedStatus = false;
    }

    // Function to set done status of task to be true
    public void markAsDone() {
        this.completedStatus = true;
    }

    // Function to set done status of task to be false
    public void markAsUndone() {
        this.completedStatus = false;
    }

    // Override toString() so that String format resembles
    // that of Level-3's sample output
    @Override
    public String toString() {
        // Checks if task is done, if so print status of task with [X]
        // Else print status of task with []
        if(completedStatus) {
            return "[X] "+this.taskName;
        }
        else {
            return "[] "+this.taskName;
        }
    }
}
