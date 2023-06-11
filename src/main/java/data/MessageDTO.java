package data;

import java.io.Serializable;

public class MessageDTO implements Serializable {
    private String text;
    private Integer id;
    public MessageDTO(String text, Integer id){
        this.text = text;
        this.id = id;
    }
    public String getText(){
        return text;
    }
    public Integer getID(){
        return id;
    }
}
