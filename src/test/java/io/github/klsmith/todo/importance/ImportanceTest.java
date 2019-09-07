package io.github.klsmith.todo.importance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.klsmith.todo.importance.Importance;

class ImportanceTest {

    @Test
    void testSorting() {
        final List<Importance> expected = Arrays.asList(Importance.values());
        final List<Importance> actual = new ArrayList<>(expected);
        Collections.reverse(actual);
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

}
