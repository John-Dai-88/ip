package jarvis.classes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/** Tests the behavior of {@link Event}. */
public class EventTest {
    /** Verifies that an event formats date-only start and end values correctly. */
    @Test
    public void constructor_dateOnlyEventFormatsCorrectly() {
        Event event = new Event("Read book",
                LocalDateTime.of(2026, 8, 22, 0, 0),
                LocalDateTime.of(2026, 8, 23, 0, 0));

        assertEquals(Task.CompletionStatus.UNDONE, event.getStatus());
        assertEquals("[E][] Read book (from: 08 22 2026 to: 08 23 2026)", event.toString());
    }

    /** Verifies that a date only event can be marked as done and displays the done marker. */
    @Test
    public void setCompletionStatus_doneDateOnlyEventDisplaysDoneMarker() {
        Event event = new Event("Read book",
                LocalDateTime.of(2026, 8, 22, 0, 0),
                LocalDateTime.of(2026, 8, 23, 0, 0));

        event.setCompletionStatus(Task.CompletionStatus.DONE);

        assertEquals(Task.CompletionStatus.DONE, event.getStatus());
        assertEquals("[E][X] Read book (from: 08 22 2026 to: 08 23 2026)", event.toString());
    }

    /** Verifies that a completed date only event can be marked undone again. */
    @Test
    public void setCompletionStatus_undoneDateOnlyEventDisplaysUndoneMarker() {
        Event event = new Event("Read book",
                LocalDateTime.of(2026, 8, 22, 0, 0),
                LocalDateTime.of(2026, 8, 23, 0, 0));

        event.setCompletionStatus(Task.CompletionStatus.DONE);
        event.setCompletionStatus(Task.CompletionStatus.UNDONE);

        assertEquals(Task.CompletionStatus.UNDONE, event.getStatus());
        assertEquals("[E][] Read book (from: 08 22 2026 to: 08 23 2026)", event.toString());
    }

    /** Verifies that an event formats start and end date-time values correctly. */
    @Test
    public void constructor_eventWithTimeFormatsCorrectly() {
        Event event = new Event("Read book",
                LocalDateTime.of(2026, 8, 22, 9, 30),
                LocalDateTime.of(2026, 8, 22, 10, 30));

        assertEquals("[E][] Read book (from: 08 22 2026 09:30 to: 08 22 2026 10:30)",
                event.toString());
    }

    /** Verifies that a date and time event can be marked as done and displays the done marker. */
    @Test
    public void setCompletionStatus_doneDateAndTimeEventDisplaysDoneMarker() {
        Event event = new Event("Read book",
                LocalDateTime.of(2026, 8, 22, 9, 30),
                LocalDateTime.of(2026, 8, 23, 12, 30));

        event.setCompletionStatus(Task.CompletionStatus.DONE);

        assertEquals(Task.CompletionStatus.DONE, event.getStatus());
        assertEquals("[E][X] Read book (from: 08 22 2026 09:30 to: 08 23 2026 12:30)", event.toString());
    }

    /** Verifies that a completed date and time event can be marked undone again. */
    @Test
    public void setCompletionStatus_undoneDateAndTimeEventDisplaysUndoneMarker() {
        Event event = new Event("Read book",
                LocalDateTime.of(2026, 8, 22, 17, 45),
                LocalDateTime.of(2026, 8, 23, 20, 50));

        event.setCompletionStatus(Task.CompletionStatus.DONE);
        event.setCompletionStatus(Task.CompletionStatus.UNDONE);

        assertEquals(Task.CompletionStatus.UNDONE, event.getStatus());
        assertEquals("[E][] Read book (from: 08 22 2026 17:45 to: 08 23 2026 20:50)", event.toString());
    }

}
