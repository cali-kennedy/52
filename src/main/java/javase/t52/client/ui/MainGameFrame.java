package main.java.javase.t52.client.ui;

import main.java.javase.t52.core.TexasHoldem;
import main.java.javase.t52.client.ui.GameUIController;
import javax.swing.*;
import java.awt.*;

/**
 * MainGameFrame is the top-level view container for the Texas Hold'em application.
 * It uses a CardLayout to switch between various UI panels (Login, Lobby, Gameplay).
 */
public class MainGameFrame extends JFrame {

    // Panels for different parts of the UI
    private LoginPanel loginPanel;
    private LobbyPanel lobbyPanel;
    private GamePlayPanel gamePlayPanel;

    // Container panel that uses CardLayout to switch between panels
    private JPanel mainPanel;
    private CardLayout cardLayout;

    /**
     * Constructs a new MainGameFrame and initializes the UI.
     */
    public MainGameFrame() {
        initUI();
    }

    /**
     * Initializes the user interface by setting up the panels and the layout.
     */
    private void initUI() {
        // Initialize CardLayout and container panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Instantiate the individual panels
        loginPanel = new LoginPanel();
        lobbyPanel = new LobbyPanel();
        gamePlayPanel = new GamePlayPanel();

        // Add panels to the main panel with unique identifiers.
        // Note: Order is important: first parameter is the component, second is the identifier (String)
        mainPanel.add(loginPanel, "LoginPanel");
        mainPanel.add(lobbyPanel, "LobbyPanel");
        mainPanel.add(gamePlayPanel, "GamePlayPanel");

        // Add the container panel to the frame
        getContentPane().add(mainPanel);

        // Configure frame properties
        setTitle("Texas Hold'em Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    /**
     * Switches the currently displayed panel.
     * @param panelName the identifier of the panel to display.
     */
    public void setPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    // Getters for individual panels
    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public LobbyPanel getLobbyPanel() {
        return lobbyPanel;
    }

    public GamePlayPanel getGamePlayPanel() {
        return gamePlayPanel;
    }

    /**
     * Updates the UI components based on the current state of the TexasHoldem model.
     * @param model the TexasHoldem model containing the latest game state.
     */
    public void updateGameState(TexasHoldem model) {
        // Update the gameplay panel with the new state
        gamePlayPanel.updateGameState(model);
    }

    /**
     * Optionally, inject the GameUIController into the panels if needed.
     * @param controller the GameUIController instance.
     */
    public void initUI(GameUIController controller) {
        // Example:
        // loginPanel.setController(controller);
        // lobbyPanel.setController(controller);
        // gamePlayPanel.setController(controller);
    }
}
