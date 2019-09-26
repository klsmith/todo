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

	private enum Command {

		ADD {
			@Override
			void execute(String... tokens) {
				final TaskRepository repository = new JsonFileTaskRepository();
				final TaskParser parser = new TaskParser();
				final Task task = parser.parse(tokens);
				repository.add(task);
				System.out.printf("Task Added: %s%n", task.getDisplayString());
			}
		},

		LIST {
			@Override
			void execute(String... tokens) {
				final TaskRepository repository = new JsonFileTaskRepository();
				final List<Task> results = repository.getAll();
				Collections.sort(results);
				for (int i = 0; i < results.size(); i++) {
					final Task task = results.get(i);
					System.out.printf("%d. %s%n", i + 1, task.getDisplayString());
				}
				if (results.isEmpty()) {
					System.out.println("Your todo list is empty!");
				}
			}
		},

		DELETE {
			@Override
			void execute(String... tokens) {
				final TaskRepository repository = new JsonFileTaskRepository();
				final int listNumber = Integer.parseInt(tokens[0]);
				final Task removedTask = repository.removeByListNumber(listNumber);
				System.out.printf("Task Deleted: %s%n", removedTask.getDisplayString());
			}
		},

		COMPLETE {
			@Override
			void execute(String... tokens) {
				final TaskRepository repository = new JsonFileTaskRepository();
				final int listNumber = Integer.parseInt(tokens[0]);
				final Task completedTask = repository.completeByListNumber(listNumber);
				System.out.printf("Task Completed: %s%n", completedTask.getDisplayString());
			}
		},

		UNKNOWN {
			@Override
			void execute(String... tokens) {
				System.out.printf("Command \"%s\" not recognized.%n", tokens[0]);
			}
		};

		private static Command fromString(String string) {
			if (null != string) {
				if (string.isEmpty()) {
					return LIST;
				}
				for (Command command : values()) {
					if (command.name().equalsIgnoreCase(string)) {
						return command;
					}
				}
			}
			return UNKNOWN;
		}

		abstract void execute(String... tokens);

	}

	public static void main(String[] args) {
		final String command;
		final String[] tokens;
		if (args.length > 0) {
			command = args[0];
			tokens = Arrays.copyOfRange(args, 1, args.length);
		} else {
			command = "";
			tokens = new String[] {};
		}
		try {
			final int status;
			final Command c = Command.fromString(command);
			if (Command.UNKNOWN == c) {
				c.execute(command);
				status = STATUS_BAD_COMMAND;
			} else {
				c.execute(tokens);
				status = STATUS_OK;
			}
			System.exit(status);
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.exit(STATUS_UNKNOWN_ERROR);
		}
	}

}
