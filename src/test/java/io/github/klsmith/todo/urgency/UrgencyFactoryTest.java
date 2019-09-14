package io.github.klsmith.todo.urgency;

import static io.github.klsmith.asserts.OptionalAsserts.assertIsNotPresent;
import static io.github.klsmith.asserts.OptionalAsserts.assertIsPresent;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;

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
    void testBuildWheneverLevel() {
        final Urgency urgency = UrgencyFactory.whenever();
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

}
