package exercise.Seven;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastServer {
    public static void main(String[] args) throws Exception {
        String groupAddress = "230.0.0.1";
        int port = 5000;

        InetAddress group = InetAddress.getByName(groupAddress);
        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);

        new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("\n[Received]: " + msg);
                    System.out.print("You: ");
                }
            } catch (Exception e) {
                // Socket closed or error
            }
        }).start();

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("--- Multicast Chat Started ---");
        while (true) {
            System.out.print("You: ");
            String msg = stdIn.readLine();
            byte[] data = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
            socket.send(packet);
        }
    }
}