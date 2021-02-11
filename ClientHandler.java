import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileWriter;



public class ClientHandler extends Thread {
    private Socket client;
    private PrintWriter toClient;
    private BufferedReader fromClient;
    private String clientName = "client";
    private Server server;
    private static ArrayList<Message> board = new ArrayList<>();
    private static ArrayList<String> clientNames = new ArrayList<>();


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
                        ClientNameSetter(argument);
                    }
                }
                else if (command.equalsIgnoreCase("post") && argument.length() > 1) {
                    synchronized(ClientHandler.class) {
                        PostMessage(argument);
                    }
                }
                else if (command.equalsIgnoreCase("read") && argument.length() == 0) {
                    synchronized (ClientHandler.class) {
                        ReadMessages();
                    }
                }
                else if (command.equalsIgnoreCase("quit")) {
                    ClientQuit(clientHandlerScanner);
                    return;
                }
                else {
                    toClient.println(1);
                    toClient.println("Please Enter a Valid Command and/or Argument");
                }
            }
        } catch (IOException clientHanlderIoException) {
            System.err.println("Exception while connected");
        }
    }

    private void ClientQuit(Scanner clientHandlerScanner) throws IOException {
        toClient.println(1);
        toClient.println("Exiting");
        clientNames.remove(this.clientName);
        fromClient.close();
        toClient.close();
        clientHandlerScanner.close();
    }

    private void ReadMessages() {
        toClient.println(board.size());
        for (Message msg : board)
            toClient.println(msg.getClientName() + " Says: " + msg.getMessage() + " on " + msg.getDate());
    }

    private void PostMessage(String argument) throws IOException{
        File file = new File("main.txt");
        if(file.createNewFile()) {
            System.out.println("File Created");
        }
        else {
            System.out.println("File Already Exists");
        }
        FileWriter fWriter = new FileWriter(file, true);
        System.out.println("File Created");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Message json = new Message(clientName, argument, format.format(date));
        fWriter.append(json.getClientName() + " " + json.getMessage() + " " + json.getDate() + "\n");
        board.add(json);
        fWriter.close();
        toClient.println(1);
        toClient.println("Message Has been Posted");
    }

    private void ClientNameSetter(String argument) {
        if (argument.length() > 2 && !clientNames.contains(argument)) {
            clientName = argument;
            clientNames.add(argument);
            toClient.println(1);
            toClient.println("Your Name is now Set to: " + clientName);
        }
        else {
            toClient.println(1);
            toClient.println("Name is not Acceptable or Taken");
        }
    }

    private BufferedReader FromClientBuilder() throws IOException {
        return new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    private PrintWriter ToClientBuilder() throws IOException {
        return new PrintWriter(client.getOutputStream(), true);
    }
}