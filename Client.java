import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String hostName = "localhost";
    private int port = 12345;


    public static void main(String[] args) {
        try {
            Client client = new Client();
            Socket clientSocket = client.ClientSocketBuilder();
            PrintWriter clientOutput = client.ClientPrintWriterBuilder(clientSocket);
            Scanner clientInput = client.ClientScannerBuilder(clientSocket);
            BufferedReader clientStdIn = client.ClientBufferedReaderBuilder();
            client.WelcomeMessage();
            client.HandleInput(clientOutput, clientInput, clientStdIn);
        }
        catch (UnknownHostException clientUnknownHostException) {
            System.err.println("Unable to find Host, Exiting");
            System.exit(1);
        } 
        catch (IOException clientIOException) {
            System.err.println("Failed to Setup IO, Exiting");
            System.exit(1);
        } 
        catch (NoSuchElementException clientNSElementException) {
            System.err.println("Connection to Server has Been Closed");
            System.exit(1);
        }
    }

    private void WelcomeMessage() {
        System.out.println("Welcome to the Server, Before Starting Set Your Name with the Command: " + "name " + "Followed by you username");
        System.out.println("For More Commands & Their Usage type: help");
    }

    private void HandleInput(PrintWriter clientOutput, Scanner clientInput, BufferedReader clientStdIn)
            throws IOException {
        String userInput;
        while ((userInput = clientStdIn.readLine()) != null) {
            clientOutput.println(userInput);
            int n = clientInput.nextInt();
            clientInput.nextLine();
            for (int i = 0; i < n; i++) {
                System.out.println(clientInput.nextLine());
            }
        }   
    }

    private BufferedReader ClientBufferedReaderBuilder() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    private Scanner ClientScannerBuilder(Socket clientSocket) throws IOException {
        return new Scanner(clientSocket.getInputStream());
    }

    private Socket ClientSocketBuilder() throws IOException {
        return new Socket(hostName, port);
    }

    private PrintWriter ClientPrintWriterBuilder(Socket clientSocket) throws IOException {
        return new PrintWriter(clientSocket.getOutputStream(), true);
    }
}