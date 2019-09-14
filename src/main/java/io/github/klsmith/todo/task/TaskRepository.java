package io.github.klsmith.todo.task;

import java.util.List;

public interface TaskRepository {

    public void add(Task task);

    public List<Task> getAll();

}
