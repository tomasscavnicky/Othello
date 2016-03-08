import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Othello {

    public static void main(String[] args) {
        new Launcher();
    }

    public static void newGame(Launcher launcher) {

        int boardSize = Integer.parseInt(launcher.getSelectedButtonText(launcher.boardSizeButtonGroup));

        Player playerWhite = new Player(Player.COLOR_WHITE);
        Player playerBlack = new Player(Player.COLOR_BLACK);

        if (launcher.humanOponentRadioButton.isSelected()) {

            playerWhite.setHuman(true);
            playerBlack.setHuman(true);

        } else if (launcher.computerOponentRadioButton.isSelected()) {

            int algorithm = Integer.parseInt(launcher.getSelectedButtonText(launcher.algorithmButtonGroup));

            if (launcher.whiteColorRadioButton.isSelected()) {

                playerWhite.setHuman(true);
                playerBlack.setHuman(false);
                playerBlack.setAlgorithm(algorithm);

            } else if (launcher.blackColorRadioButton.isSelected()) {

                playerWhite.setHuman(false);
                playerWhite.setAlgorithm(algorithm);
                playerBlack.setHuman(true);
            }
        }

        boolean stoneFreeze = launcher.stoneFreezeCheckBox.isSelected();
        // TODO set inteval I and B and stone count

        Game game = new Game(playerBlack, playerWhite, boardSize, stoneFreeze);
        game.startGame();
    }

    public static void loadGame(String fileName) {
        Game game;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();

            game.continueGame();

        } catch (IOException i) {
            JOptionPane.showMessageDialog(null, "Cannot load game", "Error", JOptionPane.ERROR_MESSAGE);
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            JOptionPane.showMessageDialog(null, "Class not found", "Error", JOptionPane.ERROR_MESSAGE);
            c.printStackTrace();
        }
    }
}
