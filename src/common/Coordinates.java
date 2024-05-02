package common;

/**
 * A class representing a coordinate on the board.
 */
public class Coordinates {
    private final int column;
    private final int row;

    /**
     * Creates a new coordinate with the given parameters.
     *
     * @param row    the row coordinate.
     * @param column the column coordinate.
     */
    public Coordinates(int row, int column) {
        this.column = column;
        this.row = row;
    }

    /**
     * Gets the x coordinate.
     *
     * @return the x coordinate.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate.
     */
    public int getRow() {
        return row;
    }
}
