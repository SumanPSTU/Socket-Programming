package concurrent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConcorrentServer {
    public static void main(String[] args) {
        int port = 8080;

        // Use try-with-resources to automatically close the server socket when done
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server running on port " + port + "...");

            // The while(true) loop must wrap around serverSocket.accept()
            // so it can continuously accept multiple incoming clients!
            while (true) {
                Socket socket = serverSocket.accept(); // Blocks until a new client connects

                if (socket.isConnected()) {
                    System.out.println("Client connected: " + socket.getInetAddress().getCanonicalHostName());
                }

                // Spawn a new thread for each individual client connection
                new Thread(new ClientHandler(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}