import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
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
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
}
