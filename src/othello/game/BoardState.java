/**
 * Project for IJA course
 *
 * @author Tomáš Vlk
 * @author Tomáš Ščavnický
 */

package othello.game;

import java.io.Serializable;


/**
 * Represents state of board - where are and where aren't stones
 */
public class BoardState implements Cloneable, Serializable {

    public static final int STONE_BLACK = -1;

    public static final int STONE_WHITE = 1;

    public static final int STONE_NONE = 0;

    public static final int STONE_POTENTIAL = 42;

    public int size;

    public int[][] state;

    public Player player;


    /**
     * Get board state player
     *
     * @return board state player
     */
    public Player getPlayer() {
        return player;
    }


    /**
     * Set board state player
     *
     * @param player board state player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }


    /**
     * Board state constructor
     *
     * @param size size of board
     */
    public BoardState(int size) {
        this.size = size;
        this.state = new int[size][size];

        // initialize board state - there are no stones on board
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.state[i][j] = STONE_NONE;
            }
        }
    }


    /**
     * Get potential stones depending on where are and which stones are on board
     *
     * @return matrix of potential stones with same size as board
     */
    public int[][] getPotentialStones() {
        return this.getPotentialStones(this.player);
    }


    /**
     * Get potential stones depending on where are and which stones are on board
     *
     * @param player potential stones of which player
     * @return matrix of potential stones with same size as board
     */
    public int[][] getPotentialStones(Player player) {
        int[][] potencialStones = new int[this.size][this.size];
        int currentPlayerColor = player.getColor();
        BoardState foo;
        foo = this.clone();
        potencialStones = foo.state;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (potencialStones[i][j] == STONE_POTENTIAL) {
                    potencialStones[i][j] = STONE_NONE;
                }
            }
        }

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (potencialStones[i][j] == currentPlayerColor) {
                    for (int direction = 0; direction < 8; direction++) {
                        boolean positionIsValid = true;
                        boolean positionIsSet = false;
                        int x = i;
                        int y = j;
//                        try {
                            switch (direction) {
                                case 0:
                                    if (x + 1 < this.size) {
                                        while (this.state[x + 1][y] == -this.player.getColor()) {
                                            if (x + 2 < this.size) {
                                                positionIsSet = true;
                                                x++;
                                            } else {
                                                positionIsValid = false;
                                                break;
                                            }
                                        }
                                        ++x;
                                    }

                                    break;
                                case 1:
                                    if (x + 1 < this.size && y + 1 < this.size) {
                                        while (this.state[x + 1][y + 1] == -this.player.getColor()) {
                                            if (x + 2 < this.size && y + 2 < this.size) {
                                                positionIsSet = true;
                                                x++;
                                                y++;
                                            } else {
                                                positionIsValid = false;
                                                break;
                                            }
                                        }
                                        ++x;
                                        ++y;
                                    }
                                    break;
                                case 2:
                                    if (y + 1 < this.size) {
                                        while (this.state[x][y + 1] == -this.player.getColor()) {
                                            if (y + 2 < this.size) {
                                                positionIsSet = true;
                                                y++;
                                            } else {
                                                positionIsValid = false;
                                                break;
                                            }
                                        }
                                        ++y;
                                    }
                                    break;
                                case 3:
                                    if (x - 1 >= 0 && y + 1 < this.size) {
                                        while (this.state[x - 1][y + 1] == -this.player.getColor()) {
                                            if (x - 2 >= 0 && y + 2 < this.size) {
                                                positionIsSet = true;
                                                x--;
                                                y++;
                                            } else {
                                                positionIsValid = false;
                                                break;
                                            }
                                        }
                                        --x;
                                        ++y;
                                    }
                                    break;
                                case 4:
                                    if (x - 1 >= 0) {
                                        while (this.state[x - 1][y] == -this.player.getColor()) {
                                            if (x - 2 >= 0) {
                                                positionIsSet = true;
                                                x--;
                                            } else {
                                                positionIsValid = false;
                                                break;
                                            }
                                        }
                                        --x;
                                    }
                                    break;
                                case 5:
                                    if (x - 1 >= 0 && y - 1 >= 0) {
                                        while (this.state[x - 1][y - 1] == -this.player.getColor()) {
                                            if (x - 2 >= 0 && y - 2 >= 0) {
                                                positionIsSet = true;
                                                x--;
                                                y--;
                                            } else {
                                                positionIsValid = false;
                                                break;
                                            }
                                        }
                                        --x;
                                        --y;
                                    }
                                    break;
                                case 6:
                                    if (y - 1 >= 0) {
                                        while (this.state[x][y - 1] == -this.player.getColor()) {
                                            if (y - 2 >= 0) {
                                                positionIsSet = true;
                                                y--;
                                            } else {
                                                positionIsValid = false;
                                                break;
                                            }
                                        }
                                        --y;
                                    }
                                    break;
                                case 7:
                                    if (x + 1 < this.size && y - 1 >= 0) {
                                        while (this.state[x + 1][y - 1] == -this.player.getColor()) {
                                            if (x + 2 < this.size  && y - 2 >= 0) {
                                                positionIsSet = true;
                                                x++;
                                                y--;
                                            } else {
                                                positionIsValid = false;
                                                break;
                                            }
                                        }
                                        ++x;
                                        --y;
                                    }
                                    break;
                                default:
                                    break;
                            }
//                        } catch (ArrayIndexOutOfBoundsException error) {
//                            System.out.println(error);
//                            System.out.println("x: " + x);
//                            System.out.println("y: " + y);
//                            System.out.println("direction: " + direction);
//                            System.out.println("========================================");
//                        }


                        if (potencialStones[x][y] == STONE_NONE) {
                            potencialStones[x][y] = positionIsSet && positionIsValid ? STONE_POTENTIAL : potencialStones[x][y];
                        }
                    }
                }
            }
        }


        return potencialStones;
    }

    @Override
    public BoardState clone() {
        try {
            BoardState clone = (BoardState) super.clone();
            clone.state = new int[this.size][this.size];
            for (int i = 0; i < this.size; i++) {
                System.arraycopy(this.state[i], 0, clone.state[i], 0, this.size);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                string += "\t" + Integer.toString(state[i][j]) + "\t:";
            }
            string += "\n";
        }

        if (this.getPlayer().getColor() == Player.COLOR_BLACK) {
            string += "\nPlayer: black";
        } else if (this.getPlayer().getColor() == Player.COLOR_WHITE) {
            string += "\nPlayer: white";
        }

        return string;
    }
}
