import java.net.UnknownHostException;
import java.util.Vector;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class Server {

    static Vector<ClientHandler> clientHandlerVector = ClientHandlerVectorBuilder();
    public static void main(String[] args) {
        int port = 12345;
        Socket clientSocket = null;
        try
        {
            ServerSocket serverSocket = ServerBuilder(port);
            CheckConnections(serverSocket, clientSocket);
        }
        catch (IOException serverSocketIOE)
        {
            System.err.println("Failed to Listen On " + port);
            System.exit(1);
        }
    }

    private static Vector<ClientHandler> ClientHandlerVectorBuilder() {
        return new Vector<ClientHandler>();
    }

    private static void CheckConnections(ServerSocket serverSocket, Socket clientSocket) throws IOException{
        while (true)
        {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Client Connected");
                DataInputStream inputStream = DataInputBuilder(clientSocket);
                DataOutputStream outputStream = DataOutputBuilder(clientSocket);
                ClientHandler ServerClientHandler = new ClientHandler(clientSocket, inputStream, outputStream);
                Thread clientThread = ClientThreadHandler();
            }
            catch (IOException cIoException)
            {
                System.err.println("Connection to Server Failed, Exiting");
                System.exit(1);
            }
        }
    }


    private static Thread ClientThreadHandler() {
        return null;
    }

    private static DataOutputStream DataOutputBuilder(Socket clientSocket) throws IOException {
        return new DataOutputStream(clientSocket.getOutputStream());
    }

    private static DataInputStream DataInputBuilder(Socket clientSocket) throws IOException {
        return new DataInputStream(clientSocket.getInputStream());
    }

    // Private Methods for Building and returning the server socket
    private static ServerSocket ServerBuilder(int port) throws IOException{
        return new ServerSocket(port);
    }
}
