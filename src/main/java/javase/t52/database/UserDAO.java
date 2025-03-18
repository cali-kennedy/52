package main.java.javase.t52.database;

import main.java.javase.t52.core.User;
import java.sql.*;
import java.util.Properties;

public class UserDAO {

    // Use environment variables for demo credentials with fallback defaults.
    private String jdbcUsername =  "DEMO_USER";
    private String jdbcPassword = "Javaproject1!";

    // Construct the JDBC URL using the TCPS protocol.
    // Replace HOST and SERVICE_NAME with your ATP instance values.
    // This URL uses TLS (TCPS) authentication.
    private String jdbcURL = "jdbc:oracle:thin:@(DESCRIPTION="
            + "(ADDRESS=(PROTOCOL=tcps)(HOST=adb.us-phoenix-1.oraclecloud.com)(PORT=1522))"
            + "(CONNECT_DATA=(SERVICE_NAME=g248fd62db01ca7_texasholdem_tp.adb.oraclecloud.com))"
            + "(SECURITY=(SSL_SERVER_DN_MATCH=yes)))";

    /**
     * Constructor loads the Oracle JDBC driver.
     */
    public UserDAO() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Oracle JDBC Driver loaded successfully!");
            System.out.println("JDBC URL: " + jdbcURL);
        } catch (ClassNotFoundException e) {
            System.err.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        }

    }

    /**
     * Establishes a connection to the Oracle ATP database using TLS.
     *
     * @return a Connection object.
     * @throws SQLException if a database access error occurs.
     */
    private Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", jdbcUsername);
        props.setProperty("password", jdbcPassword);
        // Enable SSL/TLS features for a secure connection.
        props.setProperty("oracle.net.ssl_server_dn_match", "true");
        // Option 1: Remove the explicit SSL version property and let the driver negotiate.
        // Option 2: Alternatively, try setting a different version (e.g., "TLSv1") if needed:
        // props.setProperty("oracle.net.ssl_version", "TLSv1");

        return DriverManager.getConnection(jdbcURL, props);
    }

    /**
     * Inserts a new user into the USERS table.
     *
     * @param user the User object containing user data.
     * @return true if the insertion was successful; false otherwise.
     */
    public boolean createUser(User user) {
        System.out.println("In UserDAO.java: createUser...");
        String sql = "INSERT INTO ADMIN.USERS (USERNAME, PASSWORD, CHIP_COUNT) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword()); // Consider hashing for production.
            statement.setInt(3, user.getChipCount());

            int rowsInserted = statement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
            if (rowsInserted >= 1) {
                System.out.println("Inserted: " + user.getUsername() + " "
                        + user.getPassword() + " " + user.getChipCount());
            }
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves a User from the USERS table by username.
     *
     * @param username the username to search for.
     * @return the User object if found; null otherwise.
     */
    public User readUser(String username) {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String password = rs.getString("PASSWORD");
                int chipCount = rs.getInt("CHIP_COUNT");
                return new User(username, password, chipCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an existing user in the USERS table.
     *
     * @param user the User object containing updated data.
     * @return true if the update was successful; false otherwise.
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE USERS SET PASSWORD = ?, CHIP_COUNT = ? WHERE USERNAME = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getChipCount());
            statement.setString(3, user.getUsername());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user from the USERS table.
     *
     * @param user the User object representing the user to delete.
     * @return true if the deletion was successful; false otherwise.
     */
    public boolean deleteUser(User user) {
        String sql = "DELETE FROM USERS WHERE USERNAME = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
