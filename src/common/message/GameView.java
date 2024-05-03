package common.message;

import common.Observable;
import model.Game;
import model.Tile;

public class GameView extends Observable implements Message {
    private final Game game;

    public GameView(Game game) {
        this.game = game;
    }

    public int getNumberOfRows() {
        return game.getBoard().getNumberOfRows();
    }

    public int getNumberOfColumns() {
        return game.getBoard().getNumberOfColumns();
    }

    public Tile getTileAt(int row, int column) {
        return game.getBoard().getTileAt(row, column);
    }

    public boolean isGameLost() {
        return game.isLost();
    }

    public boolean isGameWon() {
        return game.isWon();
    }
}
