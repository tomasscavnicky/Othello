import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by tom on 16/02/16.
 */
public class BoardState {

    public int size;

    public int[][] state;

    public Player player;

    public BoardState(int size) {
        this.size = size;
        this.state = new int[size][size];
    }

    public int[][] getPotencialStones() {
        return new int[size][size];
    }
}
