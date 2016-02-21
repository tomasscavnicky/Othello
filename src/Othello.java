
public class Othello {

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
