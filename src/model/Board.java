package model;

import java.util.Random;

public class Board {
    private final Tile[][] tiles;
    private final int rows;
    private final int columns;

    public Board(int rows, int columns, int numberOfBombs) {
        this.tiles = new Tile[rows][columns];
        this.rows = rows;
        this.columns = columns;

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
                int adjacentBombs = countAdjacentBombs(i, j);
                tiles[i][j].setAdjacentBombs(adjacentBombs);
            }
        }
    }

    public Tile getTileAt(int row, int column) {
        return tiles[row][column];
    }

    /**
     * Counts the adjacent bombs for the given row and column
     *
     * @param row    the row of the tile
     * @param column the column of the tile
     * @return the number of adjacent bombs
     */
    private int countAdjacentBombs(int row, int column) {
        if (tiles[row][column].hasBomb()) return -1;

        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i >= rows) continue;
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j >= columns) continue;
                if (tiles[i][j].hasBomb()) count++;
            }
        }

        return count;
    }
}
