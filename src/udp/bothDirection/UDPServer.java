package udp.bothDirection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) {
        int port = 8080;
        try {
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("UDP Server is running!!!");
            byte[] recBuffer = new byte[1024];
            DatagramPacket recPacket = new DatagramPacket(recBuffer,recBuffer.length);
            socket.receive(recPacket);
            String resData = new String(recPacket.getData(),0,recPacket.getLength());
            System.out.println("From Client: "+resData);

            byte[] sendBuffer = new byte[1024];
            String message = "Receivied From UDP server";
            sendBuffer = message.getBytes();
            InetAddress address = recPacket.getAddress();
            int clientPort = recPacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer,sendBuffer.length,address,clientPort);
            socket.send(sendPacket);
            System.out.println("Server send Data!!!");

        }catch (Exception e){
            System.out.println("Something went wrong!!!");
            e.printStackTrace();
        }
    }
}
