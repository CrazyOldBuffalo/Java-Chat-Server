import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;

public class Client {
    static final int PORT = 12345;

    public static void main(String[] args) throws UnknownHostException{
        try {
            EstablishConnection();
        }
        catch (IOException eCIoException)
        {
            System.err.println("Error Occurred");
            System.exit(1);
        }
        

    }

    private static void EstablishConnection() throws IOException{
        Scanner clientScanner = ClientScannerBuilder();
        Socket clientSocket = ClientSocketBuilder(PORT);
        DataInputStream inputStream = ClientInputBuilder(clientSocket);
        DataOutputStream outputStream = ClientOutputBuilder(clientSocket);
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String message = clientScanner.nextLine();
                    try {
                        outputStream.writeUTF(message);
                    }
                    catch (IOException ClientIOE) {
                        ClientIOE.printStackTrace();
                    }
                }
            }
        });
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(true) {
                    try {
                        String message = inputStream.readUTF();
                        System.out.println(message);
                    }
                    catch (IOException ClientIOE) {
                        ClientIOE.printStackTrace();
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();
    }

    private static DataInputStream ClientInputBuilder(Socket clientSocket) throws IOException{
        return new DataInputStream(clientSocket.getInputStream());
    }

    private static DataOutputStream ClientOutputBuilder(Socket clientsocket) throws IOException {
        return new DataOutputStream(clientsocket.getOutputStream());
    }
    private static Socket ClientSocketBuilder(int PORT) throws IOException{
        return new Socket("localhost", PORT);
    }

    private static Scanner ClientScannerBuilder() {
        return new Scanner(System.in);
    }

}