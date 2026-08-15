package tcp.integer;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            if (socket.isConnected()) System.out.println("Connected!");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            String data = String.valueOf(inputStream.read());
            if (data != null && !data.isEmpty()){
                System.out.println("From Client: "+data);
            }
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
