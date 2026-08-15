package exercise.Eight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ElectionClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 5002);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.print("Enter your vote ('A' or 'B'): ");
        String vote = stdIn.readLine().trim().toUpperCase();
        out.println(vote);

        String result = in.readLine();
        System.out.println(result);

        socket.close();
    }
}