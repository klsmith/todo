package io.github.klsmith.todo.core.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class InMemoryTaskRepositoryTest implements TaskRepositoryTest {

    @Override
    public TaskRepository getTaskRepositoryImplementation() {
        return new InMemoryTaskRepository();
    }

    @Test
    void testTaskRepositoryStartsEmpty() {
        final InMemoryTaskRepository repo = new InMemoryTaskRepository();
        assertTrue(repo.getAll().isEmpty());
    }

    @Test
    void testCanBeInitialized() {
        final List<Task> initialList = Arrays.asList(
                TaskRepositoryTest.TASK_1,
                TaskRepositoryTest.TASK_2);
        final InMemoryTaskRepository repo = new InMemoryTaskRepository(initialList);
        assertEquals(initialList, repo.getAll());
    }

    @Test
    void testIntializationIsCopy() {
        final List<Task> initialList = new ArrayList<>();
        initialList.add(TaskRepositoryTest.TASK_1);
        initialList.add(TaskRepositoryTest.TASK_2);
        final InMemoryTaskRepository repo = new InMemoryTaskRepository(initialList);
        initialList.add(TaskRepositoryTest.TASK_3);
        assertNotEquals(initialList, repo.getAll());
    }

}
