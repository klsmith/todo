package io.github.klsmith.todo.cli;

import io.github.klsmith.todo.core.importance.Importance;
import io.github.klsmith.todo.core.task.Task;
import io.github.klsmith.todo.core.urgency.Urgency;
import io.github.klsmith.todo.core.urgency.UrgencyFactory;
import io.github.klsmith.util.StringUtil;

public class TaskParser {

	public TaskParser() {
	}

	public Task parse(String string) {
		final String[] tokens = StringUtil.splitOnSpaceWithQuoteEscapes(string);
		return parse(tokens);
	}

	public Task parse(String... tokens) {
		final String text = tokens[0];
		final String importanceString = tokens[1];
		final Importance importance = Importance.valueOf(importanceString.toUpperCase());
		final String urgencyString = tokens[2];
		final Urgency urgency = UrgencyFactory.fromString(urgencyString)
				.orElseThrow(() -> new IllegalStateException("Could not parse urgency='" + urgencyString + "'"));
		return new Task(text, false, importance, urgency);
	}

}
