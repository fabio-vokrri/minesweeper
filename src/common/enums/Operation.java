package common.enums;

public enum Operation {
    OPEN("L"), FLAG("K"), NONE("N");

    private final String key;

    Operation(String key) {
        this.key = key;
    }

    static public Operation fromKey(String c) {
        for (Operation operation : Operation.values()) {
            if (operation.key.equals(c)) return operation;
        }

        return null;
    }

    public String getKey() {
        return key;
    }
}
