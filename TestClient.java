import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TestClient {
    private String hostName = "localhost";
    private int port = 12345;

    public static void main(String[] args) {

        System.out.println("Please enter which test to begin?");
        Scanner testClientScanner = new Scanner(System.in);
        String testOption = testClientScanner.next();
        String testName = testClientScanner.skip(" ").next();
        TestClient client = new TestClient();

        if (testOption.equalsIgnoreCase("Low")) {
            try {
                Socket clientSocket = client.ClientSocketBuilder();
                PrintWriter clientOutput = client.ClientPrintWriterBuilder(clientSocket);
                Scanner clientInput = client.ClientScannerBuilder(clientSocket);
                System.out.println("Starting Low Throughput");
                client.LowThroughput(testName, clientOutput, clientInput);
            }
            catch (IOException e) {
                System.err.println("Error");
                System.exit(1);
            }
        }
        else if (testOption.equalsIgnoreCase("High")) {
            try {
                Socket clientSocket = client.ClientSocketBuilder();
                PrintWriter clientOutput = client.ClientPrintWriterBuilder(clientSocket);
                Scanner clientInput = client.ClientScannerBuilder(clientSocket);
                System.out.println("Starting High Throughput");
                client.HighThroughput(testName, clientOutput, clientInput);
            }
            catch (IOException e) {
                System.err.println("Error");
                System.exit(1);
            }
        }
        else if (testOption.equalsIgnoreCase("persistance")) {
            try {
                Socket clientSocket = client.ClientSocketBuilder();
                PrintWriter clientOutput = client.ClientPrintWriterBuilder(clientSocket);
                Scanner clientInput = client.ClientScannerBuilder(clientSocket);
                System.out.println("Starting High Throughput");
                client.Persistance(testName, clientOutput, clientInput);
            }
            catch (IOException e) {
                System.err.println("Error");
                System.exit(1);
            }
        }
        else if (testOption.equalsIgnoreCase("persistsub")) {
            try {
                Socket clientSocket = client.ClientSocketBuilder();
                PrintWriter clientOutput = client.ClientPrintWriterBuilder(clientSocket);
                Scanner clientInput = client.ClientScannerBuilder(clientSocket);
                System.out.println("Starting High Throughput");
                client.Persistsub(testName, clientOutput, clientInput);
            }
            catch (IOException e) {
                System.err.println("Error");
                System.exit(1);
            }
        }
        else if (testOption.equalsIgnoreCase("PersistCheck")) {
            try {
                Socket clientSocket = client.ClientSocketBuilder();
                PrintWriter clientOutput = client.ClientPrintWriterBuilder(clientSocket);
                Scanner clientInput = client.ClientScannerBuilder(clientSocket);
                System.out.println("Starting High Throughput");
                client.PersistCheck(testName, clientOutput, clientInput);
            }
            catch (IOException e) {
                System.err.println("Error");
                System.exit(1);
            }
        }
        else {
            System.out.println("Not valid");
        }
        
    }



    private void Persistsub(String testName, PrintWriter clientOutput, Scanner clientInput) {
        WelcomeMessage();
        String userInput = "";
        userInput = "name " + testName;
        clientOutput.println(userInput);
        int n = clientInput.nextInt();
        clientInput.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println(clientInput.nextLine());
        }
        userInput = "sub test";
        clientOutput.println(userInput);
        int m = clientInput.nextInt();
        clientInput.nextLine();
        for (int j = 0; j < m; j++) {
            System.out.println(clientInput.nextLine());
        }
        for (int k = 0; k < 45; k++){
            userInput = "postto test hello test";
            clientOutput.println(userInput);
            int b = clientInput.nextInt();
            clientInput.nextLine();
            for (int l = 0; l < b; l++) {
                System.out.println(clientInput.nextLine());
            }
        }
    }

    private void WelcomeMessage() {
        System.out.println("Welcome to the Server, Before Starting Set Your Name with the Command: " + "name " + "Followed by you username");
        System.out.println("For More Commands & Their Usage type: help");
    }

    private void HighThroughput(String testName, PrintWriter clientOutput, Scanner clientInput) {
        try {
            WelcomeMessage();
            String userInput = "";
            userInput = "name " + testName;
            clientOutput.println(userInput);
            int n = clientInput.nextInt();
            clientInput.nextLine();
            for (int i = 0; i < n; i++) {
                System.out.println(clientInput.nextLine());
            }
            for (int i = 0; i < 100; i++){
                userInput = "post hello";
                clientOutput.println(userInput);
                int q = clientInput.nextInt();
                clientInput.nextLine();
                for (int j = 0; j < q; j++) {
                    System.out.println(clientInput.nextLine());
                }
            }
            userInput = "read";
            clientOutput.println(userInput);
                int m = clientInput.nextInt();
                clientInput.nextLine();
                for (int l = 0; l < m; l++) {
                    System.out.println(clientInput.nextLine());
                }
        }
        catch (NoSuchElementException clientNSElementException) {
            System.err.println("Connection to Server has Been Closed");
            System.exit(1);
        }
    }

    private void LowThroughput(String testName, PrintWriter clientOutput, Scanner clientInput) {
        try {
            WelcomeMessage();
            String userInput = "";
            userInput = "name " + testName;
            clientOutput.println(userInput);
            int n = clientInput.nextInt();
            clientInput.nextLine();
            for (int i = 0; i < n; i++) {
                System.out.println(clientInput.nextLine());
            }
            for (int j = 0; j < 10; j++){
                userInput = "post hello";
                clientOutput.println(userInput);
                int m = clientInput.nextInt();
                clientInput.nextLine();
                for (int k = 0; k < m; k++) {
                    System.out.println(clientInput.nextLine());
                }
            }
            userInput = "read";
            clientOutput.println(userInput);
            int b = clientInput.nextInt();
            clientInput.nextLine();
            for (int l = 0; l < b; l++) {
                System.out.println(clientInput.nextLine());
            }
        }
        catch (NoSuchElementException clientNSElementException) {
            System.err.println("Connection to Server has Been Closed");
            System.exit(1);
        }   
    }
    
    private void Persistance(String testName, PrintWriter clientOutput, Scanner clientInput) {
        WelcomeMessage();
        String userInput = "";
        userInput = "name " + testName;
        clientOutput.println(userInput);
        int n = clientInput.nextInt();
        clientInput.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println(clientInput.nextLine());
        }
        userInput = "open test";
        clientOutput.println(userInput);
        int m = clientInput.nextInt();
        clientInput.nextLine();
        for (int j = 0; j < m; j++) {
            System.out.println(clientInput.nextLine());
        }
        for (int k = 0; k < 100; k++){
            userInput = "postto test hello test";
            clientOutput.println(userInput);
            int b = clientInput.nextInt();
            clientInput.nextLine();
            for (int l = 0; l < b; l++) {
                System.out.println(clientInput.nextLine());
            }
        }
    }

    private void PersistCheck(String testName, PrintWriter clientOutput, Scanner clientInput) {
        WelcomeMessage();
        String userInput = "";
        userInput = "name " + testName;
        clientOutput.println(userInput);
        int n = clientInput.nextInt();
        clientInput.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println(clientInput.nextLine());
        }
        userInput = "open test";
        clientOutput.println(userInput);
        int m = clientInput.nextInt();
        clientInput.nextLine();
        for (int j = 0; j < m; j++) {
            System.out.println(clientInput.nextLine());
        }
        userInput = "read test";
        clientOutput.println(userInput);
        int b = clientInput.nextInt();
        clientInput.nextLine();
        for (int k = 0; k < b; k++) {
            System.out.println(clientInput.nextLine());
        }
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
