package io.github.klsmith.todo.core.urgency;

import static io.github.klsmith.asserts.OptionalAsserts.*;
import static io.github.klsmith.asserts.OptionalAsserts.assertIsPresent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class UrgencyFactoryTest {

    private static final LocalDateTime TEST_DATE = LocalDateTime.of(2019, 8, 6, 10, 30);

    @Test
    void testBuildAsapLevel() {
        final Urgency urgency = UrgencyFactory.asap();
        assertEquals(UrgencyLevel.ASAP, urgency.getLevel());
        assertIsNotPresent(urgency.getDate());
        assertSame(UrgencyFactory.ASAP_INSTANCE, urgency);
    }

    @Test
    void testBuildAsapFromString() {
        final Optional<Urgency> result = UrgencyFactory.fromString("ASAP");
        final Urgency urgency = assertIsPresent(result);
        assertEquals(UrgencyLevel.ASAP, urgency.getLevel());
        assertIsNotPresent(urgency.getDate());
        assertSame(UrgencyFactory.ASAP_INSTANCE, urgency);
    }

    @Test
    void testBuildLowerCaseAsapFromString() {
        final Optional<Urgency> result = UrgencyFactory.fromString("asap");
        final Urgency urgency = assertIsPresent(result);
        assertEquals(UrgencyLevel.ASAP, urgency.getLevel());
        assertIsNotPresent(urgency.getDate());
        assertSame(UrgencyFactory.ASAP_INSTANCE, urgency);
    }

    @Test
    void testBuildWheneverLevel() {
        final Urgency urgency = UrgencyFactory.whenever();
        assertEquals(UrgencyLevel.WHENEVER, urgency.getLevel());
        assertIsNotPresent(urgency.getDate());
        assertSame(UrgencyFactory.WHENEVER_INSTANCE, urgency);
    }

    @Test
    void testBuildWheneverFromString() {
        final Optional<Urgency> result = UrgencyFactory.fromString("WHENEVER");
        final Urgency urgency = assertIsPresent(result);
        assertEquals(UrgencyLevel.WHENEVER, urgency.getLevel());
        assertIsNotPresent(urgency.getDate());
        assertSame(UrgencyFactory.WHENEVER_INSTANCE, urgency);
    }

    @Test
    void testBuildLowerCaseWheneverFromString() {
        final Optional<Urgency> result = UrgencyFactory.fromString("whenever");
        final Urgency urgency = assertIsPresent(result);
        assertEquals(UrgencyLevel.WHENEVER, urgency.getLevel());
        assertIsNotPresent(urgency.getDate());
        assertSame(UrgencyFactory.WHENEVER_INSTANCE, urgency);
    }

    @Test
    void testBuildDateLevel() {
        final Urgency urgency = UrgencyFactory.fromDate(TEST_DATE);
        assertEquals(UrgencyLevel.DATE, urgency.getLevel());
        assertIsPresent(urgency.getDate());
        assertEquals(TEST_DATE, urgency.getDate().get());
    }

    @Test
    void testBuildDateFromString() {
        final Optional<Urgency> result = UrgencyFactory.fromString(
                UrgencyFactory.DATE_FORMAT.format(TEST_DATE));
        final Urgency urgency = assertIsPresent(result);
        assertEquals(UrgencyLevel.DATE, urgency.getLevel());
        assertIsPresent(urgency.getDate());
        assertEquals(TEST_DATE, urgency.getDate().get());
    }

    @Test
    void testBuildFromNonsenseString() {
        final Optional<Urgency> result = UrgencyFactory.fromString("Something Invalid");
        assertIsNotPresent(result);
    }

}
