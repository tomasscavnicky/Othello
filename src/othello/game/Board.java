/**
 * Project for IJA course
 *
 * @author Tomáš Vlk
 * @author Tomáš Ščavnický
 */

package othello.game;

import java.io.Serializable;
import java.util.Stack;


/**
 * Represents the game board on which can be placed stones.
 */
public class Board implements Serializable {

    private int size;

    private Stack<BoardState> history;


    /**
     * Bord constructor
     *
     * @param size - size of board
     */
    public Board(int size) {
        this.size = size;
        this.history = new Stack<BoardState>();
    }


    /**
     * Get current state of board - where are and where aren't stones
     *
     * @return current state - peek of board state history stack
     */
    public BoardState getCurrentState() {
        return this.history.peek();
    }


    /**
     * Pop current state from board state history stack
     *
     * @return if in stack is first state return false, else return true
     */
    public boolean undoState() {
        // check if in history is not only startState
        if (this.history.size() != 1) {
            this.history.pop();
            return true;
        } else {
            return false;
        }
    }


    /**
     * Push new state to board state history stack
     *
     * @param state new state
     */
    public void setNewState(BoardState state) {
        this.history.push(state);
    }


    /**
     * Get size of board
     *
     * @return size of board
     */
    public int getSize() {
        return this.size;
    }


    /**
     * Set size of board
     *
     * @param size size of board
     */
    public void setSize(int size) {
        this.size = size;
    }


    public boolean isFull() {
        BoardState currentState = this.getCurrentState();
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++) {
                if (currentState.state[i][j] == BoardState.STONE_NONE) {

                    return false;
                }
            }
        }

        return true;
    }
}
