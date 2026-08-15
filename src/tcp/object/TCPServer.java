package tcp.object;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args){
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            if (serverSocket!=null){
                System.out.println("Server running!!!");
            }
            Socket socket = serverSocket.accept();
            if (socket.isConnected()) System.out.println("Socket connected");
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Employee employee =(Employee) inputStream.readObject();
            if (employee!=null ){
                System.out.println("Emp id: "+employee.getId());
                System.out.println("Emp name: "+employee.getName());
                System.out.println("Emp Salary: "+employee.getEmpSalary());
            }
            socket.close();
            serverSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
