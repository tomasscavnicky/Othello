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

            if (launcher.whiteColorRadioButton.isSelected()) {

                playerWhite.setHuman(true);
                playerBlack.setHuman(false);

            } else if (launcher.blackColorRadioButton.isSelected()) {

                playerWhite.setHuman(false);
                playerBlack.setHuman(true);
            }
        }

        boolean stoneFreeze = launcher.stoneFreezeCheckBox.isSelected();

        Game game = new Game(playerBlack, playerWhite, boardSize, stoneFreeze);
        game.startGame();
    }
}
