import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Enumeration;

public class Launcher extends JFrame {
    private JPanel contentPane;
    public JButton buttonPlay;
    public JRadioButton boardSize8;
    public JRadioButton boardSize6;
    public JRadioButton boardSize10;
    public JRadioButton boardSize12;
    public JRadioButton humanOponentRadioButton;
    public JRadioButton computerOponentRadioButton;
    public JCheckBox stoneFreezeCheckBox;
    public JRadioButton whiteColorRadioButton;
    public JRadioButton blackColorRadioButton;
    public ButtonGroup colorButtonGroup;
    public ButtonGroup oponentButtonGroup;
    public ButtonGroup boardSizeButtonGroup;
    public JButton buttonCancel;

    public Launcher() {
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mMainMenu = new JMenu("Main menu");
        menuBar.add(mMainMenu);
        JMenuItem mMainMenuLoadGame = new JMenuItem("Load game");
        mMainMenuLoadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    System.out.println("Opening: " + file.getName() + "."); // TODO
                } else {
                    System.out.println("Open command cancelled by user.");  // TODO
                }
            }
        });
        JMenuItem mMainMenuExit = new JMenuItem("Exit");
        mMainMenuExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                System.exit(0);
            }
        });

        mMainMenu.add(mMainMenuLoadGame);
        mMainMenu.addSeparator();
        mMainMenu.add(mMainMenuExit);

        JMenu mHelp = new JMenu("Help");
        menuBar.add(mHelp);
        JMenuItem mHelpAbout = new JMenuItem("About");
        mHelpAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Project for IJA course", "About Othello", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        mHelp.add(mHelpAbout);

        computerOponentRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (computerOponentRadioButton.isSelected()) {
                    whiteColorRadioButton.setEnabled(true);
                    blackColorRadioButton.setEnabled(true);
                } else {
                    whiteColorRadioButton.setEnabled(false);
                    blackColorRadioButton.setEnabled(false);
                }
            }
        });

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
                System.exit(0);
            }
        });

        // call dispose() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        setBounds(100, 100, 600, 300);
        setVisible(true);
    }

    private void onPlay() {
        Othello.newGame(this);
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}
