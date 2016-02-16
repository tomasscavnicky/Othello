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

        Board board = new Board(8);
        game.setBoard(board);

        game.startGame();
    }
}
