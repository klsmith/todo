package io.github.klsmith.todo.urgency;

import java.time.LocalDateTime;

public final class UrgencyFactory {

    protected static final Urgency ASAP_INSTANCE = new Urgency(UrgencyLevel.ASAP, null);
    protected static final Urgency WHENEVER_INSTANCE = new Urgency(UrgencyLevel.WHENEVER, null);

    private UrgencyFactory() {}

    public static Urgency asap() {
        return ASAP_INSTANCE;
    }

    public static Urgency whenever() {
        return WHENEVER_INSTANCE;
    }

    public static Urgency fromDate(LocalDateTime date) {
        return new Urgency(UrgencyLevel.DATE, date);
    }

}
