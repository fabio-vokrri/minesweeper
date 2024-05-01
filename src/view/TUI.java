package view;

import common.Console;
import common.Observable;
import common.Observer;
import common.enums.Color;
import common.enums.Direction;
import common.enums.Operation;
import common.message.*;
import model.GameView;

import java.util.Scanner;

import static common.Constants.*;

public class TUI extends Observable implements Observer {
    private final Scanner scanner = new Scanner(System.in);
    private GameView gameView;

    public void run() {
        renderPreGame();
        renderInGame();
        renderPostGame();
    }

    private void renderPreGame() {
        System.out.flush();
        renderTitle();

        int rows;
        do {
            System.out.println("SELECT THE NUMBER OF ROWS");
            System.out.println("(" + minRows + "-" + maxRows + ")");
            try {
                rows = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of rows");
                rows = -1;
            }
        } while (rows < minRows || rows > maxRows);

        int columns;
        do {
            System.out.println("SELECT THE NUMBER OF COLUMNS");
            System.out.println("(" + minColumns + "-" + maxColumns + ")");
            try {
                columns = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of columns");
                columns = -1;
            }
        } while (columns < minColumns || columns > maxColumns);

        int numberOfBombs;
        do {
            System.out.println("SELECT THE NUMBER OF BOMBS");
            System.out.println("(" + 1 + "-" + rows * columns + ")");
            try {
                numberOfBombs = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of number of bombs");
                numberOfBombs = -1;
            }
        } while (numberOfBombs < 0 || numberOfBombs > rows * columns);

        notifyObservers(new InitGameMessage(rows, columns, numberOfBombs));
    }

    private void renderInGame() {
        while (!gameView.isGameLost() && !gameView.isGameWon()) {
            renderGame();
            renderRemainingBombs();
            renderCommands();
            String input = scanner.next().toUpperCase();

            if (directionKeys.contains(input)) {
                notifyObservers(new MoveMessage(Direction.fromKey(input)));
                continue;
            }

            if (operationKeys.contains(input)) {
                notifyObservers(
                        new OperationMessage(
                                Operation.fromKey(input),
                                gameView.getPointerCoordinates()
                        )
                );
            }
        }
    }

    private void renderStatus() {
        System.out.println("Lost? " + gameView.isGameLost());
        System.out.println("Pointer row:\t\t" + gameView.getPointerCoordinates().getRow());
        System.out.println("Pointer column:\t\t" + gameView.getPointerCoordinates().getColumn());
    }

    private void renderPostGame() {
        System.out.flush();
        System.out.println(gameView.renderBombs());

        if (gameView.isGameLost()) System.out.println(Console.coloredString("YOU WON! :)", Color.GREEN));
        else System.out.println(Console.coloredString("YOU LOST! :(", Color.RED));

        System.out.println("q to quit");

        String input;
        do input = scanner.next(); while (!input.equals("q"));

        System.exit(0);
    }


    private void renderTitle() {
        System.out.println(
                """
                                 _____                                                                         _____\s
                                ( ___ )                                                                       ( ___ )
                                 |   |~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|   |\s
                                 |   |   __  __ ___ _   _ _____ ______        _______ _____ ____  _____ ____   |   |\s
                                 |   |  |  \\/  |_ _| \\ | | ____/ ___\\ \\      / / ____| ____|  _ \\| ____|  _ \\  |   |\s
                                 |   |  | |\\/| || ||  \\| |  _| \\___ \\\\ \\ /\\ / /|  _| |  _| | |_) |  _| | |_) | |   |\s
                                 |   |  | |  | || || |\\  | |___ ___) |\\ V  V / | |___| |___|  __/| |___|  _ <  |   |\s
                                 |   |  |_|  |_|___|_| \\_|_____|____/  \\_/\\_/  |_____|_____|_|   |_____|_| \\_\\ |   |\s
                                 |   |                                                                         |   |\s
                                 |___|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|___|\s
                                (_____)                                                                       (_____)\s
                        """
        );
    }

    private void renderRemainingBombs() {
        System.out.println("Remaining bombs: " + gameView.getNumberOfRemainingBombs() + "\n");
    }

    private void renderGame() {
        System.out.flush();
        System.out.println(gameView.render());
    }

    private void renderCommands() {
        for (Direction direction : Direction.values()) {
            System.out.println(direction.name() + " > " + direction.getKey());
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
