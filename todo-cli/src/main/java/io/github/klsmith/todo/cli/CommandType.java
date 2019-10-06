package io.github.klsmith.todo.cli;

import java.util.Arrays;
import java.util.List;

public enum CommandType {

    ADD, LIST(""), REMOVE("rm"), COMPLETE, UNKNOWN;

    private final List<String> aliasList;

    private CommandType(String... aliasArray) {
        aliasList = Arrays.asList(aliasArray);
    }

    public static CommandType fromArgs(String... args) {
        final String commandString = args.length > 0 ? args[0] : "";
        if (null != commandString) {
            for (CommandType command : values()) {
                if (command.isCommandAlias(commandString)) {
                    return command;
                }
            }
        }
        return UNKNOWN;
    }

    public boolean isCommandAlias(String string) {
        if (this == UNKNOWN) {
            return false;
        }
        return name().equalsIgnoreCase(string)
                || aliasList.contains(string);
    }

}
