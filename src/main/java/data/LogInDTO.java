package data;

import java.io.Serializable;

public class LogInDTO implements Serializable {
    private String name;
    private String password;
    public LogInDTO(String name, String password){
        this.name = name;
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

}
