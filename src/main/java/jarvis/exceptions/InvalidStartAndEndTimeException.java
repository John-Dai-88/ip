package jarvis.exceptions;

/** Represents invalid pair of start and end date & time */
public class InvalidStartAndEndTimeException extends JarvisException {

    /** Creates an exception with the specified message.
     *
     * @param message Error message to display.
     */
    public InvalidStartAndEndTimeException(String message) {
        super(message);
    }
}
