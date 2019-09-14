package io.github.klsmith.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

class StringUtilTest {

    @Test
    void testSplitOnSpaceWithQuoteEscapes_SimpleCase() {
        final String input = "A \"B B\"";
        final String[] expected = new String[] {
                "A", "B B"
        };
        final String[] actual = StringUtil.splitOnSpaceWithQuoteEscapes(input);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSplitOnSpaceWithQuoteEscapes_QuotesFirst() {
        final String input = "\"A A\" B";
        final String[] expected = new String[] {
                "A A", "B"
        };
        final String[] actual = StringUtil.splitOnSpaceWithQuoteEscapes(input);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testSplitOnSpaceWithQuoteEscapes_MultipleQuotes() {
        final String input = "\"A A\" \"B B\"";
        final String[] expected = new String[] {
                "A A", "B B"
        };
        final String[] actual = StringUtil.splitOnSpaceWithQuoteEscapes(input);
        assertArrayEquals(expected, actual);
    }

}
