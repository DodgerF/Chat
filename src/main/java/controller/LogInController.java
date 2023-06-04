package controller;

import client.Client;
import database.UsersDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.chat.Main;


import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable{

    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    Button buttonCancel;
    @FXML
    Button buttonLogIn;
    private Client client;

    public void setClient(Client client){
        this.client = client;
    }
    @FXML
    private void logIn(ActionEvent actionEvent){
        client.connect();
        client.LogIn(usernameField.getText(), passwordField.getText());
    }

    @FXML
    private void cancel(ActionEvent actionEvent){
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void exit(){
        UsersDatabase.closeDB();
        System.exit(0);
    }

}
