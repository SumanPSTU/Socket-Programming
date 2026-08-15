package multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

public class MulticastSender {
    public static void main(String[] args) {
        String multicastAddress = "230.0.0.1";
        int port = 4446;
        try {
            MulticastSocket socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(multicastAddress);
            String message = "Hello, Everyone! this is a nulticast broadcasr!";
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length,group,port);
            socket.send(packet);
            System.out.println("Multicast message send!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
