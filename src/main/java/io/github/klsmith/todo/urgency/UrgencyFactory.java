package io.github.klsmith.todo.urgency;

import java.time.LocalDateTime;

public final class UrgencyFactory {

    protected static final Urgency ASAP_INSTANCE = new Urgency(UrgencyLevel.ASAP, null);
    protected static final Urgency NONE_INSTANCE = new Urgency(UrgencyLevel.NONE, null);

    private UrgencyFactory() {}

    public static Urgency asap() {
        return ASAP_INSTANCE;
    }

    public static Urgency none() {
        return NONE_INSTANCE;
    }

    public static Urgency fromDate(LocalDateTime date) {
        return new Urgency(UrgencyLevel.DATE, date);
    }

}
