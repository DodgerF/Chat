package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NameListDTO implements Serializable {
    private List<String> usernamesList;
    public NameListDTO(List<String> usernamesList){
        this.usernamesList = new ArrayList<>();
        this.usernamesList.addAll(usernamesList);
    }
    public List<String> getUsernamesList(){
        return usernamesList;
    }
    public void setUsernamesList(List<String> usernamesList){
        this.usernamesList.clear();
        this.usernamesList.addAll(usernamesList);
    }
}
