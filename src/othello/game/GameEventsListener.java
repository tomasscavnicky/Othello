/**
 * Project for IJA course
 *
 * @author Tomáš Vlk
 * @author Tomáš Ščavnický
 */

package othello.game;

import java.io.Serializable;


/**
 * Events which could be implemented in some user interface
 */
public abstract class GameEventsListener implements Serializable {


    /**
     * Callback when game state is changed
     */
    public void onChangeState() {
    }


    /**
     * Callback when game is started
     */
    public void onStartGame() {
    }


    /**
     * Callback when game continues after some step
     */
    public void onContinueGame() {
    }


    /**
     * Callback when game is saved
     */
    public void onSaveGame() {
    }


    /**
     * Callback when game step is undone
     */
    public void onUndoGame() {
    }


    /**
     * Callback when fame is quited
     */
    public void onQuitGame() {
    }

}
