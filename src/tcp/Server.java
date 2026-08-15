package tcp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args){
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server running on port: "+port);
            Scanner scanner = new Scanner(System.in);

            Socket socket = serverSocket.accept();
            if (socket.isConnected()) System.out.println("Client is connected!");

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            while (true){
                Object msg = inputStream.readObject();
                if (!msg.equals("-1") && msg != null){
                    String string = new String((String) msg);
                    System.out.println("From client: "+string);
                    System.out.printf("Send: ");
                    String data = scanner.nextLine();
                    Object object = (Object) data;
                    outputStream.writeObject(object);
                    outputStream.flush();
                }
                if (msg.equals("-1")){
                    socket.close();
                    serverSocket.close();
                    scanner.close();
                    break;
                }



            }
            if (serverSocket.isClosed()){
                System.out.println("Server is Closed!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}