import javax.swing.*;
import java.awt.event.*;

public class Launcher extends JFrame {
    private JPanel contentPane;
    private JButton buttonPlay;
    private JRadioButton boardSize8;
    private JRadioButton boardSize6;
    private JRadioButton boardSize10;
    private JRadioButton boardSize12;
    private JRadioButton člověkRadioButton;
    private JRadioButton počítačRadioButton;
    private JCheckBox checkBox1;
    private JRadioButton bíláRadioButton;
    private JRadioButton černáRadioButton;
    private JButton buttonCancel;

    public Launcher() {
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mMainMenu = new JMenu("Hlavní menu");
        menuBar.add(mMainMenu);
        JMenuItem mMainMenuLoadGame = new JMenuItem("Načíst uloženou hru");
        JMenuItem mMainMenuExit = new JMenuItem("Konec");
        mMainMenu.add(mMainMenuLoadGame);
        mMainMenu.addSeparator();
        mMainMenu.add(mMainMenuExit);

        JMenu mHelp = new JMenu("Nápověda");
        menuBar.add(mHelp);
        JMenuItem mHelpAbout = new JMenuItem("O aplikaci");
        mHelp.add(mHelpAbout);


        getRootPane().setDefaultButton(buttonPlay);

        buttonPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onPlay();
            }
        });

        // call dispose() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call dispose() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        setBounds(100, 100, 600, 300);
        setVisible(true);
    }

    private void onPlay() {
        Othello.newGame();
    }
}
