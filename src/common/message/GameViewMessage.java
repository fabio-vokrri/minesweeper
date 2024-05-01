package common.message;

import model.GameView;

public class GameViewMessage implements Message {
    private final GameView gameView;

    public GameViewMessage(GameView gameView) {
        this.gameView = gameView;
    }

    public GameView getGameView() {
        return gameView;
    }
}
