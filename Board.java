import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Board {
    private String boardName;
    private static ArrayList<String> Clients = new ArrayList<>();
    private static ArrayList<Message> messages = new ArrayList<>();
    private File room;

    public Board(String sName, String client) {
        this.boardName = sName;
        Clients.add(client);
        room = CreateRoomFile();
    }

    private File CreateRoomFile() {
        return new File(boardName +".txt");
    }

    public void AddClient(String client) {
        Clients.add(client);
    }

    public void RoomPost(String clientName, String message) throws IOException{
        FileWriter fWriter = new FileWriter(room, true);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Message json = new Message(clientName, message, format.format(date));
        messages.add(json);
        fWriter.append(json.getClientName() + " " + json.getMessage() + " " + json.getDate());
        fWriter.close();
    }

    public String boardname() {
        return boardName;
    }

    public ArrayList<Message> ReadRoom() {
        return messages;
    }

    public boolean Subscribed(String ClientName) {
        if (Clients.contains(ClientName)) {
            return true;
        }
        return false;
    }

    public int GetMessageCount() {
        return messages.size();
    }
}
