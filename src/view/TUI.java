package view;

import common.enums.Color;
import common.enums.Command;
import common.message.CommandMessage;
import common.message.GameView;
import common.message.InitGame;
import common.message.Message;
import common.printable.Console;
import model.Tile;

import java.util.Scanner;

import static common.printable.Constants.*;

/**
 * Textual User Interface
 */
public class TUI extends View {
    /**
     * Input scanner
     */
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private GameView gameView;

    @Override
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
            System.out.println("Select the number of rows (" + minRows + "-" + maxRows + "): ");
            try {
                rows = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of rows");
                rows = -1;
            }
        } while (rows < minRows || rows > maxRows);

        int columns;
        do {
            System.out.println("Select the number of columns (" + minColumns + "-" + maxColumns + "): ");
            try {
                columns = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of columns");
                columns = -1;
            }
        } while (columns < minColumns || columns > maxColumns);

        int numberOfBombs, maxNumberOfBombs;
        do {
            maxNumberOfBombs = (int) ((rows * columns) * 0.5f);
            System.out.println("Select the number of bombs (" + 1 + "-" + maxNumberOfBombs + "): ");
            try {
                numberOfBombs = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Please select a valid number of number of bombs");
                numberOfBombs = -1;
            }
        } while (numberOfBombs < 0 || numberOfBombs >= maxNumberOfBombs);

        notifyObservers(new InitGame(rows, columns, numberOfBombs));
    }

    private void renderInGame() {
        while (!gameView.isGameLost() && !gameView.isGameWon()) {
            renderBoard();
            renderCommands();

            Command command = Command.NONE;
            int parsedRow = -1, parsedColumn = -1;

            boolean isValid = false;
            while (!isValid) {
                String input = scanner.next();

                System.out.println(input);

                String[] commandTokens = input.split("\\s+");

                if (commandTokens.length < 3) {
                    System.out.println("Please enter a valid command");
                    continue;
                }

                command = Command.fromKey(commandTokens[0]);

                try {
                    parsedRow = Integer.parseInt(commandTokens[1]);
                    if (parsedRow < 0 || parsedRow >= gameView.getNumberOfRows()) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please insert a valid row number between 0 and " + gameView.getNumberOfRows());
                    continue;
                }

                try {
                    parsedColumn = Integer.parseInt(commandTokens[2]);
                    if (parsedColumn < 0 || parsedColumn >= gameView.getNumberOfRows()) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please insert a valid column number between 0 and " + gameView.getNumberOfColumns());
                    continue;
                }

                isValid = true;
            }

            notifyObservers(new CommandMessage(command, parsedRow, parsedColumn));
        }
    }

    private void renderPostGame() {
        renderBombs();

        if (gameView.isGameLost()) System.out.println(Console.coloredString("YOU LOST! :(", Color.RED));
        else System.out.println(Console.coloredString("YOU WON!", Color.GREEN));

        System.out.println("q to quit");

        String input;
        do input = scanner.next(); while (!input.equals("q"));

        System.exit(0);
    }

    private void renderTitle() {
        System.out.println(title);
    }

    private void renderCommands() {
        for (Command command : Command.values()) {
            System.out.println(command.name() + " > " + command.getKey());
        }

        System.out.println("Enter a command: ");
    }

    public void renderBoard() {
        StringBuilder out = new StringBuilder();

        out.append("   ");
        for (int i = 0; i < gameView.getNumberOfColumns(); i++) {
            out.append(" ").append(i);
            if (i < 10) out.append(" ");
        }

        out.append("\n");

        for (int i = 0; i < gameView.getNumberOfRows(); i++) {
            if (i < 10) out.append(" ");
            out.append(i).append(" ");

            for (int j = 0; j < gameView.getNumberOfColumns(); j++) {
                Tile currentTile = gameView.getTileAt(i, j);
                if (!currentTile.isOpen()) {
                    if (currentTile.isFlagged()) out.append(flagSymbol);
                    else out.append(unopenedSymbol);
                } else {
                    if (currentTile.hasBomb()) out.append(bombSymbol);
                    else {
                        int adjacentBombs = currentTile.getAdjacentBombs();
                        if (adjacentBombs != 0) out.append(" ").append(adjacentBombs).append(" ");
                        else out.append(square);
                    }
                }
            }

            out.append(" ").append(i).append("\n");
        }

        out.append("   ");
        for (int i = 0; i < gameView.getNumberOfColumns(); i++) {
            out.append(" ").append(i);
            if (i < 10) out.append(" ");
        }

        out.append("\n");

        System.out.flush();
        System.out.print(out);
    }

    public void renderBombs() {
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < gameView.getNumberOfRows(); i++) {
            for (int j = 0; j < gameView.getNumberOfColumns(); j++) {
                Tile tile = gameView.getTileAt(i, j);
                if (tile.hasBomb()) {
                    out.append(bombSymbol);
                } else {
                    int adjacentBombs = tile.getAdjacentBombs();
                    if (adjacentBombs != 0) out.append(" ").append(adjacentBombs).append(" ");
                    else out.append(square);
                }
            }
            out.append("\n");
        }

        System.out.flush();
        System.out.println(out);
    }

    @Override
    public void update(Message message) {
        if (message instanceof GameView gv) {
            this.gameView = gv;
        }
    }
}
