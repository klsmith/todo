package io.github.klsmith.util;

public final class StringUtil {

    private static final String REGEX_SPLIT_ON_SPACE_WITH_QUOTE_ESCAPES = "\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?";

    private StringUtil() {}

    public static String[] splitOnSpaceWithQuoteEscapes(String string) {
        // if first token has quotes, remove the first one
        // this is a workaround for a quirk in the regex
        return string.replaceAll("^\"", "").split(REGEX_SPLIT_ON_SPACE_WITH_QUOTE_ESCAPES);
    }

}
