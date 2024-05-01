import controller.GameController;
import model.Game;
import view.TUI;

public class Main {
    public static void main(String[] args) {
        TUI view = new TUI();
        Game model = new Game();
        GameController controller = new GameController(model);

        view.addObserver(controller);
        model.addObserver(view);
        view.run();
    }
}