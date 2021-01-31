import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket clientSocket;
    private PrintWriter clientWriter;
    private BufferedReader clientReader;
    private String clientName;

	public ClientHandler(Socket clientSocket, String clientName) throws IOException{
        this.clientSocket = clientSocket;
        this.clientWriter = clientWriterBuilder(clientSocket);
        this.clientReader = clientReaderBuilder(clientSocket);
        this.clientName = clientName;
	}


	private BufferedReader clientReaderBuilder(Socket clientSocket) throws IOException{
        return new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private PrintWriter clientWriterBuilder(Socket clientSocket) throws IOException {
        return new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            String message;
                while((message = clientReader.readLine()) != null) {
                    System.out.println("Message Recieved from Client");
                    clientWriter.write(message);
                }

        }
        catch (IOException clientHandlerIOException) {
            System.err.println("Error When Printing Message");
        }
	}
    
}
