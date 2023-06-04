package client;

import controller.ChatController;
import controller.LogInController;
import data.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parameters.HostParameters;
import sample.chat.Main;


import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private String username;
    private String password;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ObservableList<String> usersName;
    private Scene scene;
    private Stage stage;

    public String getUsername(){
        return username;
    }
    public boolean connect() {
        try{
            socket = new Socket(HostParameters.getIp(), HostParameters.getPortServer());
            System.out.println("client connected to socket");
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            start();
            return true;
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }
    @Override
    public void run(){
        try {
            while (!socket.isClosed()) {
                Object message = in.readObject();
                if (message.getClass().equals(NameDTO.class)) {
                    username = ((NameDTO) message).getUsername();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            closeWindow();
                            createChatWindow();
                        }
                    });

                }
                if (message.getClass().equals(NameListDTO.class)) {
                    System.out.println(((NameListDTO) message).getUsernamesList());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println("users name set");
                                usersName.setAll(((NameListDTO) message).getUsernamesList().stream().map(String::valueOf).toList());
                            } catch (Exception exception) {
                                if(usersName != null)
                                    usersName.clear();
                            }
                        }
                    });
                }
            }
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public boolean LogIn(String username, String password){
        try{
            LogInDTO logInDTO = new LogInDTO(username, password);
            out.writeObject(logInDTO);
            return true;
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }
    public void setUsersName(ObservableList<String> usersName){
        this.usersName = usersName;
    }
    public void closeClient(){
        try {
            out.writeObject(new CloseDTO());
            socket.close();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }



    public void createLogInWindow(){

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("logIn.fxml"));

        scene = null;
        try{
            scene = new Scene(loader.load(), 600, 400);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        ((LogInController)loader.getController()).setClient(this);
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setOnCloseRequest(windowEvent -> {
            ((LogInController) loader.getController()).exit();
        });
        stage.show();
    }
    public void closeWindow(){
        stage.getScene().getWindow().hide();
    }
    public void createChatWindow(){

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("chat.fxml"));

        scene = null;
        try{
            scene = new Scene(loader.load(), 600, 400);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        ((ChatController)loader.getController()).setClient(this);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(username);
        stage.setResizable(false);

        stage.setOnCloseRequest(windowEvent -> {
            ((ChatController) loader.getController()).exit();
        });
        stage.show();
    }

}
