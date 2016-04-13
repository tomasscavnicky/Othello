package othello.game;


public interface EventsListener {

    void onChangeState();

    void onStartGame();

    void onContinueGame();

    void onSaveGame();

    void onUndoGame();

    void onQuitGame();
}
