package exercise.Six;

import java.io.*;
import java.net.*;

public class MultiTcpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Multi-Client TCP Server started on port 5000...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("[+] New client connected: " + socket.getRemoteSocketAddress());
            new ClientHandler(socket).start();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("[" + socket.getRemoteSocketAddress() + "] Other: " + msg);
                System.out.print("Server Reply to [" + socket.getRemoteSocketAddress() + "]: ");
                out.println(stdIn.readLine());
            }
        } catch (Exception e) {
            System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
        }
    }
}