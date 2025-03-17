package main.java.javase.t52.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import main.java.javase.t52.client.ui.GameUIController;

public class LoginPanel extends JPanel {

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JLabel feedbackLabel;

    // Reference to the controller to forward user actions
    private GameUIController controller;

    public LoginPanel() {
        initComponents();
        layoutComponents();
        attachListeners();
    }

    /**
     * Initialize UI components.
     */
    private void initComponents() {
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new JButton("Login");
        feedbackLabel = new JLabel(" ");
    }

    /**
     * Arrange the components using GridBagLayout.
     */
    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username label and field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(userField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passField, gbc);

        // Login button spanning two columns
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // Feedback label spanning two columns
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(feedbackLabel, gbc);
    }

    /**
     * Attach the action listener to the login button.
     */
    private void attachListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    String username = userField.getText();
                    String password = new String(passField.getPassword());
                    controller.handleLogin(username, password);
                } else {
                    setFeedback("Controller not set.");
                }
            }
        });
    }

    /**
     * Inject the GameUIController into this panel.
     *
     * @param controller the GameUIController instance.
     */
    public void setController(GameUIController controller) {
        this.controller = controller;
    }

    /**
     * Sets the feedback message to be displayed on the panel.
     *
     * @param message the message to display.
     */
    public void setFeedback(String message) {
        feedbackLabel.setText(message);
    }
}
