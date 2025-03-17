package main.java.javase.t52.core;

/**
 * GameStateListener is an interface for objects that need to be notified
 * when the state of the TexasHoldem game changes.
 */
public interface GameStateListener {
    /**
     * Called when the game state has changed.
     */
    void gameStateChanged();
}