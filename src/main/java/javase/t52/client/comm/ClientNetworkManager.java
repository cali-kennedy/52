package main.java.javase.t52.client.comm;



import main.java.javase.t52.client.ui.GameUIController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientNetworkManager manages the network communication on the client side.
 * It connects to the server, sends requests, and listens for responses.
 */
public class ClientNetworkManager {

    private String serverAddress;
    private int port;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private GameUIController controller; // For notifying the controller about responses
    private Thread listenerThread;
    private boolean connected;

    public ClientNetworkManager(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     * Connects to the server and initializes input/output streams.
     */
    public void connect() {
        try {
            socket = new Socket(serverAddress, port);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            connected = true;
            System.out.println("Connected to server at " + serverAddress + ":" + port);

            // Start a thread to listen for server responses
            listenerThread = new Thread(() -> listenForResponses());
            listenerThread.start();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Listens for responses from the server.
     */
    private void listenForResponses() {
        String responseLine;
        try {
            while (connected && (responseLine = reader.readLine()) != null) {
                System.out.println("Received from server: " + responseLine);
                // Notify the controller (if set) to handle the response
                if (controller != null) {
                    controller.handleServerResponse(responseLine);
                }
            }
        } catch (IOException e) {
            if (connected) {
                System.err.println("Error receiving response: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a request to the server.
     * @param request The request string.
     */
    public void sendRequest(String request) {
        if (writer != null) {
            writer.println(request);
            System.out.println("Sent to server: " + request);
        }
    }

    /**
     * Disconnects from the server and stops the listener thread.
     */
    public void disconnect() {
        connected = false;
        try {
            if (socket != null) {
                socket.close();
            }
            if (listenerThread != null) {
                listenerThread.join();
            }
            System.out.println("Disconnected from server.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sets the controller reference so that incoming responses can be handled.
     * @param controller The GameUIController instance.
     */
    public void setController(GameUIController controller) {
        this.controller = controller;
    }
}

