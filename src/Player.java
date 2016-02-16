/**
 * Created by tom on 16/02/16.
 */
public class Player {

    private enum color{
        WHITE,
        BLACK
    };

    private boolean isHuman;

    public void play(Game game) {
        if(isHuman) {
            // na tlačítku bude callBack
            // Game.setStone(x, y);
            // Game.continueGame();
        }else {
            // AI

            int x = 0;
            int y = 0;

            game.setStone(x, y);
            game.continueGame();
        }
    }
}
