package jarvis.classes;

import java.io.Serializable;

/** Represents a task that can be marked as done or undone. */
public class Task implements  Serializable {
    /** Stores the task description. */
    private String taskName;
    /** Stores the task's completion status. */
    private CompletionStatus completedStatus;
    /** Declares the version identifier for Java serialization to verify class compatibility. */
    private static final long serialVersionUID = 1l;

    /** Represents the possible completion states of a task. */
    public enum CompletionStatus {
        DONE,
        UNDONE
    }


    /** Creates a task with an initially undone status.
     *
     * @param taskName Description of the task.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.completedStatus = CompletionStatus.UNDONE;
    }

    /** Sets the completion status of this task.
     *
     * @param status New completion status.
     */
    public void setCompletionStatus(CompletionStatus status) {
        this.completedStatus = status;
    }

    /**
     * Return the name of task.
     *
     * @return Name of task.
     */
    public String getTask() {
        return taskName;
    }

    /** Returns the task in the format used by the task list.
     *
     * @return Formatted task description and completion marker.
     */
    @Override
    public String toString() {
        // Checks if task is done, if so print status of task with [X]
        // Else print status of task with []
        if (completedStatus == CompletionStatus.DONE) {
            return "[X] " + taskName;
        } else {
            return "[] " + taskName;
        }
    }
}
