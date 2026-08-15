package tcp.object;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        int port = 8080;
        String host = "localhost";
        try {
            Socket socket = new Socket(InetAddress.getByName(host),port);
            if (socket.isConnected()) System.out.println("Connected to server!!!");
            Scanner scanner = new Scanner(System.in);
            System.out.printf("Enter emp id:");
            int id =  scanner.nextInt();
            scanner.nextLine();
            System.out.printf("Enter emp name:");
            String name = scanner.nextLine();
            System.out.printf("Enter emp Salary:");
            double salary = scanner.nextDouble();

            Employee employee = new Employee(id,name,salary);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(employee);
            outputStream.flush();
            scanner.close();
            socket.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
