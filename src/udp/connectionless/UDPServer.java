package udp.connectionless;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args){
        int port = 8080;
        try {
            DatagramSocket socket = new DatagramSocket(port);
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);
            String receiveData = new String(packet.getData(),0,packet.getLength());
            if (!receiveData.isEmpty()){
                System.out.println("From Client: "+receiveData);
            }
        }catch (Exception e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
    }
}
