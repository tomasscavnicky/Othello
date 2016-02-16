import javax.swing.*;
import java.awt.*;

/**
 * Created by tom on 15/02/16.
 */
public class Game {

    private Board board;

    private Player playerBlack;

    private Player playerWhite;

    public boolean setStone(int x, int y){

        return true;
    }

    public startGame() {
        playerBlack.play();
    }

    public continueGame() {

    }





    public Dimension boardSize;

    public boolean stoneFreeze;

    public int rival;

    public Dimension getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(Dimension boardSize) {
        this.boardSize = boardSize;
    }

    public boolean isStoneFreeze() {
        return stoneFreeze;
    }

    public void setStoneFreeze(boolean stoneFreeze) {
        this.stoneFreeze = stoneFreeze;
    }

    public int getRival() {
        return rival;
    }

    public void setRival(int rival) {
        this.rival = rival;
    }


    public void render() {
        JFrame frame = new JFrame();
        Container board = new Container();

        frame.setContentPane(board);

        GridLayout layout = new GridLayout(0, this.getBoardSize().width, 2, 2);
        board.setLayout(layout);


        int width = this.getBoardSize().width;
        int height = this.getBoardSize().height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                JPanel box;
                if ((i == width / 2 - 1 && j == height / 2 - 1) || (i == width / 2 && j == height / 2)) {
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
                } else if ((i == width / 2 && j == height / 2 - 1) || (i == width / 2 - 1 && j == height / 2)) {
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

        frame.setBounds(100, 100, 400, 400);
        frame.setVisible(true);
    }
}
