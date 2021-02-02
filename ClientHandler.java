import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.simple.*;

public class ClientHandler extends Thread {
        private Socket client;
        private PrintWriter toClient;
        private BufferedReader fromClient;
        private String ClientId;

        public ClientHandler(Socket socket,String clientnumber) throws IOException {
            client = socket;
            ClientId = clientnumber;
            toClient = new PrintWriter(client.getOutputStream(), true);
            fromClient = new BufferedReader(
                             new InputStreamReader(client.getInputStream()));
        }

        @Override
        public void run() {
            try {
                String inputLine;
				while ((inputLine = fromClient.readLine()) != null) {
					System.out.println("Request is "+inputLine);
					toClient.println(inputLine + " To " + ClientId);
			    }


            } catch (IOException e) {
                System.out.println("Exception while connected");
                System.out.println(e.getMessage());
            }
        }
    }