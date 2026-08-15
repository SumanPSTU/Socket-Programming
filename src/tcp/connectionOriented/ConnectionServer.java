package tcp.connectionOriented;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionServer {
    public static void main(String[] args){
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("TCP server running!!!");
            Socket socket = serverSocket.accept();
            if (socket.isConnected() ) System.out.println("Client connedted!!!");
            OutputStream outputStream = socket.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String data = reader.readLine();
            if (data != null && !data.isEmpty()){
                System.out.println("From Client: "+data);
            }
            String message = "Received from Server\n";
            outputStream.write(message.getBytes());
            outputStream.flush();
            System.out.println("server send!!!");


        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
