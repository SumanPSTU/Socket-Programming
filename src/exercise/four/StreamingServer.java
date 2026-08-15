package exercise.four;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class StreamingServer {
    private static final int PORT = 9876;
    private static final int MIN_CHUNK = 1000;
    private static final int MAX_CHUNK = 2000;

    public static void main(String[] args) {
        System.out.println("[*] Streaming Server started on port " + PORT + "...");

        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            byte[] receiveBuffer = new byte[1024];

            while (true) {
                // Receive request from client
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String fileName = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("Client requested file: " + fileName + " from " + clientAddress + ":" + clientPort);

                File file = new File(fileName);
                if (!file.exists() || !file.isFile()) {
                    String errorMsg = "ERROR: File not found.";
                    byte[] errData = errorMsg.getBytes();
                    DatagramPacket errPacket = new DatagramPacket(errData, errData.length, clientAddress, clientPort);
                    serverSocket.send(errPacket);
                    System.out.println("[-] Error sent: File not found.");
                    continue;
                }

                // Stream the file in random chunks between 1000 and 2000 bytes
                try (FileInputStream fis = new FileInputStream(file)) {
                    Random rand = new Random();
                    byte[] fileBuffer = new byte[MAX_CHUNK];
                    int bytesRead;
                    long totalBytesSent = 0;


                    while ((bytesRead = fis.read(fileBuffer)) != -1) {

                        int chunkSize = rand.nextInt((MAX_CHUNK - MIN_CHUNK) + 1) + MIN_CHUNK;
                        if (chunkSize > bytesRead) {
                            chunkSize = bytesRead;
                        }

                        // Send the datagram packet
                        DatagramPacket sendPacket = new DatagramPacket(fileBuffer, chunkSize, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                        totalBytesSent += chunkSize;

                        Thread.sleep(10);
                    }
                }
                streamFileToClient(serverSocket, file, clientAddress, clientPort);
            }
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static void streamFileToClient(DatagramSocket socket, File file, InetAddress address, int port) {
        try (FileInputStream fis = new FileInputStream(file)) {
            Random rand = new Random();
            long fileSize = file.length();
            long totalSent = 0;

            System.out.println("[-] Starting to stream " + file.getName() + " (" + fileSize + " bytes)...");

            while (totalSent < fileSize) {
                // Random chunk size between 1000 and 2000 bytes
                int targetChunkSize = rand.nextInt((MAX_CHUNK - MIN_CHUNK) + 1) + MIN_CHUNK;

                // Remaining bytes to send
                long remaining = fileSize - totalSent;
                int currentChunkSize = (int) Math.min(targetChunkSize, remaining);

                byte[] buffer = new byte[currentChunkSize];
                int bytesRead = fis.read(buffer, 0, currentChunkSize);
                if (bytesRead == -1) break;

                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, address, port);
                socket.send(packet);
                totalSent += bytesRead;

                // Small delay to emulate stream pacing
                Thread.sleep(15);
            }

            // Send an empty packet or end-of-stream signal if desired, or let client timeout/track size
            // For simplicity, we can send a 0-byte packet as EOF marker
            DatagramPacket eofPacket = new DatagramPacket(new byte[0], 0, address, port);
            socket.send(eofPacket);

            System.out.println("[+] Finished streaming file. Total bytes sent: " + totalSent);

        } catch (Exception e) {
            System.out.println("[-] Error during file streaming: " + e.getMessage());
        }
    }
}