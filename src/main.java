import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class main {
    public static void main(String[] args) {
        String host = "www.google.com";
        int port = 80;
        try {
            Socket socket = new Socket(host,port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("GET / HTTP/1.1");
            out.println("Host: "+host);
            out.println("Connection: close");
            out.println();
            String line;
            System.out.println("________Reading Data from Google________");
            while ((line = in.readLine())!=null){
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
