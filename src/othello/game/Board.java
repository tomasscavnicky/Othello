package othello.game;

import java.io.Serializable;
import java.util.Stack;

public class Board implements Serializable {

    private int size;

    private Stack<BoardState> history;

    public Board(int size) {
        this.size = size;
        this.history = new Stack<BoardState>();
    }

    public BoardState getCurrentState() {
        return this.history.peek();
    }

    public boolean undoState() {
        // check if in history is not only startState
        if (this.history.size() != 1) {
            this.history.pop();
            return true;
        } else {
            return false;
        }
    }

    public boolean isFull() {
        return true;
    }

    public void setNewState(BoardState state) {
        this.history.push(state);
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
