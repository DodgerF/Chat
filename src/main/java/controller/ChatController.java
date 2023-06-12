package controller;

import client.Client;
import data.ChatDTO;
import data.ChatListDTO;
import data.NameListDTO;
import data.RefreshChatDTO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import sample.chat.Main;
import view.ChatView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    ListView listView;
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
    Button buttonOpen;
    @FXML
    Button buttonSend;
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonCancel;
    @FXML
    Text chatInfo;

    private String currentChat = "";
    private ObservableList<String> nameList = FXCollections.observableArrayList();
    private ObservableList<String> chatList = FXCollections.observableArrayList();
    private ChatView chatView;

    public void createPrivateChat(){
        try {
            MultipleSelectionModel selectionModel = listView.getSelectionModel();
            Main.getClient().createChat(selectionModel.getSelectedItems().get(0).toString(), true);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    public void createGroupChat(){
        try {
            MultipleSelectionModel selectionModel = listView.getSelectionModel();
            Main.getClient().createChat(selectionModel.getSelectedItems().get(0).toString(), false);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    public void selectUser(){
        chatView.setListView(nameList);
        chatView.setSelectUser(true);
    }
    public void cancelSelect(){
        chatView.setListView(chatList);
        chatView.setSelectUser(false);
    }
    public void addUser(){
        try {
            MultipleSelectionModel selectionModel = listView.getSelectionModel();
            Main.getClient().addUser(selectionModel.getSelectedItems().get(0).toString(), currentChat);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        chatView.setListView(chatList);
        chatView.setSelectUser(false);
    }


    public void openChat(){
        try {
            MultipleSelectionModel selectionModel = listView.getSelectionModel();
            Main.getClient().openChat(selectionModel.getSelectedItems().get(0).toString());
            currentChat = selectionModel.getSelectedItems().get(0).toString();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    public void send(){
        Main.getClient().sendMessage(Main.getClient().getUsername() + ": " + writeArea.getText() + "\n\n", Integer.parseInt(currentChat));
        writeArea.clear();
    }

    public void openChats(){
        chatView.setListView(chatList);
        chatView.setChatsView();
    }
    public void openUsers(){
        chatView.setListView(nameList);
        chatView.setUsersView();
        currentChat = "";
    }

    private void addDispatcherListener(){
        Main.getClient().dispatcherProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Object message = Main.getClient().getDispatcher();
                if (message instanceof NameListDTO){
                    nameList.setAll(((NameListDTO) message).getUsernamesList());
                }
                if (message instanceof ChatListDTO){
                    chatList.setAll(((ChatListDTO) message).getChatList().stream().map(String::valueOf).toList());
                }
                if (message instanceof ChatDTO chat){
                    chatView.setChatInfo(chat.isPrivate(), chat.getUsernames());
                    chatArea.setText(chat.getText());
                    chatView.setWriteArea(true);
                }
                if (message instanceof RefreshChatDTO chat){
                    if (currentChat.equals(chat.getID())){
                        chatView.setChatInfo(chat.isPrivate(), chat.getUsernames());
                        chatArea.setText(chat.getText());
                        System.out.println(chat.getText());
                    }
                }
            }
        });
    }

    @FXML
    public void exit(){
        Main.getClient().closeClient();
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.chatView = new ChatView(listView, chatArea, writeArea, buttonOnline, buttonChats,
                buttonPrivateChat, buttonGroupChat, buttonAddUser, buttonOpen, buttonSend, chatInfo, buttonAdd, buttonCancel);
        addDispatcherListener();
        Main.getClient().getLists();
        openUsers();
    }
}