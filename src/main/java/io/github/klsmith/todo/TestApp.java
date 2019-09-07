package io.github.klsmith.todo;

import java.util.Arrays;

import io.github.klsmith.todo.task.Task;
import io.github.klsmith.todo.task.TaskParser;

public class TestApp {

    public static void main(String[] args) {
        final String command = args[0];
        final TaskParser parser = new TaskParser();
        final String[] tokens = Arrays.copyOfRange(args, 1, args.length);
        final Task task = parser.parse(tokens);
        System.out.printf("%s: %s%n", command, task);
    }

}
