package controller;

import common.Coordinates;
import common.Observer;
import common.enums.Direction;
import common.enums.Operation;
import common.message.*;
import model.Game;
import model.GameView;
import model.Tile;

/**
 * A class representing the game controller.
 * <p>
 * This class contains all the business logic of the game.
 */
public class GameController implements Observer {
    private final Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void initGame(int rows, int columns, int numberOfBombs) {
        game.init(rows, columns, numberOfBombs);
    }

    private void handle(OperationMessage operationMessage) {
        Operation operation = operationMessage.getOperation();
        Coordinates coordinates = operationMessage.getCoordinates();

        switch (operation) {
            case OPEN -> {
                game.setLost(this.checkIfLost());
                game.setWon(this.checkIfWon());
                this.openTileAt(coordinates);
            }
            case FLAG -> toggleFlagAt(coordinates);
        }
    }

    private void handle(MoveMessage moveMessage) {
        Direction direction = moveMessage.getDirection();

        switch (direction) {
            case UP -> {
                if (game.getPointerCoordinates().getRow() == 0) return;
                game.decreasePointerRow();
            }
            case DOWN -> {
                if (game.getPointerCoordinates().getRow() >= game.getBoard().getNumberOfRows()) return;
                game.increasePointerRow();
            }
            case LEFT -> {
                if (game.getPointerCoordinates().getColumn() == 0) return;
                game.decreasePointerColumn();
            }
            case RIGHT -> {
                if (game.getPointerCoordinates().getColumn() >= game.getBoard().getNumberOfColumns()) return;
                game.increasePointerColumn();
            }
        }

        System.out.println(game.getBoard().getTileAt(game.getPointerCoordinates()));
    }

    /**
     * Opens the tile at the given coordinates.
     *
     * @param row    the row of the tile to open.
     * @param column the column of the tile to open.
     */
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

    private void toggleFlagAt(Coordinates coordinates) {
        toggleFlagAt(coordinates.getRow(), coordinates.getColumn());
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
        return game.getBoard().getTileAt(game.getPointerCoordinates()).hasBomb();
    }

    @Override
    public void update(Message message) {
        if (message instanceof InitGameMessage m) {
            initGame(m.getRows(), m.getColumns(), m.getNumberOfBombs());
        }

        if (message instanceof OperationMessage) {
            handle((OperationMessage) message);
        }

        if (message instanceof MoveMessage) {
            handle((MoveMessage) message);
        }

        this.game.notifyObservers(new GameViewMessage(new GameView(this.game)));
    }
}
