package io.github.klsmith.todo;

import java.util.Arrays;

import io.github.klsmith.todo.task.Task;
import io.github.klsmith.todo.task.TaskParser;

public class CommandLineApp {

    private static final int STATUS_OK = 0;
    private static final int STATUS_BAD_COMMAND = 1;

    private static final String ADD_COMMAND = "add";

    public static void main(String[] args) {
        final String command = args[0];
        final String[] tokens = Arrays.copyOfRange(args, 1, args.length);
        final int status;
        switch (command) {
            case ADD_COMMAND:
                status = add(tokens);
                break;
            default:
                status = commandNotRecognized(command);
                break;
        }
        System.exit(status);
    }

    private static int add(String... tokens) {
        final TaskParser parser = new TaskParser();
        final Task task = parser.parse(tokens);
        System.out.printf("ADDING TASK: %s%n", task);
        return STATUS_OK;
    }

    private static int commandNotRecognized(String command) {
        System.out.printf("Command \"%s\" not recognized.%n", command);
        return STATUS_BAD_COMMAND;
    }
}
