import javax.swing.*;
import java.awt.*;

/**
 * Created by tom on 15/02/16.
 */
public class Game {

    private JFrame window;

    private Board board;

    private Player playerBlack;

    private Player playerWhite;

    public boolean setStone(int x, int y){

        return true;
    }

    public void startGame() {
        if (this.window == null) {
            this.window = new JFrame();
            Container board = new Container();
            this.window.setContentPane(board);
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

        Container board = this.window.getContentPane();

        GridLayout layout = new GridLayout(0, this.board.getSize(), 2, 2);
        board.setLayout(layout);


        int size = this.board.getSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JPanel box;
                if ((i == size / 2 - 1 && j == size / 2 - 1) || (i == size / 2 && j == size / 2)) {
                    box = new JPanel() {
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.setColor(Color.WHITE);
                            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
                        }

                        public void paint(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g;
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            super.paint(g);
                        }
                    };
                } else if ((i == size / 2 && j == size / 2 - 1) || (i == size / 2 - 1 && j == size / 2)) {
                    box = new JPanel() {
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.setColor(Color.BLACK);
                            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
                        }

                        public void paint(Graphics g) {
                            Graphics2D g2 = (Graphics2D) g;
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            super.paint(g);
                        }
                    };
                } else {
                    box = new JPanel();
                }

                box.setBackground(new Color(0, 200, 0));
                board.add(box);
            }
        }

        this.window.setBounds(100, 100, 400, 400);
        this.window.setVisible(true);
    }
}
