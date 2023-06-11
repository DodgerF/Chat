package sample.chat;

import client.Client;
import controller.ChatController;
import controller.LogInController;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private static Client client;
    @Override
    public void start(Stage stage){
        client = new Client();
        if(client.connect())
            createLogInWindow();
        else{
            System.exit(0);
        }
    }

    public static void createLogInWindow(){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("logIn.fxml"));

        Scene scene = null;
        try{
            scene = new Scene(loader.load(), 600, 400);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setOnCloseRequest(windowEvent -> {
            ((LogInController) loader.getController()).exit();
        });
        stage.show();
    }

    public static void createChatWindow(){

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("chat.fxml"));

        Scene scene = null;
        try{
            scene = new Scene(loader.load(), 600, 400);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle(getClient().getUsername());
        stage.setOnCloseRequest(windowEvent -> {
            ((ChatController) loader.getController()).exit();
        });
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }


    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        Main.client = client;
    }
}