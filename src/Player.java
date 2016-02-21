
public class Player {

    public static final int COLOR_WHITE = 1;
    public static final int COLOR_BLACK = 0;

    private int color;

    private boolean isHuman;

    public Player(int color) {
        this.color = color;
    }

    public void play(Game game) {
        if (isHuman) {
            // na tlačítku bude callBack
            // Game.setStone(x, y);
            // Game.continueGame();
        } else {
            // AI

            int x = 0;
            int y = 0;

            game.setStone(x, y);
            game.continueGame();
        }
    }
}
