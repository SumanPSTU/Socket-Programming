package exercise.Three;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 65432;

    public static void main(String[] args) {
        System.out.println("Remote Calculator Client");
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter first integer: ");
            int num1 = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter second integer: ");
            int num2 = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter operation ('+', '-', '*', '/', '%'): ");
            String operator = scanner.nextLine().trim();

            if (!operator.matches("[+\\-*/%]")) {
                System.out.println("Invalid operator! Please choose from '+', '-', '*', '/', '%'.");
                return;
            }

            // Format message as "num1,num2,operator"
            String message = num1 + "," + num2 + "," + operator;


            try (Socket socket = new Socket(HOST, PORT);
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                writer.println(message);
                String result = reader.readLine();
                System.out.println("\n[Server Result]: " + num1 + " " + operator + " " + num2 + " = " + result);
            } catch (Exception e) {
                System.out.println("Error: Could not connect to the server. Make sure the server is running.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter valid integers.");
        } finally {
            scanner.close();
        }
    }
}