package io.github.klsmith.todo.cli;

public class CommandLineApp {

    public static void main(String[] args) {
        try {
            final Command command = new Command(args);
            final Status status = command.execute();
            System.exit(status.code());
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.exit(Status.UNKNOWN_ERROR.code());
        }
    }

}
