import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class Game implements Serializable {

    private transient JFrame window;

    private transient Container boardContainer;

    private transient JLabel scoreLabel;

    private transient Box blackLabel;

    private transient Box whiteLabel;

    private Board board;

    private Player playerBlack;

    private Player playerWhite;

    private Player activePlayer;
    private Player nonActivePlayer;

    private boolean stoneFreeze;

    public Game(Player playerBlack, Player playerWhite, int size, boolean stoneFreeze) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.board = new Board(size);
        this.stoneFreeze = stoneFreeze;
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

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean setStone(int x, int y) {

        BoardState currentStateCopy = this.getBoard().getCurrentState().clone();

        currentStateCopy.state[x][y] = activePlayer.getColor();
        currentStateCopy.setPlayer(this.nonActivePlayer);

        this.changeOpponentsStones(x, y);

        this.getBoard().setNewState(currentStateCopy);

        setNextActivePlayer();

        return true;
    }

    private void swapStones(int originX, int originY, int toX, int toY, int direction) {
        int addX = 0;
        int addY = 0;
        switch (direction) {
            case 0:
                addX = 1;
                addY = 0;
                break;
            case 1:
                addX = 1;
                addY = 1;
                break;
            case 2:
                addX = 0;
                addY = 1;
                break;
            case 3:
                addX = -1;
                addY = 1;
                break;
            case 4:
                addX = -1;
                addY = 0;
                break;
            case 5:
                addX = -1;
                addY = -1;
                break;
            case 6:
                addX = 0;
                addY = -1;
                break;
            case 7:
                addX = 1;
                addY = -1;
                break;
        }
        while(originX != toX && originY != toY) {
            this.getBoard().getCurrentState().state[originX][originY] = this.getBoard().getCurrentState().getPlayer().getColor();
            originX += addX;
            originY += addY;
        }
    }

    private void changeOpponentsStones(int x, int y) {

        BoardState currentBoardState = this.getBoard().getCurrentState();



        if (currentBoardState.state[x][y] == BoardState.STONE_POTENCIAL) {
            for (int direction = 0; direction < 8; direction++) {
                boolean positionIsValid = true;
                boolean positionIsSet = false;

                int originX = x;
                int originY = y;

                int toX = -1;
                int toY = -1;

                try {
                    switch (direction) {
                        case 0:
                            if (x + 1 < currentBoardState.size) {
                                while (currentBoardState.state[x + 1][y] == -currentBoardState.player.getColor()) {
                                    if (x + 2 < currentBoardState.size) {
                                        positionIsSet = true;
                                        x++;
                                    } else {
                                        positionIsValid = false;
                                        break;
                                    }
                                }
                                if (currentBoardState.state[x + 1][y] == currentBoardState.player.getColor()) {
                                    toX = x+1;
                                    toY = y;
                                } else {
                                    toX = originX;
                                    toY = originY;
                                }
                                if (positionIsValid  && positionIsSet) {
                                    swapStones(originX, originY, toX, toY, direction);
                                }
                            }
                            break;
                        case 1:
                            if (x + 1 < currentBoardState.size && y + 1 < currentBoardState.size) {
                                while (currentBoardState.state[x + 1][y + 1] == -currentBoardState.player.getColor()) {
                                    if (x + 2 < currentBoardState.size && y + 2 < currentBoardState.size) {
                                        positionIsSet = true;
                                        x++;
                                        y++;
                                    } else {
                                        positionIsValid = false;
                                        break;
                                    }
                                }
                                if (currentBoardState.state[x + 1][y + 1] == currentBoardState.player.getColor()) {
                                    toX = x+1;
                                    toY = y+1;
                                } else {
                                    toX = originX;
                                    toY = originY;
                                }
                                if (positionIsValid  && positionIsSet) {
                                    swapStones(originX, originY, toX, toY, direction);
                                }
                            }
                            break;
                        case 2:
                            if (y + 1 < currentBoardState.size) {
                                while (currentBoardState.state[x][y + 1] == -currentBoardState.player.getColor()) {
                                    if (y + 2 < currentBoardState.size) {
                                        positionIsSet = true;
                                        y++;
                                    } else {
                                        positionIsValid = false;
                                        break;
                                    }
                                }
                                if (currentBoardState.state[x][y + 1] == currentBoardState.player.getColor()) {
                                    toY = y+1;
                                } else {
                                    toX = originX;
                                    toY = originY;
                                }
                                if (positionIsValid  && positionIsSet) {
                                    swapStones(originX, originY, toX, toY, direction);
                                }
                            }
                            break;
                        case 3:
                            if (x - 1 >= 0 && y + 1 < currentBoardState.size) {
                                while (currentBoardState.state[x - 1][y + 1] == -currentBoardState.player.getColor()) {
                                    if (x - 2 >= 0 && y + 2 < currentBoardState.size) {
                                        positionIsSet = true;
                                        x--;
                                        y++;
                                    } else {
                                        positionIsValid = false;
                                        break;
                                    }
                                }
                                if (currentBoardState.state[x - 1][y + 1] == currentBoardState.player.getColor()) {
                                    toX = x-1;
                                    toY = y+1;
                                } else {
                                    toX = originX;
                                    toY = originY;
                                }
                                if (positionIsValid  && positionIsSet) {
                                    swapStones(originX, originY, toX, toY, direction);
                                }
                            }
                            break;
                        case 4:
                            if (x - 1 >= 0) {
                                while (currentBoardState.state[x - 1][y] == -currentBoardState.player.getColor()) {
                                    if (x - 2 >= 0) {
                                        positionIsSet = true;
                                        x--;
                                    } else {
                                        positionIsValid = false;
                                        break;
                                    }
                                }
                                if (currentBoardState.state[x - 1][y] == currentBoardState.player.getColor()) {
                                    toX = x-1;
                                } else {
                                    toX = originX;
                                    toY = originY;
                                }
                                if (positionIsValid  && positionIsSet) {
                                    swapStones(originX, originY, toX, toY, direction);
                                }
                            }
                            break;
                        case 5:
                            if (x - 1 >= 0 && y - 1 >= 0) {
                                while (currentBoardState.state[x - 1][y - 1] == -currentBoardState.player.getColor()) {
                                    if (x - 2 >= 0 && y - 2 >= 0) {
                                        positionIsSet = true;
                                        x--;
                                        y--;
                                    } else {
                                        positionIsValid = false;
                                        break;
                                    }
                                }

                                if (currentBoardState.state[x - 1][y - 1] == currentBoardState.player.getColor()) {
                                    toX = x-1;
                                    toY = y-1;
                                } else {
                                    toX = originX;
                                    toY = originY;
                                }
                                if (positionIsValid  && positionIsSet) {
                                    swapStones(originX, originY, toX, toY, direction);
                                }
                            }
                            break;
                        case 6:
                            if (y - 1 >= 0) {
                                while (currentBoardState.state[x][y - 1] == -currentBoardState.player.getColor()) {
                                    if (y - 2 >= 0) {
                                        positionIsSet = true;
                                        y--;
                                    } else {
                                        positionIsValid = false;
                                        break;
                                    }
                                }
                                if (currentBoardState.state[x][y - 1] == currentBoardState.player.getColor()) {
                                    toY = y-1;
                                } else {
                                    toX = originX;
                                    toY = originY;
                                }
                                if (positionIsValid  && positionIsSet) {
                                    swapStones(originX, originY, toX, toY, direction);
                                }
                            }
                            break;
                        case 7:
                            if (x + 1 < currentBoardState.size && y - 1 >= 0) {
                                while (currentBoardState.state[x + 1][y - 1] == -currentBoardState.player.getColor()) {
                                    if (x + 2 >= 0 && y - 2 >= 0) {
                                        positionIsSet = true;
                                        x++;
                                        y--;
                                    } else {
                                        positionIsValid = false;
                                        break;
                                    }
                                }
                                if (currentBoardState.state[x + 1][y - 1] == currentBoardState.player.getColor()) {
                                    toY = y-1;
                                    toX = x-1;
                                } else {
                                    toX = originX;
                                    toY = originY;
                                }
                                if (positionIsValid  && positionIsSet) {
                                    swapStones(originX, originY, toX, toY, direction);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                } catch (ArrayIndexOutOfBoundsException error) {
                    System.out.println("x: " + x);
                    System.out.println("y: " + y);
                    System.out.println("direction: " + direction);
                    System.out.println("========================================");
                }



            }
        }

    }


    private void setNextActivePlayer() {
        switch (activePlayer.getColor()) {
            case Player.COLOR_BLACK:
                if (canPlay(this.playerWhite)) {
                    this.activePlayer = this.playerWhite;
                    this.nonActivePlayer = this.playerBlack;
                }
                break;
            case Player.COLOR_WHITE:
                if (canPlay(this.playerBlack)) {
                    this.activePlayer = this.playerBlack;
                    this.nonActivePlayer = this.playerWhite;
                }
                break;
        }
    }

    private boolean canPlay(Player player) {
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

    public void startGame() {
        int size = this.board.getSize();
        this.activePlayer = playerBlack;    // player with black stones begins game
        this.nonActivePlayer = playerWhite;

        BoardState startState = new BoardState(size);
        // initial board stones
        startState.state[size / 2 - 1][size / 2 - 1] = BoardState.STONE_WHITE;
        startState.state[size / 2 - 1][size / 2] = BoardState.STONE_BLACK;
        startState.state[size / 2][size / 2 - 1] = BoardState.STONE_BLACK;
        startState.state[size / 2][size / 2] = BoardState.STONE_WHITE;
        startState.setPlayer(this.getActivePlayer());
        this.getBoard().setNewState(startState);

        continueGame();
    }

    public void continueGame() {
        this.render();
        this.getActivePlayer().play(this);
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

    public void undoGame() {
        this.getBoard().undoState();
        this.setActivePlayer(this.getBoard().getCurrentState().getPlayer());
        if (this.getActivePlayer().isHuman()) {
            continueGame();
        } else {
            undoGame();
        }
    }

    public void render() {

        // if window not exists, create it
        if (this.window == null) {
            renderWindow();
        }

        this.boardContainer.removeAll();  // remove all old boxes

        if (this.getActivePlayer() == this.getPlayerBlack()) {
            this.whiteLabel.setActive(false);
            this.blackLabel.setActive(true);
        } else if (this.getActivePlayer() == this.getPlayerWhite()) {
            this.whiteLabel.setActive(true);
            this.blackLabel.setActive(false);
        }

        BoardState currentState = this.getBoard().getCurrentState();
        int[][] potencialStones = currentState.getPotencialStones();

        int size = this.getBoard().getSize();

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
                } else if (potencialStones[i][j] == BoardState.STONE_POTENCIAL) {
                    box = new Box(BoardState.STONE_POTENCIAL);
                    box.setBoardX(i);
                    box.setBoardY(j);
                } else {
                    box = new Box(BoardState.STONE_NONE);
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
        GridLayout boardLayout = new GridLayout(0, this.getBoard().getSize(), 2, 2);

        gameContainer.setLayout(gameLayout);
        controlContainer.setLayout(controlLayout);
        this.boardContainer.setLayout(boardLayout);

        gameContainer.add(boardContainer, BorderLayout.CENTER);
        gameContainer.add(controlContainer, BorderLayout.PAGE_END);

        JButton undoButton = new JButton("Undo");
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                undoGame();
            }
        });
        this.blackLabel = new Box(BoardState.STONE_BLACK);
        blackLabel.setOpaque(false);
        this.scoreLabel = new JLabel("", SwingConstants.CENTER);
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

    private class Box extends JPanel {

        int type;

        int boardX;

        int boardY;

        boolean active = false;

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

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
            this.revalidate();
            this.repaint();
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
