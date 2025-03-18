package main.java.javase.t52.core;

import main.java.javase.t52.database.UserDAO;

/**
 * UserManager provides higher-level methods for user authentication and management.
 * It uses UserDAO to interact with the database for CRUD operations on users.
 */
public class UserManager {

    private UserDAO userDAO;

    /**
     * Constructs a new UserManager and initializes the underlying UserDAO.
     */
    public UserManager() {
        userDAO = new UserDAO();
    }

    /**
     * Validates a user's login credentials.
     *
     * @param user a User object containing the username and password provided by the user.
     * @return true if the provided credentials match those in the database; false otherwise.
     */
    public boolean validateLogin(User user) {
        // Retrieve the user from the database based on the username.
        User dbUser = userDAO.readUser(user.getUsername());
        if (dbUser != null) {
            // For demonstration purposes, a plain text comparison is used.
            // In a production system, ensure passwords are hashed and salted.
            return dbUser.getPassword().equals(user.getPassword());
        }
        return false;
    }

    /**
     * Registers a new user in the system.
     *
     * @param user a User object containing the new user's information.
     * @return true if the registration was successful; false otherwise.
     */
    public boolean registerUser(User user) {
        // Use the UserDAO to create a new user in the database.
        return userDAO.createUser(user);
    }

    /**
     * Logs out the user.
     *
     * @param user a User object representing the user to log out.
     * @return true if logout is successful; false otherwise.
     *
     * Note: Since logout is typically a client-side operation (e.g., clearing session data),
     * this method may simply perform necessary cleanup and return true.
     */
    public boolean logoutUser(User user) {
        // For demonstration, simply return true.
        // In a full application, additional cleanup may be necessary.
        return true;
    }
}
