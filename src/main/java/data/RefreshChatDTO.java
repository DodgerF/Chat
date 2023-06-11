package data;

import java.io.Serializable;
import java.util.List;

public class RefreshChatDTO implements Serializable {
    private String id;
    private String text;
    private List<String> usernames;
    private boolean isPrivate;
    public RefreshChatDTO(Integer id,String text, List<String> clients, boolean isPrivate){
        this.text = text;
        this.usernames = clients;
        this.isPrivate = isPrivate;
        this.id = id.toString();
    }

    public String getText(){
        return text;
    }
    public List<String> getUsernames(){
        return usernames;
    }
    public boolean isPrivate(){
        return isPrivate;
    }
    public String getID(){
        return id;
    }
}
