package io.github.klsmith.asserts;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

public final class OptionalAsserts {

    private OptionalAsserts() {}

    public static <T> void assertIsPresent(Optional<T> optional) {
        assertTrue(optional.isPresent(), "Expected a value, but the Optional was empty.");
    }

    public static <T> void assertIsNotPresent(Optional<T> optional) {
        assertFalse(optional.isPresent(), "Expected empty, but the Optional had a value.");
    }

}
