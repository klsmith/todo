package io.github.klsmith.todo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.klsmith.todo.importance.Importance;
import io.github.klsmith.todo.urgency.Urgency;
import io.github.klsmith.todo.urgency.UrgencyFactory;

class TaskTest {

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2019, 9, 6, 10, 30);

    private static Task newTestTask(Importance importance, Urgency urgency) {
        return new Task("TEST TEXT", false, importance, urgency);
    }

    @Test
    void testConstructTask() {
        final String text = "TEST TEXT";
        final boolean complete = true;
        final Importance importance = Importance.NEED;
        final Urgency urgency = UrgencyFactory.asap();
        final Task task = new Task(text, complete, importance, urgency);
        assertEquals(text, task.getText());
        assertEquals(complete, task.isComplete());
        assertEquals(importance, task.getImportance());
        assertEquals(urgency, task.getUrgency());
    }

    @Test
    void testIdentityEquality() {
        final String text = "TEST TEXT";
        final boolean complete = true;
        final Importance importance = Importance.NEED;
        final Urgency urgency = UrgencyFactory.asap();
        final Task task = new Task(text, complete, importance, urgency);
        assertTrue(task.equals(task), "Identity equality failure.");
        assertEquals(0, task.compareTo(task), "Identity compareTo failure.");
    }

    @Test
    void testSimpleEquality() {
        final String text = "TEST TEXT";
        final boolean complete = true;
        final Importance importance = Importance.NEED;
        final Urgency urgency = UrgencyFactory.asap();
        final Task taskA = new Task(text, complete, importance, urgency);
        final Task taskB = new Task(text, complete, importance, urgency);
        assertEquals(taskA, taskB);
        assertEquals(taskB, taskA);
        assertEquals(taskA.hashCode(), taskB.hashCode());
        assertEquals(0, taskA.compareTo(taskB));
        assertEquals(0, taskB.compareTo(taskA));
    }

    @Test
    void testSimpleInequality() {
        final String text = "TEST TEXT";
        final boolean complete = true;
        final Importance importance = Importance.NEED;
        final Urgency urgency = UrgencyFactory.asap();
        final Task taskA = new Task(text.toUpperCase(), complete, importance, urgency);
        final Task taskB = new Task(text.toLowerCase(), complete, importance, urgency);
        assertNotEquals(taskA, taskB);
        assertNotEquals(taskB, taskA);
        assertNotEquals(taskA.hashCode(), taskB.hashCode());
        assertNotEquals(0, taskA.compareTo(taskB));
        assertNotEquals(0, taskB.compareTo(taskA));
    }

    @Test
    void testSortTask() {
        final List<Task> expected = Arrays.asList(
                newTestTask(Importance.NEED, UrgencyFactory.asap()),
                newTestTask(Importance.NEED, UrgencyFactory.fromDate(TEST_DATE)),
                newTestTask(Importance.NEED, UrgencyFactory.fromDate(TEST_DATE.plusDays(1))),
                newTestTask(Importance.NEED, UrgencyFactory.none()),
                newTestTask(Importance.NEED, UrgencyFactory.none()),
                newTestTask(Importance.WANT, UrgencyFactory.asap()),
                newTestTask(Importance.WANT, UrgencyFactory.asap()),
                newTestTask(Importance.WANT, UrgencyFactory.fromDate(TEST_DATE)),
                newTestTask(Importance.WANT, UrgencyFactory.fromDate(TEST_DATE.plusDays(1))),
                newTestTask(Importance.WANT, UrgencyFactory.none()),
                newTestTask(Importance.NONE, UrgencyFactory.asap()),
                newTestTask(Importance.NONE, UrgencyFactory.fromDate(TEST_DATE)),
                newTestTask(Importance.NONE, UrgencyFactory.fromDate(TEST_DATE)),
                newTestTask(Importance.NONE, UrgencyFactory.fromDate(TEST_DATE.plusDays(1))),
                newTestTask(Importance.NONE, UrgencyFactory.none()));
        final List<Task> actual = new ArrayList<>(expected);
        Collections.reverse(actual);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

}
