package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatView {
    @FXML
    TextArea chatArea;
    @FXML
    ListView listView;
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
    Button buttonOpen;
    @FXML
    Button buttonSend;
    @FXML
    Button buttonAdd;
    @FXML
    Button buttonCancel;
    @FXML
    Text chatInfo;
    public ChatView(ListView listView, TextArea chatArea, TextArea writeArea, Button buttonOnline, Button buttonChats,
                    Button buttonPrivateChat, Button buttonGroupChat, Button buttonAddUser, Button buttonOpen, Button buttonSend,
                    Text chatInfo, Button buttonAdd, Button buttonCancel){
        this.listView = listView;
        this.chatArea = chatArea;
        this.writeArea = writeArea;
        this.buttonOnline = buttonOnline;
        this.buttonChats = buttonChats;
        this.buttonPrivateChat = buttonPrivateChat;
        this.buttonGroupChat = buttonGroupChat;
        this.buttonAddUser = buttonAddUser;
        this.buttonOpen = buttonOpen;
        this.buttonSend = buttonSend;
        this.chatInfo = chatInfo;
        this.buttonAdd = buttonAdd;
        this.buttonCancel = buttonCancel;
        setChatPreferences();
    }
    public void setActive(Button button, boolean bool){
        button.setDisable(!bool);
        button.setVisible(bool);
    }
    private void setChatPreferences(){
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
    }
    public void setListView(ObservableList<String> list){
        listView.setItems(list);
    }
    public void setChatsView(){
        setActive(buttonOpen, true);
        setActive(buttonGroupChat, false);
        setActive(buttonPrivateChat, false);
        buttonAddUser.setVisible(true);
    }
    public void setUsersView(){
        setActive(buttonOpen, false);
        setActive(buttonGroupChat, true);
        setActive(buttonPrivateChat, true);
        buttonAddUser.setVisible(false);
        setWriteArea(false);
        chatArea.clear();
        chatInfo.setText("");
    }
    public void setWriteArea(boolean bool){
        writeArea.setDisable(!bool);
        buttonSend.setDisable(!bool);
        buttonAddUser.setDisable(!bool);
    }
    public void setSelectUser(boolean bool){
        setActive(buttonOpen, !bool);
        setActive(buttonGroupChat, !bool);
        setActive(buttonPrivateChat, !bool);
        setActive(buttonAddUser, !bool);
        setActive(buttonChats, !bool);
        setActive(buttonOnline, !bool);
        setActive(buttonAdd, bool);
        setActive(buttonCancel, bool);
    }
    public void setChatInfo(boolean isPrivate, List<String> usernames){
        String str = "Chat is" + (isPrivate ? " private. ":"n't private. ")
                + String.join(", ", usernames);
        if (str.length() > 90)
            str = str.substring(87) + "...";
        chatInfo.setText(str);
    }
    public void initialize() {
        list = FXCollections.observableArrayList();
    }
}
