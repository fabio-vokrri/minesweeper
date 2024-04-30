package model;

import common.Coordinates;
import common.Observable;

public class Game extends Observable {
    private final Board board;
    private final int remainingBombs;
    private int remainingTiles;
    private boolean won;
    private boolean lost;
    private Coordinates currentPointerCoordinates;

    /**
     * Creates a new Game with the given parameters.
     * <p>
     * Also initializes the board.
     *
     * @param rows          the number of rows the game's board is made of.
     * @param columns       the number of columns the game's board is made of.
     * @param numberOfBombs the number of bombs one the game's board.
     */
    public Game(int rows, int columns, int numberOfBombs) {
        this.board = new Board(rows, columns, numberOfBombs);
        this.remainingBombs = numberOfBombs;
        this.remainingTiles = rows * columns;
    }

    /**
     * Gets the game's board.
     *
     * @return the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the number of remaining bombs on the board.
     * <p>
     * The flagged ones do not count as remaining.
     *
     * @return the number of remaining bombs.
     */
    public int getRemainingBombs() {
        return remainingBombs;
    }

    /**
     * Gets the number of remaining tiles on the board.
     * <p>
     * The opened ones do not count as remaining.
     *
     * @return the number of remaining tiles.
     */
    public int getRemainingTiles() {
        return remainingTiles;
    }

    /**
     * Sets the number of remaining tiles to the given one.
     *
     * @param remainingTiles the number of remaining tiles to be set.
     */
    public void setRemainingTiles(int remainingTiles) {
        this.remainingTiles = remainingTiles;
    }

    /**
     * Decreases the number of remaining tile by one.
     */
    public void decreaseRemainingTiles() {
        remainingTiles--;
    }

    /**
     * Gets whether the player won the game.
     *
     * @return true if the player won the game.
     */
    public boolean isWon() {
        return won;
    }

    /**
     * Gets whether the player lost the game.
     *
     * @return true if the player lost the game.
     */
    public boolean isLost() {
        return lost;
    }

    /**
     * Gets the pointer coordinates.
     *
     * @return the pointer coordinates.
     */
    public Coordinates getCurrentPointerCoordinates() {
        return currentPointerCoordinates;
    }
}
