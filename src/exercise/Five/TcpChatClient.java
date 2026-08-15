package exercise.Five;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpChatClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("TCP Chat Client");
        System.out.println("Connecting to server at " + HOST + ":" + PORT + "...");

        try (Socket socket = new Socket(HOST, PORT)) {
            System.out.println("Connected to Server!\nChat Started (Press Ctrl+C to exit)");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("You: ");
                String outgoingMsg = stdIn.readLine();
                out.println(outgoingMsg);

                String incomingMsg = in.readLine();
                if (incomingMsg == null) {
                    System.out.println("Server disconnected.");
                    break;
                }
                System.out.println("Other: " + incomingMsg);
            }
        } catch (Exception e) {
            System.out.println("Could not connect or connection dropped: " + e.getMessage());
        }
    }
}