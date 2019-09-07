package io.github.klsmith.todo.task;

import java.util.Objects;

import io.github.klsmith.todo.importance.Importance;
import io.github.klsmith.todo.urgency.Urgency;

public class Task implements Comparable<Task> {

    private final String text;
    private final boolean complete;
    private final Importance importance;
    private final Urgency urgency;

    protected Task(String text, boolean complete, Importance importance, Urgency urgency) {
        this.text = text;
        this.complete = complete;
        this.importance = importance;
        this.urgency = urgency;
    }

    public String getText() {
        return text;
    }

    public boolean isComplete() {
        return complete;
    }

    public Importance getImportance() {
        return importance;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, complete, importance, urgency);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task) {
            final Task other = (Task) obj;
            return complete == other.complete
                    && importance == other.importance
                    && text.equals(other.text)
                    && urgency.equals(other.urgency);
        }
        return false;
    }

    @Override
    public int compareTo(Task other) {
        int result = importance.compareTo(other.importance);
        if (0 != result) {
            return result;
        }
        result = urgency.compareTo(other.urgency);
        if (0 != result) {
            return result;
        }
        return text.compareTo(other.text);
    }

    @Override
    public String toString() {
        return String.format("Task(\"%s\", %b, %s, %s)",
                text, complete, importance, urgency);
    }

}
