/**
 * Project for IJA course
 *
 * @author Tomáš Vlk
 * @author Tomáš Ščavnický
 */

package othello.game;

import java.io.*;


/**
 * Represents main game session
 */
public class Game implements Serializable {

    private Board board;

    private Player playerBlack;

    private Player playerWhite;

    private Player activePlayer;

    private Player nonActivePlayer;

    private boolean stoneFreeze;

    private GameEventsListener gameEventsListener;


    /**
     * Game session constructor
     *
     * @param playerBlack black player
     * @param playerWhite white player
     * @param size        size of board
     * @param stoneFreeze if stones will be frozen or not
     */
    public Game(Player playerBlack, Player playerWhite, int size, boolean stoneFreeze) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.board = new Board(size);
        this.stoneFreeze = stoneFreeze;
    }


    /**
     * Get game events listener
     *
     * @return game events listener
     */
    public GameEventsListener getGameEventsListener() {
        return gameEventsListener;
    }


    /**
     * Set game events listener
     *
     * @param gameEventsListener game events listener
     */
    public void setGameEventsListener(GameEventsListener gameEventsListener) {
        this.gameEventsListener = gameEventsListener;
    }


    /**
     * Get the game board
     *
     * @return game board
     */
    public Board getBoard() {
        return board;
    }


    /**
     * Set the game board
     *
     * @param board game board
     */
    public void setBoard(Board board) {
        this.board = board;
    }


    /**
     * Get black player
     *
     * @return black player
     */
    public Player getPlayerBlack() {
        return playerBlack;
    }


    /**
     * Set black player
     *
     * @param playerBlack black player
     */
    public void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }


    /**
     * Get white player
     *
     * @return white player
     */
    public Player getPlayerWhite() {
        return playerWhite;
    }


    /**
     * Set white player
     *
     * @param playerWhite white player
     */
    public void setPlayerWhite(Player playerWhite) {
        this.playerWhite = playerWhite;
    }


    /**
     * Get active player
     *
     * @return active player
     */
    public Player getActivePlayer() {
        return activePlayer;
    }


    /**
     * Set active player
     *
     * @param activePlayer active player
     */
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }


    /**
     * Get non active player
     *
     * @return non active player
     */
    public Player getNonActivePlayer() {
        return nonActivePlayer;
    }


    /**
     * Set non active player
     *
     * @param nonActivePlayer non active player
     */
    public void setNonActivePlayer(Player nonActivePlayer) {
        this.nonActivePlayer = nonActivePlayer;
    }


    /**
     * Set stone on board coordinates
     *
     * @param x row coordinate
     * @param y column coordinate
     * @return success of operation
     */
    public boolean setStone(int x, int y) {

        BoardState currentStateCopy = this.getBoard().getCurrentState().clone();

        currentStateCopy.state[x][y] = this.getActivePlayer().getColor();
        currentStateCopy.setPlayer(this.getNonActivePlayer());

        int shift_i = 0;
        int shift_j = 0;

        for (int direction = 0; direction < 8; direction++) {
            int i = x;
            int j = y;
            switch (direction) {
                case 0:
                    shift_i = 0;
                    shift_j = 1;
                    break;
                case 1:
                    shift_i = 1;
                    shift_j = 1;
                    break;
                case 2:
                    shift_i = 1;
                    shift_j = 0;
                    break;
                case 3:
                    shift_i = 1;
                    shift_j = -1;
                    break;
                case 4:
                    shift_i = 0;
                    shift_j = -1;
                    break;
                case 5:
                    shift_i = -1;
                    shift_j = -1;
                    break;
                case 6:
                    shift_i = -1;
                    shift_j = 0;
                    break;
                case 7:
                    shift_i = -1;
                    shift_j = 1;
                    break;
            }
            System.out.println(j + shift_j);
            System.out.println(i + shift_i);
            int distance = 0;
            if (((i + shift_i) < 0) || ((i + shift_i) >= this.board.getSize())) {
                distance = 0;
                continue;
            }
            if (((j + shift_j) < 0) || ((j + shift_j) >= this.board.getSize())) {
                distance = 0;
                continue;
            }
            while (currentStateCopy.state[i + shift_i][j + shift_j] == -this.getActivePlayer().getColor()) {
                i += shift_i;
                j += shift_j;
                if (((i + shift_i) < 0) || ((i + shift_i) > this.board.getSize())) {
                    distance = 0;
                    i = x;
                    j = y;
                    break;
                }
                if (((j + shift_j) < 0) || ((j + shift_j) > this.board.getSize())) {
                    distance = 0;
                    i = x;
                    j = y;
                    break;
                }

                distance++;

            }
            if (currentStateCopy.state[i + shift_i][j + shift_j] == 0 || currentStateCopy.state[i + shift_i][j + shift_j] == 42) { // dosiel som po policko, ktore nema ziadny kamen
                distance = 0;
            }
            for (int d = 0; d < distance; d++) {
                if (((i - shift_i) < 0) || ((i - shift_i) > this.board.getSize())) {
                    distance = 0;
                    break;
                }
                if (((j - shift_j) < 0) || ((j - shift_j) > this.board.getSize())) {
                    distance = 0;
                    break;
                }
                currentStateCopy.state[i][j] = this.getActivePlayer().getColor();
                i -= shift_i;
                j -= shift_j;
            }
        }

        // TODO here: change oponents stones based on current players move (changeOpponentsStones())

        this.getBoard().setNewState(currentStateCopy);

        this.setNextActivePlayer();
        System.out.println(this.getActivePlayer().getColor());
        return true;
    }


    /**
     * Set next player depending on active player
     */
    private void setNextActivePlayer() {
        switch (this.getActivePlayer().getColor()) {

            case Player.COLOR_BLACK:
                if (canPlay(this.getPlayerWhite())) {
                    this.setActivePlayer(this.getPlayerWhite());
                    this.setNonActivePlayer(this.getPlayerBlack());
                } else if (!canPlay(this.getPlayerWhite())) {
                    // neither can play - quit game
                    // trigger event
                    this.gameEventsListener.onChangeState();
                    this.quitGame();
                }
                break;

            case Player.COLOR_WHITE:
                if (canPlay(this.getPlayerBlack())) {
                    this.setActivePlayer(this.getPlayerBlack());
                    this.setNonActivePlayer(this.getPlayerWhite());
                } else if (!canPlay(this.getPlayerBlack())) {
                    // neither can play - quit game
                    // trigger event
                    this.gameEventsListener.onChangeState();
                    this.quitGame();
                }
                break;
        }
    }


    /**
     * Check if player can play - player has some potential stones
     *
     * @param player player to check
     * @return true if player can play, else return false
     */
    private boolean canPlay(Player player) {
        int[][] potentialStones = this.getBoard().getCurrentState().getPotentialStones(player);
        for (int i = 0; i < this.getBoard().getSize(); i++) {
            for (int j = 0; j < this.getBoard().getSize(); j++) {
                if (potentialStones[i][j] == BoardState.STONE_POTENTIAL) {

                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Start game after game session was initialized
     */
    public void startGame() {
        int size = this.board.getSize();
        // player with black stones begins game
        this.setActivePlayer(this.getPlayerBlack());
        this.setNonActivePlayer(this.getPlayerWhite());

        BoardState startState = new BoardState(size);
        // initial board stones
        startState.state[size / 2 - 1][size / 2 - 1] = BoardState.STONE_WHITE;
        startState.state[size / 2 - 1][size / 2] = BoardState.STONE_BLACK;
        startState.state[size / 2][size / 2 - 1] = BoardState.STONE_BLACK;
        startState.state[size / 2][size / 2] = BoardState.STONE_WHITE;
        startState.setPlayer(this.getActivePlayer());
        this.getBoard().setNewState(startState);

        this.continueGame();

        // trigger event
        this.gameEventsListener.onStartGame();
    }


    /**
     * Continue game - send message play to active player
     */
    public void continueGame() {
        // trigger event
        this.gameEventsListener.onChangeState();
        this.getActivePlayer().play(this);

        // trigger event
        this.gameEventsListener.onContinueGame();
    }


    /**
     * Save game to file
     *
     * @param fileName name of file
     * @throws IOException
     */
    public void saveGame(String fileName) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();

        // trigger event
        this.gameEventsListener.onSaveGame();
    }


    /**
     * Undo player step
     * Note: if previous player is computer then will be undone two steps
     */
    public void undoGame() {
        this.getBoard().undoState();
        this.setActivePlayer(this.getBoard().getCurrentState().getPlayer());
        if (this.getActivePlayer().isHuman()) {
            this.continueGame();
        } else {
            this.undoGame();
        }

        // trigger event
        this.gameEventsListener.onUndoGame();
    }


    /**
     * Quit current game session
     */
    public void quitGame() {
        // trigger event
        this.gameEventsListener.onQuitGame();
    }

}
