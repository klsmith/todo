package io.github.klsmith.todo.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import io.github.klsmith.todo.core.importance.Importance;
import io.github.klsmith.todo.core.task.Task;
import io.github.klsmith.todo.core.urgency.UrgencyFactory;

class TaskParserTest {

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2019, 9, 6, 10, 30);
    private static final String TEST_TEXT = "TEST TEXT";

    private final TaskParser parser = new TaskParser();

    @Test
    void testParseNeedAsap() {
        final Task expected = new Task(TEST_TEXT, false, Importance.NEED, UrgencyFactory.asap());
        final Task actual = parser.parse("\"TEST TEXT\" NEED ASAP");
        assertEquals(expected, actual);
    }

    @Test
    void testParseWantWhenever() {
        final Task expected = new Task(TEST_TEXT, false, Importance.WANT, UrgencyFactory.whenever());
        final Task actual = parser.parse("\"TEST TEXT\" WANT WHENEVER");
        assertEquals(expected, actual);
    }

    @Test
    void testParseNoneDate() {
        final Task expected = new Task(TEST_TEXT, false, Importance.NONE, UrgencyFactory.fromDate(TEST_DATE));
        final Task actual = parser.parse("\"TEST TEXT\" NONE 2019-9-6-10:30");
        assertEquals(expected, actual);
    }

}
