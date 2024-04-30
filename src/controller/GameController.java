package controller;

import common.Observer;
import model.Game;
import model.Tile;

public class GameController implements Observer {
    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    void openTileAt(int row, int column) {
        // gets the tile at the given row and column.
        Tile currentTile = game.getBoard().getTileAt(row, column);

        // if its already open, nothing to do.
        if (currentTile.isOpen()) return;

        // opens the given tile.
        currentTile.setOpen();

        // decreases the number of remaining tiles.
        game.decreaseRemainingTiles();

        // opens all the tiles with 0 adjacent bombs
        if (currentTile.getAdjacentBombs() != 0) return;
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i >= game.getBoard().getNumberOfRows()) continue;
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j >= game.getBoard().getNumberOfColumns()) continue;

                openTileAt(i, j);
            }
        }
    }

    public boolean checkIfWon() {
        return this.game.getRemainingTiles() == game.getBoard().getNumberOfBombs();
    }

    public boolean checkIfLost(int row, int column) {
        return game.getBoard().getTileAt(row, column).hasBomb();
    }

    @Override
    public void update(Object message) {

    }
}
