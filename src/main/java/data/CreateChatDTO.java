package data;

import java.io.Serializable;

public class CreateChatDTO implements Serializable {
    private String username;
    private boolean isPrivate;
    public CreateChatDTO(String username, boolean isPrivate){
        this.username = username;
        this.isPrivate = isPrivate;
    }
    public String getUsername(){
        return username;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
