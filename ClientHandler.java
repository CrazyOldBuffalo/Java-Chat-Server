import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.json.simple.*;


public class ClientHandler extends Thread {
    private Socket client;
    private PrintWriter toClient;
    private BufferedReader fromClient;
    private String clientName;
    private Server server;
    private static ArrayList<Message> board = new ArrayList<>();


    public ClientHandler(Socket clientSocket, Server server) throws IOException{
        client = clientSocket;
        toClient = ToClientBuilder();
        fromClient = FromClientBuilder();
        this.server = server;
	}



	@Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = fromClient.readLine()) != null) {
                System.out.println(inputLine);
                Scanner clientHandlerScanner = new Scanner(inputLine);
                String command  = "";
                String argument = "";
                try {
                        command  = clientHandlerScanner.next();
                        argument = clientHandlerScanner.skip(" ").nextLine();
                } 
                catch (NoSuchElementException e) {
                    System.err.println("Please Enter a Valid comand and/or Message");
                }
                if (command.equalsIgnoreCase("Name")) {
                    synchronized(ClientHandler.class) {
                        clientName = argument;
                    }
                    toClient.println(1);
                    toClient.println("Your Name is now Set to: " + clientName);
                }
                else if (command.equalsIgnoreCase("post")) {
                    synchronized (ClientHandler.class) {
                        Date date = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Message json = new Message(clientName, argument, format.format(date));
                        board.add(json);
                    }
                    toClient.println(1);
                    toClient.println("Message Has been Posted");
                    continue;
                }
                else if (command.equalsIgnoreCase("read")) {
                    synchronized (ClientHandler.class) {
                        toClient.println(board.size());
                        for (Message msg : board)
                            toClient.println(msg.getClientName() + " Says: " + msg.getMessage() + " on " + msg.getDate());
                    }
                    continue;
                }
                else if (command.equalsIgnoreCase("quit")) {
                    toClient.println(1);
                    toClient.println("Exiting");
                    fromClient.close();
                    toClient.close();
                    return;
                }
                else {
                    toClient.println(1);
                    toClient.println("Please Enter a Valid Command");
                }
            }
        } catch (IOException clientHanlderIoException) {
            System.err.println("Exception while connected");
        }
    }

    private BufferedReader FromClientBuilder() throws IOException {
        return new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    private PrintWriter ToClientBuilder() throws IOException {
        return new PrintWriter(client.getOutputStream(), true);
    }
}