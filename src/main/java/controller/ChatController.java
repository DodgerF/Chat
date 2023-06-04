package controller;

import client.Client;
import database.UsersDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import view.ChatView;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    ListView listViewUsers;
    @FXML
    TextArea chatArea;
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
    private ObservableList<String> listName = FXCollections.observableArrayList();
    private Client client;
    private ChatView chatView;
    public void setClient(Client client){
        listViewUsers.setItems(listName);
        this.client = client;
        this.client.setUsersName(listName);
    }
    @FXML
    public void exit(){
        client.closeClient();
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.chatView = new ChatView(listViewUsers, chatArea, writeArea, buttonOnline, buttonChats, buttonPrivateChat, buttonGroupChat, buttonAddUser);


    }
}