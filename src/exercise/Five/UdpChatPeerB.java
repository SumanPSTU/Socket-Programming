package exercise.Five;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpChatPeerB {
    private static final int LOCAL_PORT = 6001;
    private static final int REMOTE_PORT = 6000;
    private static final String HOST = "127.0.0.1";
    private static final int MAX_CHARS = 1000;

    public static void main(String[] args) {
        System.out.println("UDP Chat Peer B");
        System.out.println("Chat Started (Press Ctrl+C to exit)");

        try (DatagramSocket socket = new DatagramSocket(LOCAL_PORT)) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            InetAddress ip = InetAddress.getByName(HOST);
            byte[] receiveBuffer = new byte[MAX_CHARS];

            while (true) {
                // 1. Wait for message first
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Other: " + msg);

                // 2. Send reply
                System.out.print("You: ");
                String reply = stdIn.readLine();
                if (reply.length() > MAX_CHARS) {
                    reply = reply.substring(0, MAX_CHARS);
                    System.out.println("Message truncated to 1000 characters limit.");
                }
                byte[] sendData = reply.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, REMOTE_PORT);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            System.out.println("[-] Error: " + e.getMessage());
        }
    }
}