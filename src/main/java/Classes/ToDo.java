package Classes;

public class ToDo extends Task {

    // Constructor to make new todo task instances
    public ToDo(String taskName) {
        // Uses the parent constructor as ToDo is a subclass of Task
        super(taskName);
    }

    // Override toString() so that String format resembles
    // that of Level-4's sample output for todo tasks
    @Override
    public String toString() {
        return "[T]"+super.toString();
    }
 }
