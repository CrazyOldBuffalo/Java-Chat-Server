import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
    private static File file;
    private static ArrayList<Message> open = new ArrayList<>();
    private static ArrayList<Message> archive = new ArrayList<>();
    private static ArrayList<String> clientNames = new ArrayList<>();


    public ClientHandler(Socket clientSocket, Server server) throws IOException{
        client = clientSocket;
        toClient = ToClientBuilder();
        fromClient = FromClientBuilder();
        this.server = server;
        file = FileCreator();
	}

	private File FileCreator() {
        return new File("main.txt");
    }

    @Override
    public void run() {
        try {
            int unread = 0;
            String inputLine;
            while ((inputLine = fromClient.readLine()) != null) {
                System.out.println(inputLine);
                Scanner clientHandlerScanner = new Scanner(inputLine);
                String command  = "";
                String argument = "";
                String room = "";
                try {
                        command  = clientHandlerScanner.next();
                        argument = clientHandlerScanner.skip(" ").nextLine();
                } 
                catch (NoSuchElementException e) {
                    System.err.println("Please Enter a Valid comand and/or Message");
                }
                if (command.equalsIgnoreCase("name") || command.equalsIgnoreCase("n")){
                    synchronized(ClientHandler.class) {
                        ClientNameSetter(argument);
                    }
                }
                else if ((command.equalsIgnoreCase("post") || command.equalsIgnoreCase("p")) && argument.length() > 1) {
                    synchronized(ClientHandler.class) {
                        PostMessage(argument);
                    }
                }
                else if ((command.equalsIgnoreCase("read") || command.equalsIgnoreCase("r")) && argument.length() == 0) {
                    ReadMessages(unread);
                    unread = open.size();
                }
                else if (command.equalsIgnoreCase("archive") || command.equalsIgnoreCase("a")) {
                    ReadArchive();
                }
                else if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("h")) {
                    Help();
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
        } 
        catch (IOException clientHanlderIoException) {
            System.err.println("Exception while connected");
        }
    }

    public String clientName() {
        return clientName;
    }

    private void ReadArchive() throws IOException {
        BufferedReader fr = new BufferedReader(new FileReader("main.txt"));
        String line = null;
        while ((line = fr.readLine()) != null) {
            
            String[] archives = parse(line);
            String username = archives[0];
            String Message = archives[1];
            String date = archives[2];
            String time = archives[3];
            Message archiveJson = new Message(username, Message, date + " " + time);
            archive.add(archiveJson);

        }
        toClient.println(archive.size());
        for (Message ames : archive) {
            toClient.println(ames.getClientName() + " Says: " + ames.getMessage() + "\n" + " On [ " + ames.getDate() + " ]");
        }
        
    }

    private String[] parse(String line) {
        return line.split("\\s+");
    }

    private void Help() {
        toClient.println(4);
        toClient.println("name/n - For Setting your UserName, follow this with your specified username.      e.g: name Harry");
        toClient.println("post/p - To Post a Message to the MessageBoard, follow this with your message.     e.g: post Hello There");
        toClient.println("read/r - To Read all Posted Messages on the server.                                e.g: read");
        toClient.println("archive/a - Read all Stored Messages from the archive.                             e.g: archive");
        toClient.println("quit/q - To Exit from the server.                                                  e.g: quit");
    }

    private void ClientQuit(Scanner clientHandlerScanner) throws IOException {
        toClient.println(1);
        toClient.println("Exiting");
        clientNames.remove(this.clientName);
        fromClient.close();
        toClient.close();
        clientHandlerScanner.close();
    }

    private void ReadMessages(int unread) {
        toClient.println(open.size() - unread);
        for (int i = unread; i < open.size(); i++) {
            toClient.println(open.get(i).getClientName() + " Says: " + open.get(i).getMessage() + " On " + "\n" + open.get(i).getDate());
        }
   } 

    private void PostMessage(String argument) throws IOException{
        FileWriter fWriter = new FileWriter(file, true);
        System.out.println("File Created");
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Message json = new Message(clientName, argument, format.format(date));
        fWriter.append(json.getClientName() + " " + json.getMessage() + " " + json.getDate() + "\n");
        open.add(json);
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