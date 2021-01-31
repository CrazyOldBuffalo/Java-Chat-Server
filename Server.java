import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    // Private Variables for establishing Server, Port which is set to 12345 will ensure the port always opens to this number,
    // The ArrayList allows for each thread to be stored within it and removed when a client disconnects from the server.
    private static int port = 12345;
    private static ArrayList<ClientHandler> clientThreads = new ArrayList<>();


    private static void BuildServer() {
        try {
            ServerSocket serverSocket = ServerSocketBuilder();
            while (true) {
                Socket clientSocket = ClientSocketBuilder(serverSocket);
                ClientHandler client = new ClientHandler(clientSocket, Client.getClientName());
                clientThreads.add(client);
                client.start();
            }
        }
        catch (IOException ServerIOException) {
            System.err.println("Server Failed to Build, Exiting");
            System.exit(1);
        }
    }

    private static Socket ClientSocketBuilder(ServerSocket serverSocket) throws IOException{
        return serverSocket.accept();
    }

    private static ServerSocket ServerSocketBuilder() throws IOException {
        return new ServerSocket(port);
    }

    public static void main(String[] args) {
        BuildServer();
    }
    
}
