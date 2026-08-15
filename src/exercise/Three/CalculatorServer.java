package exercise.Three;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {
    private static final int PORT = 65432;

    public static void main(String[] args) {
        System.out.println("[*] Calculator Server is listening on port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Accept incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("[+] Connected by client: " + clientSocket.getRemoteSocketAddress());

                try (
                        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    String data = reader.readLine();
                    if (data == null || data.isEmpty()) {
                        continue;
                    }

                    String[] parts = data.split(",");
                    String response;

                    if (parts.length != 3) {
                        response = "Error: Invalid input format sent to server.";
                    } else {
                        try {
                            int num1 = Integer.parseInt(parts[0].trim());
                            int num2 = Integer.parseInt(parts[1].trim());
                            String operator = parts[2].trim();

                            System.out.println("Processing: " + num1 + " " + operator + " " + num2);
                            response = calculate(num1, num2, operator);
                        } catch (NumberFormatException e) {
                            response = "Error: Invalid integer operands.";
                        }
                    }

                    // Send the result back to the client
                    writer.println(response);
                } catch (Exception e) {
                    System.out.println("[-] Error handling client: " + e.getMessage());
                } finally {
                    clientSocket.close();
                    System.out.println("[+] Connection closed.\n");
                }
            }
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }

    private static String calculate(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return String.valueOf(num1 + num2);
            case "-":
                return String.valueOf(num1 - num2);
            case "*":
                return String.valueOf(num1 * num2);
            case "/":
                if (num2 == 0) {
                    return "Error: Division by zero is not allowed.";
                }
                return String.valueOf((double) num1 / num2);
            case "%":
                if (num2 == 0) {
                    return "Error: Modulo by zero is not allowed.";
                }
                return String.valueOf(num1 % num2);
            default:
                return "Error: Invalid operator.";
        }
    }
}