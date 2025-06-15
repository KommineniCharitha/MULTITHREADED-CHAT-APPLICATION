package pkg2;

import java.io.*;
import java.net.*;

public class ChatClient {

    private static final String SERVER_IP = "localhost"; // or your server's IP
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to the chat server!");

            // Setup I/O
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

            // Thread to read messages from the server
            Thread readThread = new Thread(() -> {
                String msgFromServer;
                try {
                    while ((msgFromServer = serverIn.readLine()) != null) {
                        System.out.println("Server: " + msgFromServer);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            readThread.start();

            // Main thread: send messages to server
            String userMsg;
            while ((userMsg = userIn.readLine()) != null) {
                serverOut.println(userMsg);
            }

        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}

