import controller.GameController;
import model.Game;
import view.GUI;
import view.View;

public class Main {
    public static void main(String[] args) {
        View view = new GUI();
        Game model = new Game();
        GameController controller = new GameController(model);

        view.addObserver(controller);
        model.addObserver(view);

        view.run();
    }
}