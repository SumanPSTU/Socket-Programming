package exercise.Two;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FileClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 7000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file you want to download from server: ");
        String fileName = scanner.nextLine();

        try (
                Socket socket = new Socket(SERVER_IP, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                InputStream sockIn = socket.getInputStream();
                FileOutputStream fos = new FileOutputStream("downloaded_" + fileName)
        ) {
            // Send the requested filename to the server
            out.println(fileName);

            // Read response status
            String status = in.readLine();
            if (status != null && status.startsWith("ERROR")) {
                System.out.println("Server error: " + status);
                return;
            }

            System.out.println("Receiving file contents...");
            byte[] buffer = new byte[1000];
            int bytesRead;
            long totalBytes = 0;

            // Read data chunks streamed from the server thread
            while ((bytesRead = sockIn.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }

            System.out.println("File downloaded successfully! Total bytes received: " + totalBytes);
            System.out.println("Saved locally as: downloaded_" + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}