package model;

import common.Coordinates;
import common.Observable;

import static common.Constants.*;

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

    public int getNumberOfRemainingBombs() {
        return game.getRemainingBombs();
    }

    public Coordinates getPointerCoordinates() {
        return game.getPointerCoordinates();
    }

    public String render() {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < game.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < game.getBoard().getNumberOfColumns(); j++) {
                if (i == getPointerCoordinates().getRow() && j == getPointerCoordinates().getColumn()) {
                    out.append(pointerSymbol);
                    continue;
                }

                Tile currentTile = game.getBoard().getTileAt(i, j);
                if (!currentTile.isOpen()) {
                    if (currentTile.isFlagged()) out.append(flagSymbol);
                    else out.append(unopenedSymbol);
                } else {
                    if (currentTile.hasBomb()) out.append(bombSymbol);
                    else {
                        int adjacentBombs = currentTile.getAdjacentBombs();
                        if (adjacentBombs != 0) out.append(adjacentBombs).append(" ");
                        else out.append(square);
                    }
                }
            }
            out.append("\n");
        }

        return out.toString();
    }

    public String renderBombs() {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < game.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < game.getBoard().getNumberOfColumns(); j++) {
                Tile tile = game.getBoard().getTileAt(i, j);
                if (tile.hasBomb()) {
                    out.append(bombSymbol);
                } else if (!tile.isOpen()) {
                    out.append(unopenedSymbol);
                } else {
                    int adjacentBombs = tile.getAdjacentBombs();
                    if (adjacentBombs != 0) out.append(adjacentBombs).append(" ");
                    else out.append(square);
                }
            }
            out.append("\n");
        }

        return out.toString();
    }
}
