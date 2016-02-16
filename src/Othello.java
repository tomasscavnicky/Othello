import javax.swing.*;
import java.awt.*;

/**
 * Created by tom on 15/02/16.
 */

public class Othello{

    public static void main(String[] args) {
        new Launcher();
    }

    public static void newGame() {
        Game game = new Game();
        game.setBoardSize(new Dimension(8,8));
        game.setRival(0);
        game.setStoneFreeze(false);

        game.render();
        game.startGame();
    }
}
