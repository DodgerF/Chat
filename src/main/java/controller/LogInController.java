package controller;

import client.Client;
import data.NameDTO;
import database.Database;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Node;

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

    @FXML
    private void logIn(){
        Main.getClient().LogIn(usernameField.getText(), passwordField.getText());
    }

    @FXML
    private void cancel(ActionEvent actionEvent){
        exit();
    }

    private void addDispatcherListener(){
        Main.getClient().dispatcherProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (Main.getClient().getDispatcher() instanceof NameDTO){
                    ((Stage) buttonLogIn.getScene().getWindow()).close();
                    Main.createChatWindow();
                }
            }
        });
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addDispatcherListener();
    }

    @FXML
    public void exit(){
        System.exit(0);
    }

}
