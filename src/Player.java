
public class Player {

    public static final int COLOR_WHITE = 1;
    public static final int COLOR_BLACK = -1;

    private int color;

    private boolean isHuman;

    public Player(int color, boolean isHuman) {
        this.color = color;
        this.isHuman = isHuman;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void play(Game game) {
        if (!isHuman) {
            // TODO AI

            int x = 0;
            int y = 0;

            game.setStone(x, y);
            game.continueGame();
        }
    }
}
