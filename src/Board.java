import java.util.Stack;

/**
 * Created by tom on 16/02/16.
 */
public class Board {

    private int size;

    private Stack<BoardState> history;

    public Board (int size) {
        this.size = size;
    }

    public BoardState getCurrentState() {
        return this.history.firstElement();
    }

    public boolean undoState() {
        return true;
    }

    public boolean isFull() {
        return true;
    }

    public boolean setNewState() {
        return true;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
