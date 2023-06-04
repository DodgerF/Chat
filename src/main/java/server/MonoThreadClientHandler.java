package server;

import data.*;
import database.UsersDatabase;

import java.io.*;
import java.net.Socket;

public class MonoThreadClientHandler extends Thread{
    private Socket clientDialog;
    private String username;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public MonoThreadClientHandler(Socket client) {
        clientDialog = client;
        try{
            in = new ObjectInputStream(clientDialog.getInputStream());
            out = new ObjectOutputStream(clientDialog.getOutputStream());
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    @Override
    public void run(){
        try {

            while (!clientDialog.isClosed()) {
                Object message = in.readObject();
                if (message.getClass().equals(CloseDTO.class)){
                    Server.namesList.remove(username);
                    Server.clients.remove(this);
                    Server.clients.forEach(MonoThreadClientHandler::sendNamesList);
                    clientDialog.close();
                }
                if (message.getClass().equals(LogInDTO.class)){
                    logIn(((LogInDTO) message).getName(), ((LogInDTO) message).getPassword());
                }
            }
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    public void logIn(String username, String password){
        boolean isLogIn = false;

        if (UsersDatabase.checkUser(username)){
            if (UsersDatabase.checkUserPassword(username, password)){
                isLogIn = true;
            }
        }
        else if (UsersDatabase.addUser(username, password)){
            isLogIn = true;
        }

        if (isLogIn){
            this.username = username;
            try {
                out.writeObject(new NameDTO(username));
            }
            catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
            Server.namesList.add(username);
            Server.clients.add(this);
            Server.clients.forEach(MonoThreadClientHandler::sendNamesList);
        }
    }
    public void sendNamesList(){
        try {
            NameListDTO list = new NameListDTO(Server.namesList.stream().filter(id -> !id.equals(this.username)).toList());
            System.out.println(Server.namesList + " server list");
            System.out.println(list.getUsernamesList() + " отсылаемый лист");
            out.writeObject(list);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
}
