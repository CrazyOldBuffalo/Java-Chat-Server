import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{
    private static int port = 12345;
    private static String hostname = "localhost";
    private static String clientName;

    public Client(String clientName) {
        this.port = port;
        this.hostname = hostname;
        this.clientName = clientName;
    }

    public static void main(String[] args) {
        Client client = new Client(ClientUsername());
        clientSetup();
    }

    private static String ClientUsername() {
        Scanner clientUsernameScanner = ClientScannerBuilder();
        System.out.println("Please Enter Your Username:" + "\n");
        String clientName = clientUsernameScanner.nextLine();
        return clientName;
    }
    private static void clientSetup() {
        try {
            Socket clientSocket = clientSocketBuilder();
            PrintWriter clientOutput = clientOutputBuilder(clientSocket);
            BufferedReader clientInput = clientInputBuilder(clientSocket);
            BufferedReader userInput = userInputBuilder();
            HandleInput(clientOutput, clientInput, userInput, clientSocket);
        }
        catch (UnknownHostException clientUHException)
        {
            System.err.println("Error Establishing Connection to Host, Exiting");
            System.exit(1);
        }
        catch (IOException clientIOException)
        {
            System.err.println("Error Occurred With I/O, Exiting");
            System.exit(1);
        }
    }

    private static void HandleInput(PrintWriter clientOutput, BufferedReader clientInput, BufferedReader userInput, Socket clientSocket) throws IOException{
        String message;
        while((message = userInput.readLine()) != null) {
            clientOutput.println(message);
            System.out.println(Client.getClientName() + " Says: " + message);
        }
    }

    private static Scanner ClientScannerBuilder() {
        return new Scanner(System.in);
    }

    private static Socket clientSocketBuilder() throws IOException{
        return new Socket(hostname, port);   
    }

    private static PrintWriter clientOutputBuilder(Socket clientSocket) throws IOException{
        return new PrintWriter(clientSocket.getOutputStream(), true);
    }

    private static BufferedReader clientInputBuilder(Socket clientSocket) throws IOException {
        return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private static BufferedReader userInputBuilder() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public static String getClientName() {
        return clientName;
    }
    
} 