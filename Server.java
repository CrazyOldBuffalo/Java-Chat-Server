
import java.net.ServerSocket;
import java.net.*;
import java.io.*;
import org.json.simple.*;  // required for JSON encoding and decoding

public class Server {
    private static int port = 12345;

    public static void main(String[] args) {
        BuildServer();
    }

    private static void BuildServer() {
        try {
            ServerSocket serverSocket = ServerSocketBuilder();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected Successfully");
                new ClientHandler(clientSocket, "client").start();
            }
        } 
        catch (IOException serverIOException) {
            System.err.println("Failed to Setup Server, Exiting");
            System.exit(1);
        }
    }

    private static ServerSocket ServerSocketBuilder() throws IOException {
        return new ServerSocket(port);
    }
}