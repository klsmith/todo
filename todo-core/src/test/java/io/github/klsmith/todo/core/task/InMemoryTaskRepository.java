package io.github.klsmith.todo.core.task;

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

    @Override
    public void remove(Task task) {
        list.remove(task);
    }

    @Override
    public Task removeByListNumber(int listNumber) {
        final List<Task> list = getAll();
        final Task task = list.get(listNumber - 1);
        list.remove(task);
        return task;
    }

    @Override
    public Task completeByListNumber(int listNumber) {
        final List<Task> list = getAll();
        final Task task = list.get(listNumber - 1);
        final Task completedTask = new Task(
                task.getText(),
                true,
                task.getImportance(),
                task.getUrgency());
        list.set(listNumber - 1, completedTask);
        return completedTask;
    }

}
