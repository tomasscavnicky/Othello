/**
 * Project for IJA course
 *
 * @author Tomáš Vlk
 * @author Tomáš Ščavnický
 */

package othello.game;

import java.io.Serializable;


/**
 * Represents player of the game. Player can be only white or black.
 */
public class Player implements Serializable {

    public static final int COLOR_WHITE = 1;
    public static final int COLOR_BLACK = -1;

    private int color;

    private boolean isHuman;

    private int algorithm;


    /**
     * Player constructor
     *
     * @param color player's color
     */
    public Player(int color) {
        this.color = color;
    }


    /**
     * Get player's color
     *
     * @return player's color
     */
    public int getColor() {
        return color;
    }


    /**
     * Set player's color
     *
     * @param color player's color
     */
    public void setColor(int color) {
        this.color = color;
    }


    /**
     * Determine if player is computer or human
     *
     * @return true if player is human, else return false
     */
    public boolean isHuman() {
        return isHuman;
    }


    /**
     * Set player as human or as computer
     *
     * @param human true if player is human, false if player is computer
     */
    public void setHuman(boolean human) {
        isHuman = human;
    }


    /**
     * Get player algorithm if player is computer
     *
     * @return algorithm of computer player
     */
    public int getAlgorithm() {
        return algorithm;
    }


    /**
     * Set player algorithm if player is computer
     *
     * @param algorithm algorithm of computer player
     */
    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }


    /**
     * Lets the player play
     *
     * @param game game session which player will play
     */
    public void play(Game game) {

        // if player is computer execute algorithm
        if (!this.isHuman()) {

            switch (this.getAlgorithm()) {
                case 1: {
                    // first potential stone
                    int[][] potentialStones = game.getBoard().getCurrentState().getPotentialStones();
                    int boardSize = game.getBoard().getSize();
                    for (int i = 0; i < boardSize; i++) {
                        for (int j = 0; j < boardSize; j++) {
                            if (potentialStones[i][j] == BoardState.STONE_POTENTIAL) {

                                game.setStone(i, j);
                                game.continueGame();

                                return;
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    // last potential stone
                    int[][] potentialStones = game.getBoard().getCurrentState().getPotentialStones();
                    int boardSize = game.getBoard().getSize();
                    for (int i = boardSize - 1; i >= 0; i--) {
                        for (int j = boardSize - 1; j >= 0; j--) {
                            if (potentialStones[i][j] == BoardState.STONE_POTENTIAL) {

                                game.setStone(i, j);
                                game.continueGame();

                                return;
                            }
                        }
                    }
                    break;
                }
            }
        }

        // if player is human then do nothing(game.setStone() method could be called by GUI)
    }
}
