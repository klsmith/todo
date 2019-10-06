package io.github.klsmith.todo.core.task;

import java.util.List;

public interface TaskRepository {

    public void add(Task task);

    public List<Task> getAll();

    public void remove(Task task);

    public Task removeByListNumber(int listNumber);

    public Task completeByListNumber(int listNumber);

}
