package udp.bothDirection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        int port = 8080;
        String host = "localhost";
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(host);
            byte[] sendBuffer = new byte[1024];
            String message;
            System.out.printf("Enter message to sent: ");
            Scanner scanner = new Scanner(System.in);
            message = scanner.nextLine();
            sendBuffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(sendBuffer,sendBuffer.length,address,port);
            socket.send(packet);
            System.out.println("Packet send!!!");
        }catch (Exception e){
            System.out.println("");
        }
    }
}
