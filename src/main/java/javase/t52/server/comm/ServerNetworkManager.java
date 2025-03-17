package main.java.javase.t52.server.comm;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * ServerNetworkManager is responsible for accepting client connections,
 * creating ClientHandler threads for each connection, and broadcasting messages.
 */
public class ServerNetworkManager {

    private int port;
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;
    private boolean isRunning;

    public ServerNetworkManager(int port) {
        this.port = port;
        this.clientHandlers = new ArrayList<>();
    }

    /**
     * Starts the server and begins accepting client connections.
     */
    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
            System.out.println("Server started on port " + port);

            // Start a thread that continuously accepts new connections
            new Thread(() -> acceptConnections()).start();
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Continuously accepts client connections and spawns a new ClientHandler for each.
     */
    private void acceptConnections() {
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket, this);
                clientHandlers.add(handler);

                // Start the ClientHandler in its own thread
                new Thread(handler).start();
            } catch (IOException e) {
                if(isRunning) { // Only log error if the server is still running
                    System.err.println("Error accepting connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Broadcasts a message to all connected clients.
     * @param message The message to broadcast.
     */
    public void broadcastMessage(String message) {
        for (ClientHandler handler : clientHandlers) {
            handler.sendMessage(message);
        }
    }

    /**
     * Stops the server and closes all client connections.
     */
    public void stopServer() {
        isRunning = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            for (ClientHandler handler : clientHandlers) {
                handler.closeConnection();
            }
            System.out.println("Server stopped.");
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}