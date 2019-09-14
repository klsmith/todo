package io.github.klsmith.todo.urgency;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Urgency implements Comparable<Urgency> {

    private final UrgencyLevel level;
    private final LocalDateTime date;

    /**
     * Only for use by subclasses and {@link UrgencyFactory}.
     */
    protected Urgency(UrgencyLevel level, LocalDateTime date) {
        this.level = level;
        this.date = date;
        validate();
    }

    private void validate() {
        if (null == level) {
            throw new InvalidUrgencyException("Cannot have a null urgency level.");
        }
        if (UrgencyLevel.DATE == level && null == date) {
            throw new InvalidUrgencyException("Cannot have null date on DATE level urgency.");
        }
        if ((UrgencyLevel.ASAP == level || UrgencyLevel.WHENEVER == level)
                && null != date) {
            throw new InvalidUrgencyException(String.format("Cannot have a date on non-DATE level urgency %s.", level));
        }
    }

    public Optional<LocalDateTime> getDate() {
        return Optional.ofNullable(date);
    }

    public UrgencyLevel getLevel() {
        return level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, date);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Urgency) {
            final Urgency other = (Urgency) obj;
            return level == other.level
                    && Objects.equals(date, other.date);
        }
        return false;
    }

    @Override
    public int compareTo(Urgency other) {
        if (UrgencyLevel.DATE == level &&
                UrgencyLevel.DATE == other.level) {
            return date.compareTo(other.date);
        }
        return level.compareTo(other.level);
    }

    @Override
    public String toString() {
        return String.format("Urgency(%s, %s)", level, date);
    }

}
