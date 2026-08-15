package tcp.connectionOriented;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server! Type 'exit' to quit.");

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                // Send to server
                writer.println(message);

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                // Read response from server
                String response = reader.readLine();
                if (response != null) {
                    System.out.println("From Server: " + response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}