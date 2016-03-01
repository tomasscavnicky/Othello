import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Game implements Serializable {

    private transient JFrame window;

    private transient Container boardContainer;

    private Board board;

    private transient JLabel scoreLabel;

    private Player playerBlack;

    private Player playerWhite;

    private Player activePlayer;

    private boolean stoneFreeze;

    public Game(Player playerBlack, Player playerWhite, int size, boolean stoneFreeze) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.board = new Board(size);
        this.stoneFreeze = stoneFreeze;
    }

    public boolean setStone(int x, int y) {

        BoardState currentStateCopy = this.board.getCurrentState().clone();

        // TODO change current state
        currentStateCopy.state[x][y] = BoardState.STONE_BLACK;

        currentStateCopy.setPlayer(this.activePlayer);

        this.board.setNewState(currentStateCopy);

        return true;
    }

    public void startGame() {

        int size = this.board.getSize();
        this.activePlayer = playerBlack;    // player with black stones begins game

        BoardState startState = new BoardState(size);
        // initial board stones
        startState.state[size / 2 - 1][size / 2 - 1] = BoardState.STONE_WHITE;
        startState.state[size / 2 - 1][size / 2] = BoardState.STONE_BLACK;
        startState.state[size / 2][size / 2 - 1] = BoardState.STONE_BLACK;
        startState.state[size / 2][size / 2] = BoardState.STONE_WHITE;
        startState.setPlayer(this.activePlayer);
        this.board.setNewState(startState);

        this.render();
        this.activePlayer.play(this);
    }

    public void continueGame() {
        switch (activePlayer.getColor()) {
            case Player.COLOR_BLACK:
                if (canPlay(this.playerWhite)) {
                    this.activePlayer = this.playerWhite;
                }
                break;
            case Player.COLOR_WHITE:
                if (canPlay(this.playerBlack)) {
                    this.activePlayer = this.playerBlack;
                }
                break;
        }

        this.render();
        this.activePlayer.play(this);
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

    }

    public boolean canPlay(Player player) {
        // TODO
        /*
        this.board.getCurrentState().getPotencialStones();
        if (there is some potencial stone){
            return true;
        } else {
            return false;
        }
        */
        return true;
    }

    public void render() {

        // if window not exists, create it
        if (this.window == null) {
            renderWindow();
        }

        this.boardContainer.removeAll();  // remove all old boxes

        BoardState currentState = this.board.getCurrentState();
        int[][] potencialStones = currentState.getPotencialStones();

        int size = this.board.getSize();

        int blackStones = 0;
        int whiteStones = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Box box;

                if (potencialStones[i][j] == BoardState.STONE_POTENCIAL) {
                    box = new Box(BoardState.STONE_POTENCIAL);
                    box.setBoardX(i);
                    box.setBoardY(j);
                } else {
                    if (currentState.state[i][j] == BoardState.STONE_BLACK) {
                        blackStones++;
                    } else if (currentState.state[i][j] == BoardState.STONE_WHITE) {
                        whiteStones++;
                    }
                    box = new Box(currentState.state[i][j]);
                }

                this.boardContainer.add(box);
            }
        }

        this.scoreLabel.setText(blackStones + " vs " + whiteStones);

        // redraw board
        this.boardContainer.revalidate();
        this.boardContainer.repaint();
    }

    private void renderWindow() {
        this.window = new JFrame("Othello");

        Container gameContainer = new Container();
        Container controlContainer = new Container();
        this.boardContainer = new Container();

        this.window.setContentPane(gameContainer);

        BorderLayout gameLayout = new BorderLayout();
        GridBagLayout controlLayout = new GridBagLayout();
        GridBagConstraints controlLayoutConstrains = new GridBagConstraints();
        GridLayout boardLayout = new GridLayout(0, this.board.getSize(), 2, 2);

        gameContainer.setLayout(gameLayout);
        controlContainer.setLayout(controlLayout);
        this.boardContainer.setLayout(boardLayout);

        gameContainer.add(boardContainer, BorderLayout.CENTER);
        gameContainer.add(controlContainer, BorderLayout.PAGE_END);

        JButton undoButton = new JButton("Undo");
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                board.undoState();
                continueGame();
            }
        });
        Box black = new Box(BoardState.STONE_BLACK);
        black.setOpaque(false);
        black.setMaximumSize(new Dimension(50, 50));
        this.scoreLabel = new JLabel("", SwingConstants.CENTER);
        this.scoreLabel.setBackground(Color.BLACK);
        Box white = new Box(BoardState.STONE_WHITE);
        white.setOpaque(false);
        JButton saveButton = new JButton("Save");
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();

                    saveGame(file.getPath());
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
        controlContainer.add(black, controlLayoutConstrains);
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
        controlContainer.add(white, controlLayoutConstrains);
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

    private class Box extends JPanel {

        int type;

        int boardX;

        int boardY;

        public int getBoardX() {
            return boardX;
        }

        public void setBoardX(int boardX) {
            this.boardX = boardX;
        }

        public int getBoardY() {
            return boardY;
        }

        public void setBoardY(int boardY) {
            this.boardY = boardY;
        }

        public Box(int type) {
            this.type = type;
            this.setBackground(Color.GREEN.darker());

            if (this.type == BoardState.STONE_POTENCIAL) {
                this.setCursor(new Cursor(Cursor.HAND_CURSOR));
                this.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Box box = (Box) e.getSource();
                        setStone(box.getBoardX(), box.getBoardY());
                        continueGame();
                    }
                });
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            switch (this.type) {
                case BoardState.STONE_WHITE: {
                    g.setColor(Color.WHITE);
                    int width = getWidth() * 90 / 100;
                    int height = getHeight() * 90 / 100;
                    g.fillOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    g.setColor(Color.GRAY);
                    g.drawOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    break;
                }
                case BoardState.STONE_BLACK: {
                    g.setColor(Color.BLACK);
                    int width = getWidth() * 90 / 100;
                    int height = getHeight() * 90 / 100;
                    g.fillOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    break;
                }
                case BoardState.STONE_POTENCIAL: {
                    g.setColor(Color.DARK_GRAY);
                    int width = getWidth() * 15 / 100;
                    int height = getHeight() * 15 / 100;
                    g.fillOval((getWidth() - width) / 2, (getHeight() - height) / 2, width, height);
                    break;
                }
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
}
