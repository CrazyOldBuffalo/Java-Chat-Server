import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable{
	Scanner clientHandlerScanner = ClientHandlerScannerBuilder();
	private String userName;
	final DataInputStream inputStream;
	final DataOutputStream outputStream;
	Socket clientSocket;
	boolean loggedIn;

	public ClientHandler(Socket clientSocket, String userName, DataInputStream inputStream, DataOutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.userName = userName;
		this.clientSocket = clientSocket;
		this.loggedIn = true;
	}

	private static Scanner ClientHandlerScannerBuilder() {
		return new Scanner(System.in);
	}

	@Override
	public void run() {
		String message;
		while (true) {
			try{
				message = inputStream.readUTF();
				System.out.println(message);

				if(message.equals("exit")) {
					this.loggedIn = false;
					this.clientSocket.close();
					break;
				}
				StringTokenizer clientTokenizer = new StringTokenizer(message, "#");
				String sentMessage = clientTokenizer.nextToken();
				String receivedMessage = clientTokenizer.nextToken();

				for (ClientHandler mc : Server.clientHandlerVector)
				{
					if(mc.userName.equals(receivedMessage) && mc.loggedIn == true)
					{
						mc.outputStream.writeUTF(this.userName + "> " + sentMessage);
					}
				}
			}
			catch (IOException test)
			{
				test.printStackTrace();
			}
		}
		try {
			this.inputStream.close();
			this.outputStream.close();
		}
		catch (IOException test)
		{
			test.printStackTrace();
		}
	}



}
