package main.java.javase.t52.client.ui;

import javax.swing.*;
import main.java.javase.t52.core.TexasHoldem;

public class GamePlayPanel extends JPanel {
    public GamePlayPanel() {
        add(new JLabel("Game Play Panel"));
    }

    /**
     * Updates the UI components based on the state of the model.
     * @param model The TexasHoldem model.
     */
    public void updateGameState(TexasHoldem model) {
        // For testing, simply update the panel with a new label.
        removeAll();
        add(new JLabel("Game updated. Current bet: " + model.getCurrentBet()));
        revalidate();
        repaint();
    }
}
