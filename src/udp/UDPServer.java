package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer {
    public static void main(String[] args) {
        int port = 8080;
        try {
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("UDP server is running");
            byte[] recBuffer = new byte[1024];
            DatagramPacket recPacket = new DatagramPacket(recBuffer,recBuffer.length);
            socket.receive(recPacket);
            String receiveData = new String(recPacket.getData(),0,recPacket.getLength());
            System.out.println("From Client: "+receiveData);

            InetAddress clientAddress = recPacket.getAddress();
            int clintPort = recPacket.getPort();
            String serverMsg = "Received from server!!!";
            byte[] resBuffer = new byte[1024];
            resBuffer = serverMsg.getBytes();
            DatagramPacket resPacket = new DatagramPacket(resBuffer,resBuffer.length,clientAddress,clintPort);
            socket.send(resPacket);
            System.out.println("Server send Data");
            socket.close();
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}