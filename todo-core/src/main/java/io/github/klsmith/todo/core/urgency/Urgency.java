package io.github.klsmith.todo.core.urgency;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Urgency implements Comparable<Urgency> {

	private final UrgencyLevel level;
	private final LocalDateTime dateTime;

	/**
	 * Only for use by subclasses and {@link UrgencyFactory}.
	 */
	protected Urgency(UrgencyLevel level, LocalDateTime date) {
		this.level = level;
		this.dateTime = date;
		validate();
	}

	private void validate() {
		if (null == level) {
			throw new InvalidUrgencyException("Cannot have a null urgency level.");
		}
		if (UrgencyLevel.DATE == level && null == dateTime) {
			throw new InvalidUrgencyException("Cannot have null dateTime on DATE level urgency.");
		}
		if ((UrgencyLevel.ASAP == level || UrgencyLevel.WHENEVER == level)
				&& null != dateTime) {
			throw new InvalidUrgencyException(
					String.format("Cannot have a dateTime on non-DATE level urgency %s.", level));
		}
	}

	public Optional<LocalDateTime> getDate() {
		return Optional.ofNullable(dateTime);
	}

	public UrgencyLevel getLevel() {
		return level;
	}

	@Override
	public int hashCode() {
		return Objects.hash(level, dateTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Urgency) {
			final Urgency other = (Urgency) obj;
			return level == other.level
					&& Objects.equals(dateTime, other.dateTime);
		}
		return false;
	}

	@Override
	public int compareTo(Urgency other) {
		if (UrgencyLevel.DATE == level &&
				UrgencyLevel.DATE == other.level) {
			return dateTime.compareTo(other.dateTime);
		}
		return level.compareTo(other.level);
	}

	@Override
	public String toString() {
		return String.format("Urgency(%s, %s)", level, dateTime);
	}

	public String getDisplayString() {
		if (UrgencyLevel.DATE == level) {
			return dateTime.toString();
		}
		return level.toString();
	}

}
