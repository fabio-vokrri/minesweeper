package common.enums;

public enum Command {
    OPEN("o"), FLAG("f"), NONE("n");

    private final String key;

    Command(String key) {
        this.key = key;
    }

    static public Command fromKey(String c) {
        for (Command command : Command.values()) {
            if (command.key.equals(c)) return command;
        }

        return Command.NONE;
    }

    public String getKey() {
        return key;
    }
}
