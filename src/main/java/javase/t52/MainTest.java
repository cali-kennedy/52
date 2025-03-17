package main.java.javase.t52;

import main.java.javase.t52.core.TexasHoldem;
import main.java.javase.t52.client.ui.GameUIController;
import main.java.javase.t52.client.ui.MainGameFrame;

import javax.swing.SwingUtilities;

public class MainTest  {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the model
            TexasHoldem texasHoldem = new TexasHoldem();
            // Create the view
            MainGameFrame mainFrame = new MainGameFrame();
            // Create the controller
            GameUIController controller = new GameUIController(texasHoldem, mainFrame);

            // Inject controller into view (if needed)
            mainFrame.initUI(controller);
            // Start with the login panel
            mainFrame.setPanel("LoginPanel");
            mainFrame.setVisible(true);
        });
    }
}
