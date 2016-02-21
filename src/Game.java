import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {

    private JFrame window;

    private Board board;

    private Player playerBlack;

    private Player playerWhite;

    public boolean setStone(int x, int y) {

        return true;
    }

    public void startGame() {
        if (this.window == null) {
            this.window = new JFrame();
            Container board = new Container();
            this.window.setContentPane(board);
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

        Container board = this.window.getContentPane();

        GridLayout layout = new GridLayout(0, this.board.getSize(), 2, 2);
        board.setLayout(layout);

        board.removeAll();  // remove all old boxes

        BoardState currentState = this.board.getCurrentState();
        int[][] potencialStones = currentState.getPotencialStones();

        int size = this.board.getSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JPanel box;
                if (currentState.state[i][j] == BoardState.STONE_WHITE) {
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
                } else if (currentState.state[i][j] == BoardState.STONE_BLACK) {
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
                    if (potencialStones[i][j] == BoardState.STONE_POTENCIAL) {
                        box = new JPanel() {
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                g.setColor(Color.BLACK);
                                g.fillOval(20, 20, getWidth() - 40, getHeight() - 40);
                            }

                            public void paint(Graphics g) {
                                Graphics2D g2 = (Graphics2D) g;
                                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                super.paint(g);
                            }
                        };
                        box.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        box.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                Component panel = (Component)e.getSource();
                                String name = panel.getName();
                                System.out.println("Coordinates: " + name);
                            }
                        });
                    } else {
                        box = new JPanel();
                    }
                }

                box.setName(Integer.toString(i) + ":" + Integer.toString(j));
                box.setBackground(Color.GREEN.darker());
                board.add(box);
            }
        }
    }
}
