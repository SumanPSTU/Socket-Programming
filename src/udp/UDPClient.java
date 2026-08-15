package udp;

import java.net.*;

public class UDPClient {
    public static void main(String[] args){
        int port = 8080;
        String host = "localhost";
        try {
            DatagramSocket socket = new DatagramSocket();
            String data = "Hello from Client!!!";
            InetAddress address = InetAddress.getByName(host);
            byte[] clientBuffer = new byte[1024];
            clientBuffer = data.getBytes();
            DatagramPacket packet = new DatagramPacket(clientBuffer,clientBuffer.length,address,port);
            socket.send(packet);

            byte[] resBuffer = new byte[1024];

            DatagramPacket resPacket = new DatagramPacket(resBuffer,resBuffer.length);
            socket.receive(resPacket);
            String resData = new String(resPacket.getData(),0,resPacket.getLength());
            System.out.println(resData);
            System.out.println("Client Received Data");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}