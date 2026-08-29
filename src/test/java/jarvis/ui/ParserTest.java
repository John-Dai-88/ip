package jarvis.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import jarvis.classes.Deadline;
import jarvis.classes.Event;
import jarvis.exceptions.IncompleteCommandException;
import jarvis.exceptions.InvalidDateAndTimeException;
import jarvis.exceptions.InvalidStartAndEndTimeException;
import jarvis.exceptions.TooSimpleArgumentException;

/** Tests command parsing behavior. */
public class ParserTest {
    /** Verifies that a todo command produces the expected description. */
    @Test
    public void parseToDo_validCommand_returnsTask() throws Exception {
        assertEquals("[T][] Buy milk", Parser.parseToDo("todo Buy milk").toString());
    }

    /** Verifies that an incomplete todo command is rejected. */
    @Test
    public void parseToDo_missingDescription_throwsException() {
        assertThrows(IncompleteCommandException.class, () -> Parser.parseToDo("todo"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseToDo("todo "));
    }

    /** Verifies that date-only and date-time deadlines are supported. */
    @Test
    public void parseDeadline_validCommands_returnsDeadlines() throws Exception {
        Deadline dateOnly = Parser.parseDeadline("deadline Submit report /by 2026-08-22");
        Deadline dateTime = Parser.parseDeadline("deadline Submit report /by 2026-08-22 18:00");

        assertEquals("[D][] Submit report (by: 08 22 2026)", dateOnly.toString());
        assertEquals("[D][] Submit report (by: 08 22 2026 18:00)", dateTime.toString());
    }

    /** Verifies that an incomplete deadline command is rejected. */
    @Test
    public void parseDeadline_missingDescription_throwsException() {
        assertThrows(IncompleteCommandException.class, () -> Parser.parseDeadline("deadline"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseDeadline("deadline "));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseDeadline("deadline /by"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseDeadline("deadline /by "));
    }

    /** Verifies that invalid deadline dates are rejected. */
    @Test
    public void parseDeadline_invalidDate_throwsException() {
        String tomorrow = "deadline Submit report /by tomorrow";
        String yearOnly = "deadline Submit report /by 2026";
        String monthOnly = "deadline Submit report /by 2026-08";
        String shortDay = "deadline Submit report /by 2026-08-7";
        String shortMinute = "deadline Submit report /by 2026-08-07 00:";
        String incompleteMinute = "deadline Submit report /by 2026-08-08 00:0";

        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseDeadline(tomorrow));
        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseDeadline(yearOnly));
        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseDeadline(monthOnly));
        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseDeadline(shortDay));
        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseDeadline(shortMinute));
        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseDeadline(incompleteMinute));
    }


    /** Verifies that events parse their start and end times. */
    @Test
    public void parseEvent_validCommand_returnsEvent() throws Exception {
        Event dateOnly = Parser.parseEvent("event Meeting /from 2026-08-22 /to 2026-08-22");
        Event dateTime = Parser.parseEvent("event Meeting /from 2026-08-22 09:00 /to 2026-08-22 10:00");

        assertEquals("[E][] Meeting (from: 08 22 2026 to: 08 22 2026)", dateOnly.toString());
        assertEquals("[E][] Meeting (from: 08 22 2026 09:00 to: 08 22 2026 10:00)", dateTime.toString());
    }

    /** Verifies that an incomplete event command is rejected. */
    @Test
    public void parseEvent_missingDescription_throwsException() {
        assertThrows(IncompleteCommandException.class, () -> Parser.parseEvent("event /from /to"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseEvent("event Meeting /from "));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseEvent("event Meeting /from 2026-08-27 /to"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseEvent("event Meeting /from 2026-08-27 /to "));
    }

    /** Verifies that invalid event dates are rejected. */
    @Test
    public void parseEvent_invalidDate_throwsException() {
        String invalidDay = "event Meeting /from 2026-08-99 /to 2026-08-99";
        String invalidMonth = "event Meeting /from 2026-99-27 /to 2026-99-28";
        String invalidStartTime = "event Meeting /from 2026-08-27 99:99 /to 2026-08-28 00:00";
        String invalidEndTime = "event Meeting /from 2026-08-27 00:00 /to 2026-08-28 99:99";

        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseEvent(invalidDay));
        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseEvent(invalidMonth));
        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseEvent(invalidStartTime));
        assertThrows(InvalidDateAndTimeException.class, () -> Parser.parseEvent(invalidEndTime));
    }

    /** Verifies that an event ending before it starts is rejected. */
    @Test
    public void parseEvent_endBeforeStart_throwsException() {
        String endBeforeStart = "event Meeting /from 2026-08-23 /to 2026-08-22";
        String endBeforeStartWithTime =
                "event Meeting /from 2026-08-22 10:00 /to 2026-08-22 09:00";

        assertThrows(InvalidStartAndEndTimeException.class, () -> Parser.parseEvent(endBeforeStart));
        assertThrows(InvalidStartAndEndTimeException.class, () -> Parser.parseEvent(endBeforeStartWithTime));
    }

    /** Verifies that task numbers are parsed from commands. */
    @Test
    public void parseTaskNumber_validCommand_returnsNumber() throws Exception {
        assertEquals(3, Parser.parseTaskNumber("delete 3"));
        assertEquals(2, Parser.parseTaskNumber("mark 2"));
        assertEquals(8, Parser.parseTaskNumber("unmark 8"));
    }

    /** Verifies that an incomplete mark/unmark/delete command is rejected. */
    @Test
    public void parseTaskNumber_missingDescription_throwsException() {
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskNumber("delete"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskNumber("delete "));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskNumber("mark"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskNumber("mark "));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskNumber("unmark"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskNumber("unmark "));
    }

    /** Verifies that key words are parsed from commands. */
    @Test
    public void parseKeyWord_validCommand_returnsKeyWord() throws Exception {
        assertEquals("book", Parser.parseTaskKeyWord("find book"));
        assertEquals("read", Parser.parseTaskKeyWord("find read"));
        assertEquals("book", Parser.parseTaskKeyWord("find BOOK"));
        assertEquals("read", Parser.parseTaskKeyWord("find READ"));
    }

    /** Verifies that an incomplete find command is rejected. */
    @Test
    public void parseKeyWord_missingDescription_throwsException() {
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskKeyWord("find"));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskKeyWord("find "));
        assertThrows(IncompleteCommandException.class, () -> Parser.parseTaskKeyWord("find    "));
    }

    /** Verifies that a simple find key word is rejected. */
    @Test
    public void parseKeyWord_simpleKeyWord_throwsException() {
        assertThrows(TooSimpleArgumentException.class, () -> Parser.parseTaskKeyWord("find a"));
        assertThrows(TooSimpleArgumentException.class, () -> Parser.parseTaskKeyWord("find A"));
        assertThrows(TooSimpleArgumentException.class, () -> Parser.parseTaskKeyWord("find 1"));
    }
}
