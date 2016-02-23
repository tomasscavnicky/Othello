import java.util.ArrayList;

public class BoardState implements Cloneable {

    public static final int STONE_BLACK = -1;

    public static final int STONE_WHITE = 1;

    public static final int STONE_NONE = 0;

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

        int currentPLayerColor = this.player.getColor();


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.state[i][j] = STONE_NONE;
            }
        }
    }

    public int[][] getPotencialStones() {

//        List<int> potentialStones = new ArrayList<int>();
        int potentialStones[][] = new int[this.size][this.size];
        int currentPlayerColor = this.player.getColor();

        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                if(this.state[x][y] == currentPlayerColor) {
                    for(int i = 0; i < 8; i++) {
                        switch (i) {
                            case 0:
                                if (this.state[x + 1][y] == -currentPlayerColor) {
                                    int pc[][] = new int[1][1];
                                    pc = evaluatePosition(x, y);
                                    if (pc[0] != -1) {

                                    }
                                }
                                evaluatePosition();
                                break;
                            case 1:
                                if (this.state[x + 1][y + 1] == -currentPlayerColor) {

                                }
                                break;
                            case 2:
                                if (this.state[x][y + 1] == -currentPlayerColor) {

                                }
                                break;
                            case 3:
                                if (this.state[x - 1][y + 1] == -currentPlayerColor) {

                                }
                                break;
                            case 4:
                                if (this.state[x - 1][y] == -currentPlayerColor) {

                                }
                                break;
                            case 5:
                                if (this.state[x - 1][y - 1] == -currentPlayerColor) {

                                }
                                break;
                            case 6:
                                if (this.state[x][y - 1] == -currentPlayerColor) {

                                }
                                break;
                            case 7:
                                if (this.state[x + 1][y - 1] == -currentPlayerColor) {

                                }
                                break;
                        }
                    }
                }
            }
        }

        if (this.state[2][3] != STONE_BLACK) {
            potentialStones[2][3] = STONE_POTENCIAL;
        }
        if (this.state[3][2] != STONE_BLACK) {
            potentialStones[3][2] = STONE_POTENCIAL;
        }
        if (this.state[5][4] != STONE_BLACK) {
            potentialStones[5][4] = STONE_POTENCIAL;
        }
        if (this.state[4][5] != STONE_BLACK) {
            potentialStones[4][5] = STONE_POTENCIAL;
        }

        return potentialStones;
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
