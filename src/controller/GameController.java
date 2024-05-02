package controller;

import common.Coordinates;
import common.Observer;
import common.enums.Command;
import common.message.CommandMessage;
import common.message.GameView;
import common.message.InitGame;
import common.message.Message;
import model.Game;
import model.Tile;

/**
 * A class representing the game controller.
 * <p>
 * This class contains all the business logic of the game.
 */
public class GameController implements Observer {
    private final Game game;
    private int selectedRow;
    private int selectedColumn;

    public GameController(Game game) {
        this.game = game;
    }

    public void initGame(int rows, int columns, int numberOfBombs) {
        game.init(rows, columns, numberOfBombs);
    }

    private void handle(CommandMessage commandMessage) {
        Command command = commandMessage.getCommand();

        selectedRow = commandMessage.getSelectedRow();
        selectedColumn = commandMessage.getSelectColumn();

        switch (command) {
            case OPEN -> {
                game.setLost(this.checkIfLost());
                game.setWon(this.checkIfWon());
                this.openTileAt(selectedRow, selectedColumn);
            }
            case FLAG -> toggleFlagAt(selectedRow, selectedColumn);
            case NONE -> System.out.println("Invalid operation! Please enter a valid command");
        }

    }

    private void openTileAt(int row, int column) {
        // gets the tile at the given row and column.
        Tile tile = game.getBoard().getTileAt(row, column);

        // if its already open, nothing to do.
        if (tile.isOpen()) return;

        // opens the given tile.
        tile.setOpen();

        // decreases the number of remaining tiles.
        game.decreaseRemainingTiles();

        // opens all the tiles with 0 adjacent bombs
        if (tile.getAdjacentBombs() != 0) return;
        for (int i = row - 1; i <= row + 1; i++) {
            if (i < 0 || i >= game.getBoard().getNumberOfRows()) continue;
            for (int j = column - 1; j <= column + 1; j++) {
                if (j < 0 || j >= game.getBoard().getNumberOfColumns()) continue;
                openTileAt(i, j);
            }
        }
    }

    public void openTileAt(Coordinates coordinates) {
        openTileAt(coordinates.getRow(), coordinates.getColumn());
    }

    /**
     * Toggles the flag of the tile at the given row and column.
     *
     * @param row    the row of the tile.
     * @param column the column of the tile.
     */
    private void toggleFlagAt(int row, int column) {
        Tile currentTile = game.getBoard().getTileAt(row, column);
        currentTile.toggleFlag();

        if (currentTile.isFlagged()) game.decreaseRemainingBombs();
        else game.increaseRemainingBombs();
    }

    /**
     * Checks whether the player won the game.
     *
     * @return true if the player won the game.
     */
    private boolean checkIfWon() {
        return this.game.getRemainingTiles() == game.getBoard().getNumberOfBombs();
    }

    /**
     * Checks whether the player lost the game.
     *
     * @return true if the player lost.
     */
    private boolean checkIfLost() {
        return game.getBoard().getTileAt(selectedRow, selectedColumn).hasBomb();
    }

    @Override
    public void update(Message message) {
        if (message instanceof InitGame m) {
            initGame(m.getRows(), m.getColumns(), m.getNumberOfBombs());
        } else if (message instanceof CommandMessage) {
            handle((CommandMessage) message);
        }

        game.notifyObservers(new GameView(this.game));
    }
}
