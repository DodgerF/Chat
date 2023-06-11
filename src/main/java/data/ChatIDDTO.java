package data;

import java.io.Serializable;

public class ChatIDDTO implements Serializable {
    private Integer id;
    public ChatIDDTO(String id){
        this.id = Integer.parseInt(id);
    }
    public Integer getId(){
        return id;
    }
}
