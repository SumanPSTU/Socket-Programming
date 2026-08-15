package concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String clientMessage;

            // Keep listening to this specific client until they disconnect or type "exit"
            while ((clientMessage = reader.readLine()) != null) {
                if (clientMessage.equalsIgnoreCase("exit")) {
                    writer.println("Connection closed by server.");
                    break;
                }


                System.out.println("Received from " + socket.getInetAddress() + ": " + clientMessage);

                // Echo back or respond
                writer.println("Server processed: " + clientMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("Client disconnected: " + socket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}