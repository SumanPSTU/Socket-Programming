package exercise.Five;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpChatPeerA {
    private static final int LOCAL_PORT = 6000;
    private static final int REMOTE_PORT = 6001;
    private static final String HOST = "127.0.0.1";
    private static final int MAX_CHARS = 1000;

    public static void main(String[] args) {
        System.out.println("UDP Chat Peer A");
        System.out.println("Chat Started (Press Ctrl+C to exit)");

        try (DatagramSocket socket = new DatagramSocket(LOCAL_PORT)) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            InetAddress ip = InetAddress.getByName(HOST);
            byte[] receiveBuffer = new byte[MAX_CHARS];

            while (true) {
                // 1. Send message first
                System.out.print("You: ");
                String msg = stdIn.readLine();
                if (msg.length() > MAX_CHARS) {
                    msg = msg.substring(0, MAX_CHARS);
                    System.out.println("(!) Message truncated to 1000 characters limit.");
                }
                byte[] sendData = msg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, REMOTE_PORT);
                socket.send(sendPacket);

                // 2. Wait for reply
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String reply = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Other: " + reply);
            }
        } catch (Exception e) {
            System.out.println("[-] Error: " + e.getMessage());
        }
    }
}