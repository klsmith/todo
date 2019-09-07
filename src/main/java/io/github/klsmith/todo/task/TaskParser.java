package io.github.klsmith.todo.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.github.klsmith.todo.importance.Importance;
import io.github.klsmith.todo.urgency.Urgency;
import io.github.klsmith.todo.urgency.UrgencyFactory;
import io.github.klsmith.util.StringUtil;

public class TaskParser {

    private final DateTimeFormatter formatter;

    public TaskParser() {
        formatter = DateTimeFormatter.ofPattern("uuuu-M-d;H:mm");
    }

    public Task parse(String string) {
        final String[] tokens = StringUtil.splitOnSpaceWithQuoteEscapes(string);
        return parse(tokens);
    }

    public Task parse(String... tokens) {
        final String text = tokens[0];
        final String importanceString = tokens[1].substring(1);
        final String urgencyString = tokens[2].substring(1);
        final Importance importance = Importance.valueOf(importanceString);
        final Urgency urgency;
        switch (urgencyString) {
            case "ASAP":
                urgency = UrgencyFactory.asap();
                break;
            case "NONE":
                urgency = UrgencyFactory.none();
                break;
            default:
                urgency = UrgencyFactory.fromDate(LocalDateTime.parse(urgencyString, formatter));
        }
        return new Task(text, false, importance, urgency);
    }

}
