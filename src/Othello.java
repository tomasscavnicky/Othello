
public class Othello {

    public static void main(String[] args) {
        new Launcher();
    }

    public static void newGame() {

        // TODO replace by data from launcher
        Player playerBlack = new Player(Player.COLOR_BLACK, true);
        Player playerWhite = new Player(Player.COLOR_WHITE, true);

        Game game = new Game(playerBlack, playerWhite, 8);
        game.startGame();
    }
}
