package server;

import data.*;
import database.ChatMembersDatabase;
import database.ChatsDatabase;
import database.UsersDatabase;

import java.io.*;
import java.net.Socket;
import java.util.List;


public class MonoThreadClientHandler extends Thread{
    private Socket clientDialog;
    private String username;
    private List<Integer> chatList;
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
                    closeClient();
                }
                if (message.getClass().equals(LogInDTO.class)){
                    logIn(((LogInDTO) message).getName(), ((LogInDTO) message).getPassword());
                }
                if (message.getClass().equals(CreateChatDTO.class)){
                    createChat(((CreateChatDTO) message).getUsername(), ((CreateChatDTO) message).isPrivate());
                }
                if (message.getClass().equals(ChatIDDTO.class)){
                    sendChatDTO(((ChatIDDTO) message).getId());
                }
                if (message.getClass().equals(GetNameListDTO.class)){
                    sendNameList();
                }
                if (message.getClass().equals(GetChatListDTO.class)){
                    sendChatList();
                }
                if (message.getClass().equals(MessageDTO.class)){
                    sendMessage(((MessageDTO) message).getText(), ((MessageDTO) message).getID());
                }
                if (message.getClass().equals(AddUserDTO.class)){
                    addUser(((AddUserDTO) message).getUsername(), ((AddUserDTO) message).getId());
                }
            }
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void addUser(String username, Integer id) {
        if (ChatMembersDatabase.userAlreadyHere(username, id)){
            return;
        }
        if (ChatMembersDatabase.IsChatPrivate(id)){
            Integer newID;
            if (ChatMembersDatabase.getUsersOfChat(id).get(0).equals(this.username)) {
                newID = createChat(ChatMembersDatabase.getUsersOfChat(id).get(1), false);
            }
            else {
                newID = createChat(ChatMembersDatabase.getUsersOfChat(id).get(0),false);
            }
            addUser(username, newID);
        }
        else{
            ChatMembersDatabase.createChatMember(id, username, false);
            Server.clients.stream().filter(obj -> obj.getUsername().equals(username)).forEach(MonoThreadClientHandler::sendChatList);
        }
    }

    private void sendMessage(String message, Integer id) {
        ChatsDatabase.setText(message, id);
        List<String> usersList = ChatMembersDatabase.getUsersOfChat(id);
        for (MonoThreadClientHandler client : Server.clients){
            if (usersList.contains(client.getUsername())){
                client.refreshChat(id);
            }
        }
    }
    private void refreshChat(Integer id){
        RefreshChatDTO refreshChatDTO = new RefreshChatDTO(id,
                ChatsDatabase.getText(id),
                ChatMembersDatabase.getUsersOfChat(id),
                ChatMembersDatabase.IsChatPrivate(id)
        );
        try{
            out.writeObject(refreshChatDTO);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void sendChatDTO(Integer id){
        ChatDTO chatDTO = new ChatDTO(
               ChatsDatabase.getText(id),
               ChatMembersDatabase.getUsersOfChat(id),
               ChatMembersDatabase.IsChatPrivate(id)
        );
        try {
            out.writeObject(chatDTO);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    private Integer createChat(String username, boolean isPrivate){
        if (isPrivate && ChatMembersDatabase.chatExists(this.username, username)){
            return null;
        }
        Integer id = ChatsDatabase.createChat(this.username, username, isPrivate);
        for(MonoThreadClientHandler client: Server.clients){
            if (client.getUsername().equals(username) || client.getUsername().equals(this.username))
                client.sendChatList();
        }
        return id;
    }

    private void logIn(String username, String password){
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
            sendChatList();
            try {
                out.writeObject(new NameDTO(username));
            }
            catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
            Server.namesList.add(username);
            Server.clients.add(this);
            Server.clients.forEach(MonoThreadClientHandler::sendNameList);
        }
    }
    private void sendNameList(){
        try {
            NameListDTO list = new NameListDTO(Server.namesList.stream().filter(id -> !id.equals(this.username)).toList());
            System.out.println(Server.namesList + " server list");
            out.writeObject(list);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    private void sendChatList(){
        chatList = ChatMembersDatabase.getChatsOfUser(this.username);
        try {
            out.writeObject(new ChatListDTO(chatList));
            System.out.println(chatList + " chats");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    private void closeClient(){
        try {
            Server.namesList.remove(username);
            Server.clients.remove(this);
            Server.clients.forEach(MonoThreadClientHandler::sendNameList);
            clientDialog.close();
            System.out.println("client is closed");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    private String getUsername(){
        return this.username;
    }
}
