
import java.util.Calendar;

import org.json.simple.*;

public class Message {
    private static final String CLASS = Message.class.getSimpleName();
    private String message;
    private String clientName;
    private String date;
    
    
    public Message(String sclientName, String smessage, String sdate) {
        this.clientName = sclientName;
        this.message = smessage;
        this.date = sdate;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date.toString();
    }

    public String getClientName() {
        return clientName;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("_class", CLASS);
        json.put("clientName", clientName);
        json.put("Message", message);
        json.put("Date", date);
        return json;
    }
}
