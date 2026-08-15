package tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int port = 8080;
        String host = "localhost";

        try {
            Socket socket = new Socket(host, port);
            if (socket.isConnected()) {
                System.out.println("Connected to Server:");
                System.out.println(socket.getInetAddress().getHostName());
            }

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter msg to send! (Type -1 to exit)");

            while (true) {
                System.out.print("Send: ");
                String string = scanner.nextLine();

                // 1. Send the message (including "-1") to the server first
                outputStream.writeObject(string);
                outputStream.flush();

                // 2. If it was "-1", break out of the loop gracefully after sending
                if (string.equals("-1")) {
                    System.out.println("Closing client connection...");
                    break;
                }

                // Read response from server
                Object object = inputStream.readObject();
                if (object != null) {
                    if (object.equals("-1")) {
                        System.out.println("Server requested disconnect.");
                        break;
                    }
                    System.out.println("From server: " + (String) object);
                }
            }

            scanner.close();
            socket.close();
            if (socket.isClosed()) {
                System.out.println("Socket Disconnected!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}