import java.util.Stack;

public class Board {

    private int size;

    private Stack<BoardState> history = new Stack<BoardState>();

    public Board(int size) {
        this.size = size;
        BoardState startState = new BoardState(size);
        startState.player = new Player(Player.COLOR_BLACK);
        startState.state[size / 2 - 1][size / 2 - 1] = BoardState.STONE_WHITE;
        startState.state[size / 2 - 1][size / 2] = BoardState.STONE_BLACK;
        startState.state[size / 2][size / 2 - 1] = BoardState.STONE_BLACK;
        startState.state[size / 2][size / 2] = BoardState.STONE_WHITE;
        this.setNewState(startState);
    }

    public BoardState getCurrentState() {
        return this.history.firstElement();
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
