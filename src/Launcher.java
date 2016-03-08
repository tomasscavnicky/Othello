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
    public JRadioButton whiteColorRadioButton;
    public JRadioButton blackColorRadioButton;
    public JRadioButton algorithm1RadioButton;
    public JRadioButton algorithm2RadioButton;
    public JCheckBox stoneFreezeCheckBox;
    public JTextField stoneCount;
    public JTextField stoneFreezeIntervalITextField;
    public JTextField stoneFreezeIntervalBTextField;
    public JPanel colorPanel;
    public JPanel stoneFreezePanel;
    public JPanel stoneFreezeIntervalPanel;
    public JPanel stoneFreezeCountPanel;
    public JPanel oponentPanel;
    public JPanel boardSizePanel;
    public JPanel algorithmPanel;
    public ButtonGroup algorithmButtonGroup;
    public ButtonGroup colorButtonGroup;
    public ButtonGroup oponentButtonGroup;
    public ButtonGroup boardSizeButtonGroup;
    public JButton buttonCancel;

    public Launcher() {
        // JMenuBar in the Mac OS X menubar
        System.setProperty("apple.laf.useScreenMenuBar", "true");

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

                    Othello.loadGame(file.getPath());
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
                    colorPanel.setVisible(true);
                    algorithmPanel.setVisible(true);
                } else {
                    colorPanel.setVisible(false);
                    algorithmPanel.setVisible(false);
                }
            }
        });

        stoneFreezeCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (stoneFreezeCheckBox.isSelected()) {
                    stoneFreezeCountPanel.setVisible(true);
                    stoneFreezeIntervalPanel.setVisible(true);
                } else {
                    stoneFreezeCountPanel.setVisible(false);
                    stoneFreezeIntervalPanel.setVisible(false);
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


        setBounds(100, 100, 500, 400);
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
