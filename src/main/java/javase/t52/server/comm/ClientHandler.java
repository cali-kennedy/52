package main.java.javase.t52.server.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler is a helper class used by the ServerNetworkManager to handle
 * communication with a single client. It listens for incoming messages from the
 * client, processes them, and sends back responses as needed.
 */
public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private ServerNetworkManager serverNetworkManager; // Reference to the server manager if needed.
    private BufferedReader reader;
    private PrintWriter writer;

    /**
     * Constructs a new ClientHandler with the given client socket and a reference
     * to the ServerNetworkManager.
     *
     * @param clientSocket the socket connected to the client
     * @param serverNetworkManager a reference to the server network manager
     */
    public ClientHandler(Socket clientSocket, ServerNetworkManager serverNetworkManager) {
        this.clientSocket = clientSocket;
        this.serverNetworkManager = serverNetworkManager;
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.err.println("Error initializing ClientHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Listens for incoming requests from the client, processes them, and sends
     * appropriate responses back.
     */
    @Override
    public void run() {
        String requestLine;
        try {
            while ((requestLine = reader.readLine()) != null) {
                // Log received request
                System.out.println("Received from client: " + requestLine);

                // Process the request. In a complete implementation, this might involve
                // parsing a JSON or XML message and then calling specific methods in the model.
                String response = processRequest(requestLine);

                // Send response back to the client
                sendMessage(response);
            }
        } catch (IOException e) {
            System.err.println("Error in client communication: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Processes the client's request.
     * In a complete implementation, this method would parse the request, interact
     * with the core game logic (e.g., TexasHoldem, GameLobby), and generate an appropriate response.
     *
     * @param request the request string received from the client
     * @return the response string to be sent back to the client
     */
    private String processRequest(String request) {
        // Example: Check request type and process accordingly
        // For now, we simply echo the request back.
        // In a real scenario, you might do:
        // if(request.startsWith("LOGIN")) { ... }
        // else if(request.startsWith("BET")) { ... }
        return "Echo: " + request;
    }

    /**
     * Sends a message to the client.
     *
     * @param message the message to send.
     */
    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
            System.out.println("Sent to client: " + message);
        }
    }

    /**
     * Closes the connection with the client.
     */
    public void closeConnection() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                System.out.println("Client connection closed.");
            }
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
