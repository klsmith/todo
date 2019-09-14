package io.github.klsmith.todo;

import java.util.List;

import io.github.klsmith.todo.task.Task;
import io.github.klsmith.todo.task.TaskRepository;

public class TodoService {

    private final TaskRepository repository;

    public TodoService(TaskRepository repository) {
        this.repository = repository;
    }

    public void addTask(Task task) {
        repository.add(task);
    }

    public List<Task> getTaskList() {
        return repository.getAll();
    }

}
