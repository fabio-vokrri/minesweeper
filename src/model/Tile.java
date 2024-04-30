package model;

public class Tile {
    private final int x;
    private final int y;

    private int adjacentBombs;
    private boolean hasBomb;
    private boolean isOpen;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hasBomb() {
        return hasBomb;
    }

    public void setBomb() {
        this.hasBomb = true;
    }

    public int getAdjacentBombs() {
        return this.adjacentBombs;
    }

    public void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    public void setOpen() {
        this.isOpen = true;
    }

    public boolean isOpen() {
        return this.isOpen;
    }
}
