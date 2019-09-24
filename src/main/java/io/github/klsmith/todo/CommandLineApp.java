package io.github.klsmith.todo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.github.klsmith.todo.task.JsonFileTaskRepository;
import io.github.klsmith.todo.task.Task;
import io.github.klsmith.todo.task.TaskParser;
import io.github.klsmith.todo.task.TaskRepository;

public class CommandLineApp {

	private static final int STATUS_OK = 0;
	private static final int STATUS_UNKNOWN_ERROR = 1;
	private static final int STATUS_BAD_COMMAND = 2;

	private static final String ADD_COMMAND = "add";
	private static final String LIST_COMMAND = "list";

	public static void main(String[] args) {
		final String command = args[0];
		final String[] tokens = Arrays.copyOfRange(args, 1, args.length);
		try {
			final int status;
			switch (command) {
			case ADD_COMMAND:
				status = add(tokens);
				break;
			case LIST_COMMAND:
				status = list();
				break;
			default:
				status = commandNotRecognized(command);
				break;
			}
			System.exit(status);
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.exit(STATUS_UNKNOWN_ERROR);
		}
	}

	private static int add(String... tokens) {
		final TaskRepository repository = new JsonFileTaskRepository();
		final TaskParser parser = new TaskParser();
		final Task task = parser.parse(tokens);
		repository.add(task);
		System.out.printf("Task Added: %s%n", task.getDisplayString());
		return STATUS_OK;
	}

	private static int list() {
		final TaskRepository repository = new JsonFileTaskRepository();
		final List<Task> results = repository.getAll();
		Collections.sort(results);
		for (int i = 0; i < results.size(); i++) {
			final Task task = results.get(i);
			System.out.printf("%d. %s%n", i + 1, task.getDisplayString());
		}
		return STATUS_OK;
	}

	private static int commandNotRecognized(String command) {
		System.out.printf("Command \"%s\" not recognized.%n", command);
		return STATUS_BAD_COMMAND;
	}

}
