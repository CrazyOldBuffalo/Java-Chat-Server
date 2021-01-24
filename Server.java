import java.util.Vector;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Server {

    static Vector<ClientHandler> clientHandlerVector = ClientHandlerVectorBuilder();
    static int i = 0;
    public static void main(String[] args) {
        int port = 12345;
        Socket clientSocket = null;
        while (true) {
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
                ClientHandler serverClientHandler = ClientHandlerBuilder(clientSocket, i, inputStream, outputStream);
                Thread clientThread = new Thread(serverClientHandler);
                clientHandlerVector.add(serverClientHandler);
                clientThread.start();
                i++;
            }
            catch (IOException cIoException)
            {
                System.err.println("Connection to Server Failed, Exiting");
                System.exit(1);
            }
        }
    }
    
    private static ClientHandler ClientHandlerBuilder(Socket clientSocket, int i, DataInputStream inputStream, DataOutputStream outputStream)
    {
        return new ClientHandler(clientSocket, "Client" + i, inputStream, outputStream);
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
