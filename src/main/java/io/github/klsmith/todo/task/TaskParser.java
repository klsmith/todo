package io.github.klsmith.todo.task;

import io.github.klsmith.todo.importance.Importance;
import io.github.klsmith.todo.urgency.Urgency;
import io.github.klsmith.todo.urgency.UrgencyFactory;
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
		final String importanceString = tokens[1].substring(1);
		final Importance importance = Importance.valueOf(importanceString);
		final String urgencyString = tokens[2].substring(1);
		final Urgency urgency = UrgencyFactory.fromString(urgencyString)
				.orElseThrow(() -> new IllegalStateException("Could not parse urgency='" + urgencyString + "'"));
		return new Task(text, false, importance, urgency);
	}

}
