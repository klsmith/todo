package io.github.klsmith.todo.task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements TaskRepository {

    private final List<Task> list;

    public InMemoryTaskRepository() {
        list = new ArrayList<>();
    }

    public InMemoryTaskRepository(List<Task> existingData) {
        this();
        list.addAll(existingData);
    }

    @Override
    public void add(Task task) {
        list.add(task);
    }

    @Override
    public List<Task> getAll() {
        return new ArrayList<>(list);
    }

}
