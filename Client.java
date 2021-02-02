import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static String hostName = "localhost";
    private static int port = 12345;

    public static void main(String[] args) {
        ClientBuilder();
    }

    private static void HandleInput(PrintWriter clientOut, BufferedReader clientIn, BufferedReader clientStdIn)
            throws IOException {
        String clientInput;
        while ((clientInput = clientStdIn.readLine()) != null) {
            clientOut.println(clientInput);
            System.out.println("Says: " + clientIn.readLine());
        }
    }

    private static void ClientBuilder() {
        try {
            Socket clientSocket = ClientSocketBuilder();
            PrintWriter clientOut = ClientPrintWriterBuilder(clientSocket);
            BufferedReader clientIn = ClientBufferedReaderBuilder(clientSocket);
            BufferedReader clientStdIn = ClientStdInBuilder();
            HandleInput(clientOut, clientIn, clientStdIn);
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } 
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
            hostName);
            System.exit(1);
        }    
    }

    private static BufferedReader ClientStdInBuilder() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private static BufferedReader ClientBufferedReaderBuilder(Socket clientSocket) throws IOException {
        return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private static PrintWriter ClientPrintWriterBuilder(Socket clientSocket) throws IOException {
        return new PrintWriter(clientSocket.getOutputStream(), true);
    }

    private static Socket ClientSocketBuilder() throws UnknownHostException, IOException {
        return new Socket(hostName, port);
    }
}