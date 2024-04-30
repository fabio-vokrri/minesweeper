package model;

import common.Coordinates;
import common.Observable;

public class GameView extends Observable {
    private final Game game;

    public GameView(Game game) {
        this.game = game;
    }

    public boolean isGameLost() {
        return game.isLost();
    }

    public boolean isGameWon() {
        return game.isWon();
    }

    public int getRows() {
        return game.getBoard().getNumberOfRows();
    }

    public int getColumns() {
        return game.getBoard().getNumberOfColumns();
    }

    public int getNumberOfBombs() {
        return game.getBoard().getNumberOfBombs();
    }

    public int getNumberOfRemainingBombs() {
        return game.getRemainingBombs();
    }

    public Coordinates getCurrentPointerCoordinates() {
        return game.getCurrentPointerCoordinates();
    }

    public String render() {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < game.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < game.getBoard().getNumberOfColumns(); j++) {
                Tile currentTile = game.getBoard().getTileAt(i, j);

                if (i == getCurrentPointerCoordinates().getX() && j == getCurrentPointerCoordinates().getY()) {

                }
            }
        }

        return out.toString();
    }
}
