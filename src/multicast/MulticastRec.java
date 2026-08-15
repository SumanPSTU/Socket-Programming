package multicast;

import java.net.*;

public class MulticastRec{
    public static void main(String[] args) {
        String address = "230.0.0.1";
        int port = 4446;
        try {
            MulticastSocket socket = new MulticastSocket(port);
            InetAddress group = InetAddress.getByName(address);
            InetSocketAddress groupAddress = new InetSocketAddress(group,port);

            NetworkInterface netIF = NetworkInterface.getByName("wlan0");
            socket.joinGroup(groupAddress,netIF);
            System.out.println("Receiver join to multicast group");
            byte[] buffer = new byte[256];
            while (true){
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(),0,packet.getLength());
                System.out.println("Received: "+message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
