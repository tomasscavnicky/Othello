import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {

    private JFrame window;

    private Container boardContainer;

    private Board board;

    private Player playerBlack;

    private Player playerWhite;

    private Player activePlayer;

    public Game(Player playerBlack, Player playerWhite, int size) {
        this.playerBlack = playerBlack;
        this.playerWhite = playerWhite;
        this.board = new Board(size);
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
        // if window not exists, create it
        if (this.window == null) {
            this.window = new JFrame("Othello");

            Container gameContainer = new Container();
            Container controlContainer = new Container();
            this.boardContainer = new Container();

            this.window.setContentPane(gameContainer);

            BorderLayout gameLayout = new BorderLayout();
            GridLayout controlLayout = new GridLayout(1, 10, 2, 2);
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
                    render();
                }
            });
            JLabel score = new JLabel("Score:");
            controlContainer.add(undoButton);
            controlContainer.add(score);
            controlContainer.setPreferredSize(new Dimension(500,50));

            this.window.setResizable(false);
            this.window.setBounds(100, 100, 500, 550);
            this.window.setVisible(true);
        }

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

        this.boardContainer.removeAll();  // remove all old boxes

        BoardState currentState = this.board.getCurrentState();
        int[][] potencialStones = currentState.getPotencialStones();

        int size = this.board.getSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Box box;

                if (potencialStones[i][j] == BoardState.STONE_POTENCIAL) {
                    box = new Box(BoardState.STONE_POTENCIAL);
                    box.setBoardX(i);
                    box.setBoardY(j);
                } else {
                    box = new Box(currentState.state[i][j]);
                }

                this.boardContainer.add(box);
            }
        }

        // redraw board
        this.boardContainer.revalidate();
        this.boardContainer.repaint();
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
                case BoardState.STONE_WHITE:
                    g.setColor(Color.WHITE);
                    g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
                    break;
                case BoardState.STONE_BLACK:
                    g.setColor(Color.BLACK);
                    g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
                    break;
                case BoardState.STONE_POTENCIAL:
                    g.setColor(Color.BLACK);
                    g.fillOval(25, 25, getWidth() - 50, getHeight() - 50);
                    break;
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
