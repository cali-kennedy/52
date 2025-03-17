package main.java.javase.t52.core;


import main.java.javase.t52.core.cards.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * TexasHoldem.java
 *
 * This class encapsulates the core game logic for Texas Hold’em.
 * It maintains the game state, manages game rounds, and notifies registered
 * GameStateListeners when state changes occur.
 */
public class TexasHoldem {

    // Observers for game state changes
    private List<GameStateListener> listeners;

    // Core game state attributes
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private Hand communityCards;

    private int playerChips;
    private int dealerChips;
    private int pot;
    private int currentBet;

    private boolean validOption;
    private boolean validRaise;
    private int raiseAmount;

    private CountDownLatch latch;

    // Options is an enum representing player/dealer moves
    private Options playerOption;
    private Options dealerOption;

    private boolean gameRunning;

    /**
     * Constructor initializes the game state.
     */
    public TexasHoldem() {
        this.listeners = new ArrayList();
        // Initialize game state (using default values; adjust as needed)
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.communityCards = new Hand();

        this.playerChips = 1000;
        this.dealerChips = 1000;
        this.pot = 0;
        this.currentBet = 0;

        this.validOption = false;
        this.validRaise = false;
        this.raiseAmount = 0;

        this.latch = new CountDownLatch(1); // placeholder: adjust per betting round
        // Default options can be set if Options is an enum or class; for now we leave null.
        this.playerOption = null;
        this.dealerOption = null;

        this.gameRunning = false;
    }

    // ----------------------
    // Game Logic Methods
    // ----------------------

    /**
     * Starts the game.
     */
    public void startGame() {
        System.out.println("Starting game...");
        this.gameRunning = true;
        // Initialize or shuffle deck, deal initial hands, etc.
        // ...
        notifyGameStateChanged();
    }

    /**
     * Plays a round of the game.
     */
    public void playRound() {
        System.out.println("Playing a round...");
        // Implement round logic: deal cards, betting, etc.
        // ...
        notifyGameStateChanged();
    }

    /**
     * Executes a betting round. Uses CountDownLatch to synchronize actions.
     */
    public void executeBettingRound() {
        System.out.println("Executing betting round...");
        // Wait for player action using latch
        try {
            latch.await(); // In a real implementation, you’d reset and count down the latch accordingly.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Process the betting round logic...
        notifyGameStateChanged();
    }

    /**
     * Handles a generic option from the player.
     * @param option A boolean flag (for example, a simplified decision indicator)
     */
    public void handleOption(boolean option) {
        System.out.println("Handling option: " + option);
        this.validOption = option;
        notifyGameStateChanged();
    }

    /**
     * Returns the player's move.
     * @param option A parameter representing the player's move (can be refined)
     * @return The player's move as an Options object.
     */
    public Options displayMove(Options option) {
        // For now, simply return the provided option.
        return option;
    }

    public void handleCheck() {
        System.out.println("Player checks.");
        // Implement check logic
        notifyGameStateChanged();
    }

    public void handleCall(boolean option) {
        System.out.println("Player calls with option: " + option);
        // Implement call logic
        notifyGameStateChanged();
    }

    public void handleRaise() {
        System.out.println("Player raises.");
        // Implement raise logic
        notifyGameStateChanged();
    }

    public void handleFold() {
        System.out.println("Player folds.");
        // Implement fold logic
        notifyGameStateChanged();
    }

    public void setDealerOption() {
        System.out.println("Dealer selects an option.");
        // Randomly set dealerOption (if Options is an enum or similar)
        notifyGameStateChanged();
    }

    public void determineWinner() {
        System.out.println("Determining winner...");
        // Compare hands, determine winner, update chips/pot accordingly
        notifyGameStateChanged();
    }

    public void waitForResponse() {
        System.out.println("Waiting for player response...");
        // Implementation detail for synchronizing player responses
    }

    public void resetGame() {
        System.out.println("Resetting game...");
        // Reset the game state to initial values
        this.gameRunning = false;
        // Reset hands, pot, currentBet, etc.
        notifyGameStateChanged();
    }

    // ----------------------
    // Observer Pattern Methods
    // ----------------------

    public void addGameStateListener(GameStateListener listener) {
        listeners.add(listener);
    }

    public void removeGameStateListener(GameStateListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that the game state has changed.
     */
    public void notifyGameStateChanged() {
        for (GameStateListener listener : listeners) {
            listener.gameStateChanged();
        }
    }

    // ----------------------
    // Getters (for the Controller to query the state)
    // ----------------------

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    // Additional getters and setters for other private attributes would be added here.

    // Optionally, include setters if needed.
}
