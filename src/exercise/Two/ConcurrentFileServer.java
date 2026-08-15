package exercise.Two;

import java.io.*;
import java.net.*;

public class ConcurrentFileServer {
    private static final int PORT = 7000;

    public static void main(String[] args) {
        System.out.println("Concurrent File Server Started on Port " + PORT );
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Accept incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Read the requested filename from the client
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String fileName = reader.readLine();

                if (fileName != null && !fileName.trim().isEmpty()) {
                    System.out.println("Client requested file: " + fileName);

                    // Spawn a thread passing the filename and client socket as arguments
                    new FileTransferThread(clientSocket, fileName.trim()).start();
                } else {
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    // Dedicated thread for handling file transfer to a specific client
    private static class FileTransferThread extends Thread {
        private final Socket clientSocket;
        private final String fileName;

        public FileTransferThread(Socket clientSocket, String fileName) {
            this.clientSocket = clientSocket;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            File file = new File(fileName);
            try (
                    OutputStream out = clientSocket.getOutputStream();
                    PrintWriter writer = new PrintWriter(out, true);
                    FileInputStream fis = new FileInputStream(file)
            ) {
                if (!file.exists() || !file.isFile()) {
                    writer.println("ERROR: File not found on server.");
                    return;
                }


                writer.println("SUCCESS");

                byte[] buffer = new byte[1000];
                int bytesRead;

                System.out.println("[" + getName() + "] Starting transfer for: " + fileName);

                while ((bytesRead = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    out.flush();


                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }

                System.out.println("[" + getName() + "] File transfer completed for: " + fileName);

            } catch (FileNotFoundException e) {
                try {
                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    writer.println("ERROR: File not found.");
                } catch (IOException ignored) {}
            } catch (IOException e) {
                System.err.println("[" + getName() + "] Error during transfer: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {

                }
            }
        }
    }
}