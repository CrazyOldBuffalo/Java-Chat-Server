import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        int port = 12345;
        try {
            Socket clientSocket = ClientSocketBuilder(port);  
        }
        catch (IOException ClientSocketIOE)
        {
            System.err.println("Socket Issue Detected");
            System.exit(1);
        }        
    }

    private static Socket ClientSocketBuilder(int port) throws IOException{
        return new Socket("localhost", port);
    }
}