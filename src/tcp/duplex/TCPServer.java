package tcp.duplex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {
    public static void main(String[] args){
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            if (serverSocket!= null) System.out.println("TCP server running!!!");
            Socket socket = serverSocket.accept();
            if (socket.isConnected()) System.out.println("TCP Client connected!!!");
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true){
                String data = bufferedReader.readLine();
                if (!data.isEmpty() && data != null){
                    System.out.println("From Client: "+data);
                }
                if (data.equals("-1")){
                    break;
                }
                String string = "Server Received\n";
                byte[] output = string.getBytes();
                outputStream.write(output);
                outputStream.flush();

            }


           socket.close();
            serverSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
