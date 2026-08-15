package exercise.Six;

import java.io.*;
import java.net.*;

public class TcpChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("You: ");
            out.println(stdIn.readLine());
            String msg = in.readLine();
            if (msg == null) break;
            System.out.println("Other: " + msg);
        }
    }
}