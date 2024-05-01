package view;

import common.Console;
import common.Observable;
import common.Observer;
import common.enums.Color;
import common.enums.Direction;
import common.enums.Operation;
import common.message.*;
import model.GameView;

import java.io.IOException;

import static common.Constants.*;

public class TUI extends Observable implements Observer {
    private GameView gameView;

    public void run() {
        renderPreGame();
        renderInGame();
        renderPostGame();
    }

    private void renderPreGame() {
        Console.clear();
        renderTitle();

        int rows;
        do {
            System.out.println("SELECT THE NUMBER OF ROWS");
            System.out.println("(" + minRows + "-" + maxRows + ")");
            try {
                rows = Integer.parseInt(String.valueOf(System.in.read()));
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of rows");
                rows = -1;
            } catch (IOException e) {
                rows = -1;
                System.exit(0);
            }
        } while (rows < minRows || rows > maxRows);

        int columns;
        do {
            System.out.println("SELECT THE NUMBER OF COLUMNS");
            System.out.println("(" + minColumns + "-" + maxColumns + ")");
            try {
                columns = Integer.parseInt(String.valueOf(System.in.read()));
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of columns");
                columns = -1;
            } catch (IOException e) {
                columns = -1;
                System.exit(0);
            }
        } while (columns < minColumns || columns > maxColumns);

        int numberOfBombs;
        do {
            System.out.println("SELECT THE NUMBER OF BOMBS");
            System.out.println("(" + 1 + "-" + rows * columns + ")");
            try {
                numberOfBombs = Integer.parseInt(String.valueOf(System.in.read()));
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of number of bombs");
                numberOfBombs = -1;
            } catch (IOException e) {
                numberOfBombs = -1;
                System.exit(0);
            }
        } while (numberOfBombs < 0 || numberOfBombs > rows * columns);

        notifyObservers(new InitGameMessage(rows, columns, numberOfBombs));
    }

    private void renderInGame() {
        renderGame();
        renderRemainingBombs();
        renderCommands();

        while (!gameView.isGameLost() && !gameView.isGameWon()) {
            try {
                String input = new String(System.in.readAllBytes());
                if (directionKeys.contains(input)) {
                    notifyObservers(new MoveMessage(Direction.fromKey(input)));
                } else {
                    notifyObservers(new OperationMessage(
                            Operation.fromKey(input), gameView.getCurrentPointerCoordinates()
                    ));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void renderPostGame() {
        if (gameView.isGameLost()) System.out.println(Console.coloredString("YOU WON! :)", Color.GREEN));
        else System.out.println(Console.coloredString("YOU LOST! :(", Color.RED));

        System.out.println("q to quit");

        char input;
        do {
            try {
                input = (char) System.in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (input != 'q');

        System.exit(0);
    }


    private void renderTitle() {
        System.out.println("""
                   \
                     __  __ ___ _  _ ___ _____      _____ ___ ___ ___ ___\s
                    |  \\/  |_ _| \\| | __/ __\\ \\    / / __| __| _ \\ __| _ \\
                    | |\\/| || || .` | _|\\__ \\\\ \\/\\/ /| _|| _||  _/ _||   /
                    |_|  |_|___|_|\\_|___|___/ \\_/\\_/ |___|___|_| |___|_|_\\
                """);
    }

    private void renderRemainingBombs() {
        System.out.println("Remaining bombs: " + gameView.getNumberOfRemainingBombs() + "\n");
    }

    private void renderGame() {
        Console.clear();
        System.out.println(gameView.render());
    }

    private void renderCommands() {
        for (Direction direction : Direction.values()) {
            System.out.println(direction.name() + " >" + direction.getKey());
        }

        System.out.println("-".repeat(10));

        for (Operation operation : Operation.values()) {
            System.out.println(operation.name() + " > " + operation.getKey());
        }
    }

    @Override
    public void update(Message message) {
        if (message instanceof GameViewMessage) {
            this.gameView = ((GameViewMessage) message).getGameView();
        }
    }

    private enum State {
        PRE_GAME, IN_GAME, POST_GAME
    }
}
