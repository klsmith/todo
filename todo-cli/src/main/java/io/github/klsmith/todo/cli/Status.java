package io.github.klsmith.todo.cli;

public enum Status {

    OK, UNKNOWN_ERROR, BAD_COMMAND;

    public int code() {
        return ordinal();
    }

}
