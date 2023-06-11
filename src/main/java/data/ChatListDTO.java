package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatListDTO implements Serializable {
    private List<Integer> chatList;
    public ChatListDTO(List<Integer> chatList){
        this.chatList = new ArrayList<>();
        this.chatList.addAll(chatList);
    }
    public List<Integer> getChatList(){
        return chatList;
    }

}
