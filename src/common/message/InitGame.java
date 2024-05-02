package common.message;

public class InitGame implements Message {
    private final int rows, columns, numberOfBombs;

    public InitGame(int rows, int columns, int numberOfBombs) {
        this.rows = rows;
        this.columns = columns;
        this.numberOfBombs = numberOfBombs;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }
}
