package jarvis.classes;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Tests the behavior of {@link Deadline}. */
public class DeadlineTest {
    /** Verifies that a deadline with a date only formats correctly. */
    @Test
    public void constructor_dateOnlyDeadlineFormatsCorrectly() {
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2026, 8, 22, 0, 0));

        assertEquals(Task.CompletionStatus.UNDONE, deadline.getStatus());
        assertEquals("[D][] Submit report (by: 08 22 2026)", deadline.toString());
    }

    /** Verifies that a deadline with a time formats correctly. */
    @Test
    public void constructor_deadlineWithTimeFormatsCorrectly() {
        Deadline deadline = new Deadline("Submit report", LocalDateTime.of(2026, 8, 22, 23, 45));

        assertEquals("[D][] Submit report (by: 08 22 2026 23:45)", deadline.toString());
    }
}
