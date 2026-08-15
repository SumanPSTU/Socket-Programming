package exercise.One;

import java.io.*;
import java.net.*;

public class TCPServer {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("--- Connection-Oriented (TCP) FTP Server Started ---");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                FileOutputStream fos = new FileOutputStream("received_tcp_file.txt")
        ) {
            // Read filename length and filename
            String fileName = dis.readUTF();
            System.out.println("Receiving file: " + fileName);

            long totalBytesReceived = 0;
            while (true) {
                int packetSize = dis.readInt();
                if (packetSize == -1) {
                    break;
                }

                byte[] buffer = new byte[packetSize];
                dis.readFully(buffer);

                // Write to file
                fos.write(buffer);
                totalBytesReceived += packetSize;

                // Send acknowledgment back to client
                out.println("ACK");
            }

            System.out.println("File transfer completed successfully. Total bytes: " + totalBytesReceived);
        } catch (IOException e) {
            System.out.println("Connection error with client: " + e.getMessage());
        }
    }
}