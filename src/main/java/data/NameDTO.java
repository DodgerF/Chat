package data;

import java.io.Serializable;

public class NameDTO implements Serializable {
    private String username;
    public NameDTO(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
}
