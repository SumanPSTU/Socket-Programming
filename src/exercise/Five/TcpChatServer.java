package exercise.Five;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpChatServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("TCP Chat Server ");
        System.out.println("Waiting for a client to connect on port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept()) {

            System.out.println("Client connected successfully!\n--- Chat Started (Press Ctrl+C to exit) ---");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String incomingMsg;
            while ((incomingMsg = in.readLine()) != null) {
                System.out.println("Other: " + incomingMsg);

                System.out.print("You: ");
                String outgoingMsg = stdIn.readLine();
                out.println(outgoingMsg);
            }
        } catch (Exception e) {
            System.out.println("Connection closed or error: " + e.getMessage());
        }
    }
}