package model;

import common.Coordinates;
import common.Observable;
import common.message.GameViewMessage;

public class Game extends Observable {
    private Board board;

    private int remainingBombs;
    private int remainingTiles;

    private boolean won;
    private boolean lost;
    private Coordinates pointerCoordinates;

    public Game() {
        board = null;
    }

    public void init(int rows, int columns, int numberOfBombs) {
        this.board = new Board(rows, columns, numberOfBombs);
        this.pointerCoordinates = new Coordinates(0, 0);

        notifyObservers(new GameViewMessage(new GameView(this)));
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
     * Gets the coordinates of the pointer on the board.
     *
     * @return the pointer coordinates.
     */
    public Coordinates getPointerCoordinates() {
        return this.pointerCoordinates;
    }

    /**
     * Sets the pointer coordinates to the given ones.
     *
     * @param pointerCoordinates the pointer coordinates to be set.
     */
    public void setPointerCoordinates(Coordinates pointerCoordinates) {
        this.pointerCoordinates = pointerCoordinates;
    }

    public void increaseRow() {
        this.pointerCoordinates.increaseRow();
    }

    public void decreaseRow() {
        this.pointerCoordinates.decreaseRow();
    }

    public void increaseColumn() {
        this.pointerCoordinates.increaseColumn();
    }

    public void decreaseColumn() {
        this.pointerCoordinates.decreaseColumn();
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
     * Sets the number of remaining bombs to the given one.
     *
     * @param remainingBombs the number of remaining bombs to be set.
     */
    public void setRemainingBombs(int remainingBombs) {
        this.remainingBombs = remainingBombs;
    }

    public void decreaseRemainingBombs() {
        if (remainingBombs <= 0) return;
        remainingBombs--;
    }

    public void increaseRemainingBombs() {
        if (remainingBombs >= this.getBoard().getNumberOfBombs()) return;
        remainingBombs++;
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
        if (remainingTiles <= 0) return;
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
     * Sets the winning state to the given one.
     *
     * @param won the win state.
     */
    public void setWon(boolean won) {
        this.won = won;
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
     * Sets the loosing state to the given one.
     *
     * @param lost the loose state.
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }
}
