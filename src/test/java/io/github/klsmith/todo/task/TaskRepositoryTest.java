package io.github.klsmith.todo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.klsmith.todo.importance.Importance;
import io.github.klsmith.todo.urgency.UrgencyFactory;

public interface TaskRepositoryTest {

    static final Task TASK_1 = new Task("TASK 1", false, Importance.NEED, UrgencyFactory.asap());
    static final Task TASK_2 = new Task("TASK 2", false, Importance.WANT, UrgencyFactory.whenever());
    static final Task TASK_3 = new Task("TASK 3", false, Importance.NONE, UrgencyFactory.whenever());

    TaskRepository getTaskRepositoryImplementation();

    @Test
    default void testResultListDoesNotMutateRepository() {
        final TaskRepository repo = getTaskRepositoryImplementation();
        final List<Task> firstList = repo.getAll();
        firstList.add(TASK_1);
        final List<Task> secondList = repo.getAll();
        assertNotEquals(firstList, secondList);
    }

    @Test
    default void testSimpleAdd() {
        final TaskRepository repo = getTaskRepositoryImplementation();
        repo.add(TASK_1);
        repo.add(TASK_2);
        final List<Task> expected = Arrays.asList(TASK_1, TASK_2);
        final List<Task> actual = repo.getAll();
        assertEquals(expected, actual);
    }

    @Test
    default void testSortingResultListDoesNotThrowException() {
        final TaskRepository repo = getTaskRepositoryImplementation();
        repo.add(TASK_1);
        repo.add(TASK_2);
        final List<Task> result = repo.getAll();
        Collections.sort(result);
        // verify exception is not thrown
    }

}
