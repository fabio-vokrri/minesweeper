package common;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int row, int column) {
        this.x = row;
        this.y = column;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
