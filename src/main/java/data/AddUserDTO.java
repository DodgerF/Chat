package data;

import java.io.Serializable;

public class AddUserDTO implements Serializable {
    private String username;
    private Integer id;
    public AddUserDTO(String username, String id){
        this.username = username;
        this.id = Integer.parseInt(id);
    }

    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }
}
