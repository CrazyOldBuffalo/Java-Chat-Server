import java.util.ArrayList;

public class Board {
    private String boardName;
    private static ArrayList<String> Clients = new ArrayList();
    private static ArrayList<Message> messages = new ArrayList();

    public Board(String sName, String client) {
        this.boardName = sName;
        Clients.add(client);
    }

    public void AddClient(String client) {
        Clients.add(client);
    }

    public void RoomPost(Message msg) {
        messages.add(msg);
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
