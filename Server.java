import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        try
        {
            ServerSocket serverSocket = ServerBuilder(port);
            CheckConnections(serverSocket);
        }
        catch (IOException serverSocketIOE)
        {
            System.err.println("Failed to Listen On " + port);
            System.exit(1);
        }
    }

    private static ServerSocket ServerBuilder(int port) throws IOException{
        return new ServerSocket(port);
    }

    private static void CheckConnections(ServerSocket serverSocket) throws IOException{
        while (true)
        {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected");
            }
            catch (IOException cIoException)
            {
                System.err.println("Connection to Server Failed, Exiting");
                System.exit(1);
            }
        }
    }
}
