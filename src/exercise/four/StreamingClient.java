package exercise.four;

import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class StreamingClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 9876;
    private static final int BUFFER_THRESHOLD_BYTES = 10 * 1024; // 10 KB threshold to launch media player

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Multimedia Streaming Client");
        System.out.print("Enter media file name to request (e.g., sample.mp4 / song.mp3): ");
        String fileName = scanner.nextLine().trim();
        scanner.close();

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            clientSocket.setSoTimeout(5000); // 5-second timeout for initial response

            InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);
            byte[] sendData = fileName.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);

            System.out.println("[-] Requesting file from server...");
            clientSocket.send(sendPacket);

            // Prepare local output file to save and play from
            File outputFile = new File("downloaded_" + fileName);
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] receiveBuffer = new byte[2000];
                long totalBytesReceived = 0;
                boolean playerLaunched = false;

                System.out.println("Receiving stream...");

                while (true) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                    try {
                        clientSocket.receive(receivePacket);
                    } catch (java.net.SocketTimeoutException e) {
                        System.out.println("Stream finished or timed out.");
                        break;
                    }

                    int packetLength = receivePacket.getLength();


                    if (packetLength == 0) {
                        System.out.println("End of stream signal received.");
                        break;
                    }

                    // Check if server sent an error message
                    String msg = new String(receivePacket.getData(), 0, packetLength);
                    if (msg.startsWith("ERROR:")) {
                        System.out.println("[Server Error]: " + msg);
                        return;
                    }

                    fos.write(receivePacket.getData(), 0, packetLength);
                    totalBytesReceived += packetLength;
                    System.out.print("\rDownloaded bytes: " + totalBytesReceived);

                    if (!playerLaunched && totalBytesReceived >= BUFFER_THRESHOLD_BYTES) {
                        System.out.println("\nReasonable buffer reached (" + totalBytesReceived + " bytes). Launching media player...");
                        launchMediaPlayer(outputFile.getAbsolutePath());
                        playerLaunched = true;
                    }
                }

                System.out.println("\nStreaming complete. File saved as: " + outputFile.getAbsolutePath());

                if (!playerLaunched && outputFile.exists()) {
                    launchMediaPlayer(outputFile.getAbsolutePath());
                }

            }

        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }

    private static void launchMediaPlayer(String filePath) {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;

            if (os.contains("win")) {
                pb = new ProcessBuilder("cmd.exe", "/c", filePath);
            } else if (os.contains("mac")) {
                // macOS default player
                pb = new ProcessBuilder("open", filePath);
            } else {
                pb = new ProcessBuilder("xdg-open", filePath);
            }
            pb.start();
        } catch (Exception e) {
            System.out.println("[-] Could not automatically launch media player: " + e.getMessage());
        }
    }
}