/**
 * Project for IJA course
 *
 * @author Tomáš Vlk
 * @author Tomáš Ščavnický
 */

package othello.gui;

import othello.game.BoardState;
import othello.game.GameEventsListener;
import othello.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


/**
 * Implementation of GUI callbacks to game events
 */
public class GUIGameEventsListener extends GameEventsListener {

    private transient JFrame window;

    private transient Container boardContainer;

    private transient Score scoreLabel;

    private transient Box blackLabel;

    private transient Box whiteLabel;

    private Game game;


    /**
     * GUI game events listener constructor
     *
     * @param game game which will be rendered
     */
    public GUIGameEventsListener(Game game) {
        this.game = game;
    }


    @Override
    public void onQuitGame() {
        String message = "Score: Black " + this.scoreLabel.getBlackScore() + " vs White " + this.scoreLabel.getWhiteScore() + "\n";
        if (this.scoreLabel.getBlackScore() > this.scoreLabel.getWhiteScore()) {
            message += "\nPlayer black wins!!\n";
        } else if (this.scoreLabel.getBlackScore() < this.scoreLabel.getWhiteScore()) {
            message += "\nPlayer white wins!!\n";
        } else {
            message += "\nIt's a draw\n";
        }

        JOptionPane.showMessageDialog(null, message, "End of the game", JOptionPane.INFORMATION_MESSAGE);
    }


    @Override
    public void onChangeState() {
        this.render();
    }


    /**
     * Render game state into window
     */
    public void render() {

        // if window not exists, create it
        if (this.window == null) {
            this.renderWindow();
        }

        // remove all old boxes
        this.boardContainer.removeAll();

        // set active players labels
        if (this.game.getActivePlayer() == this.game.getPlayerBlack()) {
            this.whiteLabel.setActive(false);
            this.blackLabel.setActive(true);
        } else if (this.game.getActivePlayer() == this.game.getPlayerWhite()) {
            this.whiteLabel.setActive(true);
            this.blackLabel.setActive(false);
        }

        // get current state and potential stones
        BoardState currentState = this.game.getBoard().getCurrentState();
        int[][] potentialStones = currentState.getPotentialStones();

        int size = this.game.getBoard().getSize();

        int blackStones = 0;
        int whiteStones = 0;


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Box box;

                if (currentState.state[i][j] == BoardState.STONE_BLACK) {
                    box = new Box(currentState.state[i][j]);
                    blackStones++;
                } else if (currentState.state[i][j] == BoardState.STONE_WHITE) {
                    box = new Box(currentState.state[i][j]);
                    whiteStones++;
                } else if (potentialStones[i][j] == BoardState.STONE_POTENTIAL) {
                    box = new Box(BoardState.STONE_POTENTIAL);
                    box.setBoardX(i);
                    box.setBoardY(j);
                } else {
                    box = new Box(BoardState.STONE_NONE);
                }

                this.boardContainer.add(box);
            }
        }

        this.scoreLabel.setScore(blackStones, whiteStones);


        // redraw board
        this.boardContainer.revalidate();
        this.boardContainer.repaint();
    }


    /**
     * Render game window
     */
    private void renderWindow() {
        this.window = new JFrame("Othello");

        Container gameContainer = new Container();
        Container controlContainer = new Container();
        this.boardContainer = new Container();

        this.window.setContentPane(gameContainer);

        BorderLayout gameLayout = new BorderLayout();
        GridBagLayout controlLayout = new GridBagLayout();
        GridBagConstraints controlLayoutConstrains = new GridBagConstraints();
        GridLayout boardLayout = new GridLayout(0, this.game.getBoard().getSize(), 2, 2);

        gameContainer.setLayout(gameLayout);
        controlContainer.setLayout(controlLayout);
        this.boardContainer.setLayout(boardLayout);

        gameContainer.add(boardContainer, BorderLayout.CENTER);
        gameContainer.add(controlContainer, BorderLayout.PAGE_END);

        JButton undoButton = new JButton("Undo");
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                game.undoGame();
            }
        });

        this.blackLabel = new Box(BoardState.STONE_BLACK);
        blackLabel.setOpaque(false);
        this.scoreLabel = new Score();
        this.scoreLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        this.whiteLabel = new Box(BoardState.STONE_WHITE);
        whiteLabel.setOpaque(false);

        JButton saveButton = new JButton("Save");
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();

                    try {
                        game.saveGame(file.getPath());
                    } catch (IOException i) {

                        JOptionPane.showMessageDialog(null, "Cannot save game", "Error", JOptionPane.ERROR_MESSAGE);
                        i.printStackTrace();
                    }
                }
            }
        });

        gameContainer.setPreferredSize(new Dimension(500, 500));

        controlLayoutConstrains.insets = new Insets(5, 5, 5, 5);
        controlLayoutConstrains.fill = GridBagConstraints.BOTH;
        controlLayoutConstrains.weightx = 1;
        controlLayoutConstrains.weighty = 1;
        controlLayoutConstrains.gridx = 0;
        controlLayoutConstrains.gridy = 0;
        controlLayoutConstrains.gridheight = 1;
        controlLayoutConstrains.gridwidth = 3;
        controlLayoutConstrains.ipadx = 0;
        controlLayoutConstrains.ipady = 0;
        controlContainer.add(undoButton, controlLayoutConstrains);
        controlLayoutConstrains.fill = GridBagConstraints.NONE;
        controlLayoutConstrains.gridx = 3;
        controlLayoutConstrains.gridwidth = 1;
        controlLayoutConstrains.ipadx = 30;
        controlLayoutConstrains.ipady = 30;
        controlContainer.add(blackLabel, controlLayoutConstrains);
        controlLayoutConstrains.gridx = 4;
        controlLayoutConstrains.gridwidth = 2;
        controlLayoutConstrains.ipadx = 0;
        controlLayoutConstrains.ipady = 0;
        controlContainer.add(scoreLabel, controlLayoutConstrains);
        controlLayoutConstrains.fill = GridBagConstraints.NONE;
        controlLayoutConstrains.gridx = 6;
        controlLayoutConstrains.gridwidth = 1;
        controlLayoutConstrains.ipadx = 30;
        controlLayoutConstrains.ipady = 30;
        controlContainer.add(whiteLabel, controlLayoutConstrains);
        controlLayoutConstrains.fill = GridBagConstraints.BOTH;
        controlLayoutConstrains.gridx = 7;
        controlLayoutConstrains.gridwidth = 3;
        controlLayoutConstrains.ipadx = 0;
        controlLayoutConstrains.ipady = 0;
        controlContainer.add(saveButton, controlLayoutConstrains);
        controlContainer.setPreferredSize(new Dimension(500, 50));

        this.window.setResizable(false);
        this.window.setBounds(100, 100, 500, 550);
        this.window.setVisible(true);
    }


    /**
     * Represents field on board.
     * Field can be empty or there can be stone.
     */
    private class Box extends JPanel {

        int type;

        int boardX;

        int boardY;

        boolean active = false;


        /**
         * Get board row where field is
         *
         * @return x coordinate of board
         */
        public int getBoardX() {
            return boardX;
        }


        /**
         * Set board row shere field is
         *
         * @param boardX x coordinate of board
         */
        public void setBoardX(int boardX) {
            this.boardX = boardX;
        }


        /**
         * Get board column where field is
         *
         * @return y coordinate of board
         */
        public int getBoardY() {
            return boardY;
        }


        /**
         * Set board column where field is
         *
         * @param boardY y coordinate of board
         */
        public void setBoardY(int boardY) {
            this.boardY = boardY;
        }


        /**
         * Check if field is active
         *
         * @return true if field is active, else returns false
         */
        public boolean isActive() {
            return active;
        }


        /**
         * Set field as active or as inactive
         *
         * @param active determine if field will be active or not
         */
        public void setActive(boolean active) {
            this.active = active;
            this.revalidate();
            this.repaint();
        }


        /**
         * Box(field) constructor
         *
         * @param type type of stone on field
         */
        public Box(int type) {
            this.type = type;
            this.setBackground(Color.GREEN.darker());

            if (this.type == BoardState.STONE_POTENTIAL) {
                this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                this.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Box box = (Box) e.getSource();
                        game.setStone(box.getBoardX(), box.getBoardY());
                        game.continueGame();
                    }
                });
            }
        }


        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            switch (this.type) {

                // draw white stone
                case BoardState.STONE_WHITE: {
                    g.setColor(Color.WHITE);
                    int width = getWidth() * 90 / 100;
                    int height = getHeight() * 90 / 100;
                    g.fillOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(2));
                    if (this.active) {
                        g2.setColor(Color.RED);
                        g2.drawOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    } else {
                        g2.setColor(Color.GRAY);
                        g2.drawOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    }
                    break;
                }

                // draw black stone
                case BoardState.STONE_BLACK: {
                    g.setColor(Color.BLACK);
                    int width = getWidth() * 90 / 100;
                    int height = getHeight() * 90 / 100;
                    g.fillOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(new BasicStroke(2));
                    if (this.active) {
                        g2.setColor(Color.RED);
                        g2.drawOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    } else {
                        g2.setColor(Color.GRAY);
                        g2.drawOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    }
                    break;
                }

                // draw potential stone
                case BoardState.STONE_POTENTIAL: {
                    g.setColor(Color.DARK_GRAY);
                    int width = getWidth() * 15 / 100;
                    int height = getHeight() * 15 / 100;
                    g.fillOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    break;
                }

                // draw empty field
                case BoardState.STONE_NONE:
                    break;
            }
        }

        // turn on antialiasing
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g);
        }
    }


    /**
     * Actual score label
     */
    private class Score extends JLabel {

        int blackScore;

        int whiteScore;


        /**
         * Get score of black player
         *
         * @return score of black player
         */
        public int getBlackScore() {
            return blackScore;
        }


        /**
         * Set score of black player
         *
         * @param blackScore score of black player
         */
        public void setBlackScore(int blackScore) {
            this.blackScore = blackScore;
        }


        /**
         * Get score of white player
         *
         * @return score of white player
         */
        public int getWhiteScore() {
            return whiteScore;
        }


        /**
         * Set score of white player
         *
         * @param whiteScore score of white player
         */
        public void setWhiteScore(int whiteScore) {
            this.whiteScore = whiteScore;
        }


        /**
         * Set actual score label text
         *
         * @param blackScore score of black player
         * @param whiteScore score of white player
         */
        public void setScore(int blackScore, int whiteScore) {
            this.setBlackScore(blackScore);
            this.setWhiteScore(whiteScore);
            super.setText(blackScore + " vs " + whiteScore);
        }
    }

}
