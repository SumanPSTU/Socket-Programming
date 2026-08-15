package tcp.duplex;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args){
        int port = 8080;
        String host = "localhost";
        try {
            Socket socket = new Socket(InetAddress.getByName(host),port);
            if (socket.isConnected()) System.out.println("Connected to server");
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println("Write line:");
                String msg = scanner.nextLine();
                msg = msg.concat("\n");
                if (msg.equals("-1")) break;
                byte[] buffer = msg.getBytes();
                outputStream.write(buffer);
                outputStream.flush();

                String message = reader.readLine();
                if (!message.isEmpty() && message!= null){
                    System.out.println("From Server: "+message);
                }
            }
            scanner.close();
            socket.close();
            if (socket.isClosed()) System.out.println("Socket close");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
