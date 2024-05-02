package model;

import common.Coordinates;

import java.util.Random;

/**
 * Model class of a board.
 */
public class Board {
    private final Tile[][] tiles;
    private final int numberOfRows;
    private final int numberOfColumns;
    private final int numberOfBombs;

    /**
     * Creates a new Board with the given parameters.
     *
     * @param numberOfRows    the number of rows the board is made of.
     * @param numberOfColumns the number of columns the board is made of.
     * @param numberOfBombs   the number of bombs on the board.
     */
    public Board(int numberOfRows, int numberOfColumns, int numberOfBombs) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.tiles = new Tile[numberOfRows][numberOfColumns];
        this.numberOfBombs = numberOfBombs;

        // initializes the board with empty tiles.
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

        // places the bombs randomly on the board.
        for (int i = 0; i < numberOfBombs; i++) {
            Random random = new Random();
            int randomRow, randomColumn;

            do {
                randomRow = random.nextInt(numberOfRows);
                randomColumn = random.nextInt(numberOfColumns);
            } while (tiles[randomRow][randomColumn].hasBomb());

            tiles[randomRow][randomColumn].setBomb();
        }

        // calculates the number of adjacent bombs for every tile
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                int adjacentBombs = countAdjacentBombs(i, j);
                tiles[i][j].setAdjacentBombs(adjacentBombs);
            }
        }
    }

    /**
     * Gets the tile at the given row and column.
     *
     * @param row    the row of the tile.
     * @param column the column of the tile.
     * @return the tile at the given row and column.
     */
    public Tile getTileAt(int row, int column) {
        return tiles[row][column];
    }

    public Tile getTileAt(Coordinates coordinates) {
        return tiles[coordinates.getColumn()][coordinates.getRow()];
    }


    private int countAdjacentBombs(int row, int column) {
        if (tiles[row][column].hasBomb()) return -1;
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i >= numberOfRows) continue;
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j >= numberOfColumns) continue;
                if (tiles[i][j].hasBomb()) count++;
            }
        }

        return count;
    }

    /**
     * Gets the number of rows the board is made of.
     *
     * @return the number of rows.
     */
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    /**
     * Gets the number of columns the board is made of.
     *
     * @return the number of columns.
     */
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    /**
     * Gets the number of bombs on the board.
     *
     * @return the number of bombs.
     */
    public int getNumberOfBombs() {
        return this.numberOfBombs;
    }
}
