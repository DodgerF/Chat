package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatView {
    @FXML
    TextArea chatArea;
    @FXML
    ListView listViewUsers;
    ObservableList<String> list;
    @FXML
    TextArea writeArea;
    @FXML
    Button buttonOnline;
    @FXML
    Button buttonChats;
    @FXML
    Button buttonPrivateChat;
    @FXML
    Button buttonGroupChat;
    @FXML
    Button buttonAddUser;
    @FXML
    Button buttonDeleteChat;
    public ChatView(ListView listViewUsers, TextArea chatArea, TextArea writeArea, Button buttonOnline, Button buttonChats,
                    Button buttonPrivateChat, Button buttonGroupChat, Button buttonAddUser){
        this.listViewUsers = listViewUsers;
        this.chatArea = chatArea;
        this.writeArea = writeArea;
        this.buttonOnline = buttonOnline;
        this.buttonChats = buttonChats;
        this.buttonPrivateChat = buttonPrivateChat;
        this.buttonGroupChat = buttonGroupChat;
        this.buttonAddUser = buttonAddUser;
    }

    public void initialize() {
        list = FXCollections.observableArrayList();
        //listViewUsers.setItems(list);
    }
}
