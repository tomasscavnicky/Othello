package othello.game;

import java.io.Serializable;

public interface EventsListener extends Serializable {

    void onChangeState();

    void onStartGame();

    void onContinueGame();

    void onSaveGame();

    void onUndoGame();

    void onQuitGame();
}
