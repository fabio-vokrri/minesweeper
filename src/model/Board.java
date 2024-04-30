package model;

import common.Coordinates;

import java.util.Random;

/**
 * Model class of a board.
 */
public class Board {
    private final Tile[][] tiles;
    private final int rows;
    private final int columns;
    private final int numberOfBombs;

    /**
     * Creates a new Board with the given parameters.
     *
     * @param rows          the number of rows the board is made of.
     * @param columns       the number of columns the board is made of.
     * @param numberOfBombs the number of bombs on the board.
     */
    public Board(int rows, int columns, int numberOfBombs) {
        this.rows = rows;
        this.columns = columns;
        this.tiles = new Tile[rows][columns];
        this.numberOfBombs = numberOfBombs;

        // initializes the board with empty tiles.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }

        // places the bombs randomly on the board.
        for (int i = 0; i < numberOfBombs; i++) {
            Random random = new Random();
            int randomRow, randomColumn;

            do {
                randomRow = random.nextInt(rows);
                randomColumn = random.nextInt(columns);
            } while (tiles[randomRow][randomColumn].hasBomb());

            tiles[randomRow][randomColumn].setBomb();
        }

        // calculates the number of adjacent bombs for every tile
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int adjacentBombs = countAdjacentBombs(new Coordinates(i, j));
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
        return tiles[coordinates.getX()][coordinates.getY()];
    }

    /**
     * Counts the adjacent bombs for the given row and column.
     *
     * @param coordinate the coordinate of the tile
     * @return the number of adjacent bombs.
     */
    private int countAdjacentBombs(Coordinates coordinate) {
        if (tiles[coordinate.getX()][coordinate.getY()].hasBomb()) return -1;

        int count = 0;
        for (int i = coordinate.getX() - 1; i <= coordinate.getX() + 1; i++) {
            if (i < 0 || i >= rows) continue;
            for (int j = coordinate.getY() - 1; j <= coordinate.getY() + 1; j++) {
                if (j < 0 || j >= columns) continue;
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
        return this.rows;
    }

    /**
     * Gets the number of columns the board is made of.
     *
     * @return the number of columns.
     */
    public int getNumberOfColumns() {
        return this.columns;
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
