package exercise.One;

import java.io.*;
import java.net.*;

public class TCPClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 5000;
    private static final int TIMEOUT_MS = 2000;

    public static void main(String[] args) {
        File fileToSend = new File("sample.txt");
        if (!fileToSend.exists()) {
            System.out.println("Error: 'sample.txt' not found. Please create one to test.");
            return;
        }
        try (
                Socket socket = new Socket(SERVER_IP, PORT);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                FileInputStream fis = new FileInputStream(fileToSend)
        ) {
            socket.setSoTimeout(TIMEOUT_MS);

            // Send filename
            dos.writeUTF(fileToSend.getName());

            byte[] buffer = new byte[100];
            int bytesRead;

            System.out.println("Starting TCP Stop-and-Wait file transfer...");

            while ((bytesRead = fis.read(buffer)) != -1) {
                boolean acknowledged = false;
                int retries = 0;
                constMaxRetries: while (!acknowledged && retries < 5) {
                    try {
                        // Send chunk size followed by chunk data
                        dos.writeInt(bytesRead);
                        dos.write(buffer, 0, bytesRead);
                        dos.flush();


                        String response = in.readLine();
                        if (response != null && response.equals("ACK")) {
                            acknowledged = true;
                        }
                    } catch (SocketTimeoutException e) {
                        retries++;
                        System.out.println("Timeout! Retrying chunk transmission (Attempt " + retries + ")...");
                    }
                }

                if (!acknowledged) {
                    System.out.println("Max retries reached. Connection lost.");
                    return;
                }
            }


            dos.writeInt(-1);
            dos.flush();
            System.out.println("File sent successfully via TCP.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}