package storage;

import classes.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/** Handles saving and loading Jarvis tasks from the hard disk. */
public class Storage {

    // File location of where the tasks list data will be stored
    private static final String FILE_PATH = "./data/jarvis.dat";

    /** Saves all tasks to the hard disk.
     *
     * @param tasks List of tasks to save.
     */
    public static void saveTasks(List<Task> tasks) {
        // Create a file object where tasks will be written into
        File file = new File(FILE_PATH);

        try {
            // Create the data directory if it does not already exist
            File parentDirectory = file.getParentFile();

            // Checks if the data directory exists in user's directory
            if (parentDirectory != null && !parentDirectory.exists()) {
                parentDirectory.mkdirs();
            }

            // Write the tasks list to the file
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream(file));

            outputStream.writeObject(tasks);
            outputStream.close();

        } catch (IOException error) {
            System.err.println("Error: Unable to save tasks to the hard disk.");
        }
    }

    /** Loads all previously saved tasks from the hard disk.
     *
     * @return Previously saved tasks, or an empty list if no save file exists.
     */
    @SuppressWarnings("unchecked")
    public static List<Task> loadTasks() {
        // Create a file object where tasks will be written into
        File file = new File(FILE_PATH);

        // If there is no save file, start with an empty task list
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            // Reads and fetch the tasks list from the file
            ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream(file));

            List<Task> tasks = (List<Task>) inputStream.readObject();
            inputStream.close();

            return tasks;

        } catch (IOException | ClassNotFoundException error) {
            System.err.println("Error: Unable to load saved tasks.");
            return new ArrayList<>();
        }
    }
}