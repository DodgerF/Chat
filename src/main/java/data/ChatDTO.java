package data;

import client.Client;

import java.io.Serializable;
import java.util.List;

public class ChatDTO implements Serializable {
    private String text;
    private List<String> usernames;
    private boolean isPrivate;
    public ChatDTO(String text, List<String> clients, boolean isPrivate){
        this.text = text;
        this.usernames = clients;
        this.isPrivate = isPrivate;
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
}
