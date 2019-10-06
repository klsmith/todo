package io.github.klsmith.todo.core.urgency;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public final class UrgencyFactory {

    protected static final Urgency ASAP_INSTANCE = new Urgency(UrgencyLevel.ASAP, null);
    protected static final Urgency WHENEVER_INSTANCE = new Urgency(UrgencyLevel.WHENEVER, null);
    protected static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("uuuu-M-d-H:mm");

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

    public static Optional<Urgency> fromString(String string) {
        Urgency result;
        switch (string.toUpperCase()) {
            case "ASAP":
                result = asap();
                break;
            case "WHENEVER":
                result = whenever();
                break;
            default:
                try {
                    result = UrgencyFactory.fromDate(LocalDateTime.parse(string, DATE_FORMAT));
                } catch (DateTimeParseException e) {
                    result = null;
                }
                break;
        }
        return Optional.ofNullable(result);
    }

}
