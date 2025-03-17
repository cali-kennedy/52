package main.java.javase.t52.client.ui;

import main.java.javase.t52.core.TexasHoldem;
import main.java.javase.t52.core.GameStateListener;

/**
 * GameUIController serves as the Controller in the MVC architecture.
 * It mediates between the TexasHoldem model and the MainGameFrame view.
 */
public class GameUIController implements GameStateListener {

    private TexasHoldem model;
    private MainGameFrame view;

    /**
     * Constructs a new GameUIController with the specified model and view.
     * Registers itself as a listener to the model.
     *
     * @param model the TexasHoldem game logic model.
     * @param view  the MainGameFrame that displays the user interface.
     */
    public GameUIController(TexasHoldem model, MainGameFrame view) {
        this.model = model;
        this.view = view;
        // Register this controller to listen for game state changes.
        this.model.addGameStateListener(this);
    }

    /**
     * Displays the login screen.
     */
    public void showLogin() {
        view.setPanel("LoginPanel");
    }

    /**
     * Displays the game lobby.
     */
    public void showLobby() {
        view.setPanel("LobbyPanel");
    }

    /**
     * Displays the gameplay screen.
     */
    public void showGamePlay() {
        view.setPanel("GamePlayPanel");
    }

    /**
     * Handles the login action triggered from the LoginPanel.
     *
     * @param username the username entered by the user.
     * @param password the password entered by the user.
     */
    public void handleLogin(String username, String password) {
        System.out.println("Handling login for user: " + username);
        // Assume login is successful; switch to the lobby view.
        showLobby();
    }

    /**
     * Handles a bet action.
     *
     * @param amount the bet amount.
     */
    public void handleBet(int amount) {
        System.out.println("Handling bet of amount: " + amount);
        // For demonstration, simulate a state change by playing a round.
        model.playRound();
    }

    /**
     * Handles a fold action.
     */
    public void handleFold() {
        System.out.println("Handling fold action.");
        model.handleFold();
    }

    /**
     * This method is called when the game state changes.
     */
    @Override
    public void gameStateChanged() {
        System.out.println("Game state changed. Updating view...");
        view.updateGameState(model);
    }

    /**
     * Handles server responses received from the ClientNetworkManager.
     *
     * @param message the server response message.
     */
    public void handleServerResponse(String message) {
        System.out.println("Received server response: " + message);
        if (message.startsWith("LOGIN_SUCCESS")) {
            showLobby();
        } else if (message.startsWith("GAME_UPDATE")) {
            view.updateGameState(model);
        }
    }
}
