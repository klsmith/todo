package io.github.klsmith.todo.cli;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.github.klsmith.todo.core.task.Task;
import io.github.klsmith.todo.core.task.TaskRepository;

public class Command {

	private final CommandType type;
	private final String[] tokens;

	public Command(String... args) {
		final CommandType temp = CommandType.fromArgs(args);
		if (CommandType.UNKNOWN == temp) {
			type = CommandType.ADD;
			tokens = args;
		} else if (args.length > 0) {
			type = temp;
			tokens = Arrays.copyOfRange(args, 1, args.length);
		} else {
			type = temp;
			tokens = new String[] {};
		}
	}

	public CommandType getType() {
		return type;
	}

	public Status execute() {
		final Status status;
		switch (type) {
		case ADD:
			status = add();
			break;
		case LIST:
			status = list();
			break;
		case REMOVE:
			status = remove();
			break;
		case COMPLETE:
			status = complete();
			break;
		default:
			status = badCommand();
			break;
		}
		return status;
	}

	private TaskRepository getRepository() {
		final CliConfig config = CliConfig.load();
		return new WebServiceTaskRepository(config.getBaseApiUri());
	}

	private Status add() {
		final TaskParser parser = new TaskParser();
		final Task task = parser.parse(tokens);
		getRepository().add(task);
		System.out.printf("Task Added: %s%n", task.getDisplayString());
		return Status.OK;
	}

	private Status list() {
		final List<Task> results = getRepository().getAll();
		Collections.sort(results);
		for (int i = 0; i < results.size(); i++) {
			final Task task = results.get(i);
			System.out.printf("%d. %s%n", i + 1, task.getDisplayString());
		}
		if (results.isEmpty()) {
			System.out.println("Your todo list is empty!");
		}
		return Status.OK;
	}

	private Status remove() {
		final int listNumber = Integer.parseInt(tokens[0]);
		final Task removedTask = getRepository().removeByListNumber(listNumber);
		System.out.printf("Task Removed: %s%n", removedTask.getDisplayString());
		return Status.OK;
	}

	private Status complete() {
		final int listNumber = Integer.parseInt(tokens[0]);
		final Task completedTask = getRepository().completeByListNumber(listNumber);
		System.out.printf("Task Completed: %s%n", completedTask.getDisplayString());
		return Status.OK;
	}

	private Status badCommand() {
		System.out.printf("Command \"%s\" not recognized.%n", tokens[0]);
		return Status.BAD_COMMAND;
	}

}
