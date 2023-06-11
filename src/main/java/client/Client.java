package client;

import data.*;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import parameters.HostParameters;


import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private String username;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    private ObjectProperty<Object> dispatcher;

    public boolean connect() {
        try{
            dispatcher = new SimpleObjectProperty<>();

            socket = new Socket(HostParameters.getIp(), HostParameters.getPortServer());
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("client connected to socket");
            start();

            return true;
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    private void dispatch(Object message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setDispatcher(message);
            }
        });
    }

    @Override
    public void run(){
        try {
            while (!socket.isClosed()) {
                Object message = in.readObject();
                dispatch(message);

                if (message.getClass().equals(NameDTO.class)) {
                    username = ((NameDTO) message).getUsername();
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
    public void createChat(String username, boolean isPrivate){
        try {
            out.writeObject(new CreateChatDTO(username, isPrivate));
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    public void addUser(String username, String id){
        try{
            out.writeObject(new AddUserDTO(username, id));
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void openChat(String id){
        try {
            out.writeObject(new ChatIDDTO(id));
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    public void getLists(){
        try{
            out.writeObject(new GetNameListDTO());
            out.writeObject(new GetChatListDTO());
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    public void sendMessage(String message, Integer id){
        try {
            out.writeObject(new MessageDTO(message, id));
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
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

    public String getUsername(){
        return username;
    }
    public Object getDispatcher() {
        return dispatcher.get();
    }

    public ObjectProperty<Object> dispatcherProperty() {
        return dispatcher;
    }

    public void setDispatcher(Object dispatcher) {
        this.dispatcher.set(dispatcher);
    }
}
