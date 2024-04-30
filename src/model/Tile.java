package model;

import common.Coordinates;

/**
 * Model class of a Tile.
 */
public class Tile {
    
    private final int x;
    private final int y;

    private int adjacentBombs;
    private boolean hasBomb;
    private boolean isOpen;

    /**
     * Creates a new tile with the given x and y coordinates.
     *
     * @param x the tile's x coordinate.
     * @param y the tile's y coordinate.
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new tile with the given coordinates.
     *
     * @param coordinates the tile's coordinates
     */
    public Tile(Coordinates coordinates) {
        this.x = coordinates.getX();
        this.y = coordinates.getY();
    }

    /**
     * Gets the x coordinate of this tile.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate of this tile.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return y;
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
}
