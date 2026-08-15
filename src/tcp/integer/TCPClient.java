package tcp.integer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        int port = 8080;
        String host = "localhost";
        try {
            Socket socket = new Socket(InetAddress.getByName(host), port);
            if (socket.isConnected()) System.out.println("Connected to server!!!");
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            int data = scanner.nextInt();
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
