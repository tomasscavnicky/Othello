/**
 * Created by tom on 16/02/16.
 */
public class Player {

    private enum color;

    private boolean isHuman;

    public int play() {
        if(isHuman) {
            // na tlačítku bude callBack
            // Game.setStone(x, y);
            // Game.continueGame();
        }else {
            // AI

            Game.setStone(x, y);
            Game.continueGame();
        }
    }
}
