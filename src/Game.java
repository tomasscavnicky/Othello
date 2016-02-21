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

    public boolean setStone(int x, int y) {

        return true;
    }

    public void startGame() {
        if (this.window == null) {
            this.window = new JFrame();
            this.boardContainer = new Container();
            GridLayout layout = new GridLayout(0, this.board.getSize(), 2, 2);
            this.boardContainer.setLayout(layout);
            this.window.setContentPane(this.boardContainer);
            this.window.setBounds(100, 100, 400, 400);
            this.window.setVisible(true);
        }
        this.render();

        //playerBlack.play(this);
    }

    public void continueGame() {
        this.render();
    }


    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return this.board;
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
                        // TODO replace by setStone method
                        System.out.println("Coordinates: " + Integer.toString(box.getBoardX()) + ":" + Integer.toString(box.getBoardY()));
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
                    g.fillOval(20, 20, getWidth() - 40, getHeight() - 40);
                    break;
                case BoardState.STONE_NONE:
                    break;
            }
        }

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paint(g);
        }
    }
}
