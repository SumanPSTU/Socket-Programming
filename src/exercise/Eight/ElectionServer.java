package exercise.Eight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ElectionServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5002);
        System.out.println("Election Server started. Waiting for 5 electorates...");

        int totalVoters = 5;
        int countA = 0;
        int countB = 0;
        PrintWriter[] clientOuts = new PrintWriter[totalVoters];

        for (int i = 0; i < totalVoters; i++) {
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientOuts[i] = new PrintWriter(socket.getOutputStream(), true);

            String vote = in.readLine().trim().toUpperCase();
            if (vote.equals("A")) countA++;
            else if (vote.equals("B")) countB++;
            System.out.println("Received vote " + (i + 1) + ": " + vote);
        }

        String winner = (countA > countB) ? "Candidate A" : (countB > countA) ? "Candidate B" : "Tie";
        String result = "Results -> A: " + countA + ", B: " + countB + " | Winner: " + winner;

        for (PrintWriter out : clientOuts) {
            out.println(result);
        }
        System.out.println(result);
        serverSocket.close();
    }
}