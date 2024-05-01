package common.enums;

public enum Direction {
    UP("W"), DOWN("S"), LEFT("A"), RIGHT("D");

    private final String key;

    Direction(String key) {
        this.key = key;
    }

    static public Direction fromKey(String c) {
        for (Direction direction : Direction.values()) {
            if (direction.key.equals(c)) return direction;
        }

        return null;
    }

    public String getKey() {
        return key;
    }
}
