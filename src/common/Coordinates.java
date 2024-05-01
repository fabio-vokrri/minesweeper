package common;

/**
 * A class representing a coordinate on the board.
 */
public class Coordinates {
    private int column;
    private int row;

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

    public void increaseRow() {
        this.row++;
    }

    public void decreaseRow() {
        this.row--;
    }

    public void increaseColumn() {
        this.column++;
    }

    public void decreaseColumn() {
        this.column--;
    }
}
