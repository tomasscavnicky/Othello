import java.io.Serializable;

public class BoardState implements Cloneable, Serializable {

    public static final int STONE_BLACK = 0;

    public static final int STONE_WHITE = 1;

    public static final int STONE_NONE = -1;

    public static final int STONE_POTENCIAL = 42;

    public int size;

    public int[][] state;

    public Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public BoardState(int size) {
        this.size = size;
        this.state = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.state[i][j] = STONE_NONE;
            }
        }
    }

    public int[][] getPotencialStones() {
        int[][] potencialStones = new int[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                potencialStones[i][j] = STONE_NONE;
            }
        }

        if (this.state[2][3] != STONE_BLACK) {
            potencialStones[2][3] = STONE_POTENCIAL;
        }
        if (this.state[3][2] != STONE_BLACK) {
            potencialStones[3][2] = STONE_POTENCIAL;
        }
        if (this.state[5][4] != STONE_BLACK) {
            potencialStones[5][4] = STONE_POTENCIAL;
        }
        if (this.state[4][5] != STONE_BLACK) {
            potencialStones[4][5] = STONE_POTENCIAL;
        }

        return potencialStones;
    }

    @Override
    public BoardState clone() {
        try {
            BoardState clone = (BoardState) super.clone();
            clone.state = new int[this.size][this.size];
            for(int i = 0; i < this.size; i++)
            {
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
                string += Integer.toString(state[i][j]) + ":";
            }
            string += "\n";
        }

        return string;
    }
}
