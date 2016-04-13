package othello.game;

import javax.swing.*;
import java.io.*;

public class Game implements Serializable {

    private Board board;

    private Player playerBlack;

    private Player playerWhite;

    private Player activePlayer;

    private Player nonActivePlayer;

    private boolean stoneFreeze;

    private EventsListener eventListener;

    public Game(Player playerBlack, Player playerWhite, int size, boolean stoneFreeze) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.board = new Board(size);
        this.stoneFreeze = stoneFreeze;
    }

    public EventsListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(EventsListener eventListener) {
        this.eventListener = eventListener;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(Player playerBlack) {
        this.playerBlack = playerBlack;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(Player playerWhite) {
        this.playerWhite = playerWhite;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player getNonActivePlayer() {
        return nonActivePlayer;
    }

    public void setNonActivePlayer(Player nonActivePlayer) {
        this.nonActivePlayer = nonActivePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean setStone(int x, int y) {

        BoardState currentStateCopy = this.getBoard().getCurrentState().clone();

        currentStateCopy.state[x][y] = this.getActivePlayer().getColor();
        currentStateCopy.setPlayer(this.getNonActivePlayer());

        // TODO here: change oponents stones based on current players move (changeOpponentsStones())

        this.getBoard().setNewState(currentStateCopy);

        this.setNextActivePlayer();

        return true;
    }

    private void setNextActivePlayer() {
        switch (this.getActivePlayer().getColor()) {

            case Player.COLOR_BLACK:
                if (canPlay(this.getPlayerWhite())) {
                    this.setActivePlayer(this.getPlayerWhite());
                    this.setNonActivePlayer(this.getPlayerBlack());
                } else if (!canPlay(this.getPlayerBlack())) {
                    // neither can play - quit game
                    this.eventListener.onChangeState();
                    this.quitGame();
                }
                break;

            case Player.COLOR_WHITE:
                if (canPlay(this.getPlayerBlack())) {
                    this.setActivePlayer(this.getPlayerBlack());
                    this.setNonActivePlayer(this.getPlayerWhite());
                } else if (!canPlay(this.getPlayerBlack())) {
                    // neither can play - quit game
                    this.eventListener.onChangeState();
                    this.quitGame();
                }
                break;
        }
    }

    private boolean canPlay(Player player) {
        int[][] potentialStones = this.getBoard().getCurrentState().getPotencialStones(player);
        for (int i = 0; i < this.getBoard().getSize(); i++) {
            for (int j = 0; j < this.getBoard().getSize(); j++) {
                if (potentialStones[i][j] == BoardState.STONE_POTENCIAL) {

                    return true;
                }
            }
        }
        return false;
    }

    public void startGame() {
        int size = this.board.getSize();
        this.setActivePlayer(this.getPlayerBlack());    // player with black stones begins game
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

        this.eventListener.onStartGame();
    }

    public void continueGame() {
        this.eventListener.onChangeState();
        this.getActivePlayer().play(this);

        this.eventListener.onContinueGame();
    }

    public void saveGame(String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();

        } catch (IOException i) {
            JOptionPane.showMessageDialog(null, "Cannot save game", "Error", JOptionPane.ERROR_MESSAGE);
            i.printStackTrace();
        }

        this.eventListener.onSaveGame();
    }

    public void undoGame() {
        this.getBoard().undoState();
        this.setActivePlayer(this.getBoard().getCurrentState().getPlayer());
        if (this.getActivePlayer().isHuman()) {
            this.continueGame();
        } else {
            this.undoGame();
        }

        this.eventListener.onUndoGame();
    }

    public void quitGame() {

        this.eventListener.onQuitGame();
    }

}
