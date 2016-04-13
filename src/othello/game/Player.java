package othello.game;

import java.io.Serializable;

public class Player implements Serializable {

    public static final int COLOR_WHITE = 1;
    public static final int COLOR_BLACK = -1;

    private int color;

    private boolean isHuman;

    private int algorithm;

    public Player(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }

    public int getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    public void play(Game game) {
        if (!this.isHuman()) {

            // TODO bug
            /*
            try {
                Thread.sleep(1000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            */

            switch (this.getAlgorithm()) {
                case 1 :
                    int[][] potentialStones = game.getBoard().getCurrentState().getPotencialStones();
                    int boardSize = game.getBoard().getSize();
                    for (int i = 0; i < boardSize; i++) {
                        for (int j = 0; j < boardSize; j++) {
                            if (potentialStones[i][j] == BoardState.STONE_POTENCIAL) {

                                game.setStone(i, j);
                                game.continueGame();

                                return;
                            }
                        }
                    }
                    break;
                case 2 :
                    break;
            }
        }
    }
}
