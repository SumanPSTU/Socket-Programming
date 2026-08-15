package udp.connectionless;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args){
        int port = 8080;
        String host = "localhost";
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(host);
            String message = "Hello UDP server";
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length,address,port);
            socket.send(packet);
            socket.close();
        }catch (Exception e){
            System.out.println("Something went wrong!!");
            e.printStackTrace();
        }
    }
}
