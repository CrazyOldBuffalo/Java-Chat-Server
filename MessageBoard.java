import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageBoard {
    private ArrayList<Message> Messages = new ArrayList<>();
    private ArrayList<ClientHandler> clientList = new ArrayList<>();
    private String name;

    public MessageBoard(String name, ClientHandler client) {
        this.name = name;
        clientList.add(client);
    }
    
    public void AddClient(ClientHandler clientThread) {
        clientList.add(clientThread);
    }

    public String GetName() {
        return name;
    }

    public void addMessage(String clientName, String message) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Message jsonMessage = new Message(clientName, message, format.format(date));
        Messages.add(jsonMessage);
    }

    public Message printMessage() {
        for (Message msg: Messages) {
            return msg;
        }
        return null;
    }

}
