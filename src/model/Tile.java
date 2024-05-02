package model;

/**
 * Model class of a Tile.
 */
public class Tile {
    private final int row;
    private final int column;

    private int adjacentBombs;
    private boolean hasBomb;
    private boolean isOpen;
    private boolean isFlagged;

    /**
     * Creates a new tile with the given row and column coordinates.
     *
     * @param row    the tile's row coordinate.
     * @param column the tile's column coordinate.
     */
    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Gets whether this tile has a bomb.
     *
     * @return true if this tile has a bomb.
     */
    public boolean hasBomb() {
        return hasBomb;
    }

    /**
     * Sets this tile to have a bomb.
     */
    public void setBomb() {
        this.hasBomb = true;
    }

    /**
     * Gets the number of bombs adjacent to this tile.
     *
     * @return the number of adjacent bombs.
     */
    public int getAdjacentBombs() {
        return this.adjacentBombs;
    }

    /**
     * Sets the number to adjacent bombs to the given one.
     *
     * @param adjacentBombs the number of adjacent bombs to be set.
     */
    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    /**
     * Sets this tile to be opened.
     */
    public void setOpen() {
        this.isOpen = true;
    }

    /**
     * Gets whether this tile is open.
     *
     * @return true if this tile is open.
     */
    public boolean isOpen() {
        return this.isOpen;
    }

    /**
     * Gets whether this tile is flagged.
     *
     * @return true if this tile is flagged.
     */
    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * Toggles this tile to be flagged or not.
     * If this tile is already opened, the state of the tile does not change.
     */
    public void toggleFlag() {
        if (this.isOpen) return;
        isFlagged = !isFlagged;
    }

    @Override
    public String toString() {
        return "{ " + row + ", " + column + " }\n" + "bomb? " + hasBomb;
    }
}
