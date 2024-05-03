package model;

import common.Observable;
import common.message.GameView;

public class Game extends Observable {
    private Board board;

    private int remainingBombs;
    private int remainingTiles;

    private boolean won;
    private boolean lost;


    public Game() {
        this.board = null;
    }

    public void init(int rows, int columns, int numberOfBombs) {
        this.board = new Board(rows, columns, numberOfBombs);
        this.remainingBombs = numberOfBombs;
        this.remainingTiles = rows * columns;

        notifyObservers(new GameView(this));
    }

    public Board getBoard() {
        return board;
    }

    public int getRemainingBombs() {
        return remainingBombs;
    }

    public void decreaseRemainingBombs() {
        if (remainingBombs <= 0) return;
        remainingBombs--;
    }

    public void increaseRemainingBombs() {
        if (remainingBombs >= this.getBoard().getNumberOfBombs()) return;
        remainingBombs++;
    }

    public int getRemainingTiles() {
        return remainingTiles;
    }

    public void decreaseRemainingTiles() {
        if (remainingTiles <= 0) return;
        remainingTiles--;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }
}
