package view;

import common.enums.Command;
import common.message.CommandMessage;
import common.message.GameView;
import common.message.InitGame;
import common.message.Message;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

import static common.printable.Constants.*;

public class GUI extends View {
    private final JFrame frame = new JFrame("Minesweeper");
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final Border border = BorderFactory.createEmptyBorder(60, 60, 60, 60);

    private GameView gameView;

    public GUI() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);

        JPanel preGamePanel = getPreGamePanel();
        cards.add(preGamePanel, "preGamePanel");

        JPanel inGamePanel = getInGamePanel();
        cards.add(inGamePanel, "inGamePanel");

        contentPane.add(cards);
        cardLayout.show(cards, "preGamePanel");
    }

    private JPanel getPreGamePanel() {
        AtomicInteger
                numberOfRows = new AtomicInteger(),
                numberOfColumns = new AtomicInteger(),
                numberOfBombs = new AtomicInteger();

        JPanel preGamePanel = new JPanel();
        preGamePanel.setLayout(new BoxLayout(preGamePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Minesweeper");
        title.setFont(new Font("JetBrains Mono ExtraBold", Font.BOLD, 64));
        preGamePanel.add(title);

        JLabel rowsLabel = new JLabel(String.valueOf(minRows));
        JSlider rowSlider = new JSlider(minRows, maxRows, minRows);

        JLabel columnsLabel = new JLabel(String.valueOf(minRows));
        JSlider columnsSlider = new JSlider(minColumns, maxColumns, minColumns);


        JLabel bombsLabel = new JLabel(String.valueOf(minRows));
        JSlider bombsSlider = new JSlider(
                1, (int) (rowSlider.getValue() * columnsSlider.getValue() * 0.5f), 1
        );

        preGamePanel.add(rowsLabel);
        preGamePanel.add(rowSlider);

        preGamePanel.add(columnsLabel);
        preGamePanel.add(columnsSlider);

        preGamePanel.add(bombsLabel);
        preGamePanel.add(bombsSlider);

        rowSlider.addChangeListener(e -> {
            numberOfRows.set(rowSlider.getValue());
            rowsLabel.setText(String.valueOf(rowSlider.getValue()));
        });

        columnsSlider.addChangeListener(e -> {
            numberOfColumns.set(columnsSlider.getValue());
            columnsLabel.setText(String.valueOf(columnsSlider.getValue()));
        });

        bombsSlider.addChangeListener(e -> {
            numberOfBombs.set(bombsSlider.getValue());
            bombsLabel.setText(String.valueOf(bombsSlider.getValue()));
        });

        JButton button = new JButton("Start Game");
        button.setFont(new Font("JetBrains Mono ExtraBold", Font.BOLD, 16));
        preGamePanel.add(button);


        button.addActionListener(e -> {
            notifyObservers(new InitGame(numberOfRows.get(), numberOfColumns.get(), numberOfBombs.get()));
            cardLayout.next(cards);
        });

        return preGamePanel;
    }

    private JPanel getInGamePanel() {
        JPanel inGamePanel = new JPanel();
        inGamePanel.setLayout(new GridLayout(gameView.getNumberOfRows(), gameView.getNumberOfColumns()));

        for (int i = 0; i < gameView.getNumberOfRows(); i++) {
            for (int j = 0; j < gameView.getNumberOfColumns(); j++) {
                String buttonLabel = "";
                if (!gameView.getTileAt(i, j).isOpen()) {
                    buttonLabel = "(" + i + ", " + j + ")";
                }

                Button button = new Button(buttonLabel);
                inGamePanel.add(button);

                int finalI = i;
                int finalJ = j;

                button.addActionListener(e -> {
                    notifyObservers(new CommandMessage(Command.OPEN, finalI, finalJ));
                });
            }
        }

        return inGamePanel;
    }

    @Override
    public void update(Message message) {
        if (message instanceof GameView gv) {
            this.gameView = gv;
        }
    }

    @Override
    public void run() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
