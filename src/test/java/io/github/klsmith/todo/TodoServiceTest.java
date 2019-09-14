package io.github.klsmith.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.klsmith.todo.importance.Importance;
import io.github.klsmith.todo.task.InMemoryTaskRepository;
import io.github.klsmith.todo.task.Task;
import io.github.klsmith.todo.task.TaskRepository;
import io.github.klsmith.todo.urgency.UrgencyFactory;

class TodoServiceTest {

    private static final Task TASK_1 = new Task("TASK 1", false, Importance.NEED, UrgencyFactory.asap());

    @Test
    void testAddThenListTask() {
        final TodoService service = new TodoService(new InMemoryTaskRepository());
        service.addTask(TASK_1);
        final List<Task> expected = Arrays.asList(TASK_1);
        final List<Task> actual = service.getTaskList();
        assertEquals(expected, actual);
    }

    @Test
    void testAddThenListAcrossInstances() {
        final TaskRepository repository = new InMemoryTaskRepository();
        final TodoService serviceA = new TodoService(repository);
        serviceA.addTask(TASK_1);
        final TodoService serviceB = new TodoService(repository);
        final List<Task> expected = Arrays.asList(TASK_1);
        final List<Task> actual = serviceB.getTaskList();
        assertEquals(expected, actual);
    }

}
