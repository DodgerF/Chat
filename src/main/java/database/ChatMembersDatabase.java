package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChatMembersDatabase extends Database {
    public static boolean chatExists(String username1, String username2){
        try {
            statement = connection.prepareStatement("SELECT chatID FROM public.chatMembers WHERE username = ? AND isPrivate = true");
            statement.setString(1, username1);
            ResultSet resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                int chatID = resultSet1.getInt("chatID");
                statement = connection.prepareStatement("SELECT chatID FROM public.chatMembers WHERE chatID = ? AND username = ?");
                statement.setInt(1, chatID);
                statement.setString(2, username2);
                ResultSet resultSet2 = statement.executeQuery();
                while (resultSet2.next()) {
                    resultSet1.close();
                    resultSet2.close();
                    return true;
                }
                resultSet2.close();
            }
            resultSet1.close();
            return false;
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }
    public static boolean userAlreadyHere(String username, Integer id){
        try {
            statement = connection.prepareStatement("SELECT username FROM public.chatMembers WHERE chatID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                if(resultSet.getString("username").equals(username)){
                    resultSet.close();
                    return true;
                }
            }
            resultSet.close();
            return false;
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return true;
        }
    }
    public static void createChatMember(int id, String username, boolean isPrivate){
        try {
            statement = connection.prepareStatement("INSERT INTO public.chatMembers (chatID, username, isPrivate) VALUES (?,?,?)");
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.setBoolean(3,isPrivate);
            statement.execute();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    public static List<Integer> getChatsOfUser(String username){
        try {
            statement = connection.prepareStatement("SELECT * FROM public.chatMembers WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            List<Integer> chatList = new ArrayList<>();
            while (resultSet.next()) {
                chatList.add(resultSet.getInt("chatID"));
            }
            return chatList;
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }
    public static List<String> getUsersOfChat(Integer id){
        try {
            statement = connection.prepareStatement("SELECT * FROM public.chatMembers WHERE chatID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<String> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(resultSet.getString("username"));
            }
            return userList;
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public static boolean IsChatPrivate(Integer id){
        try {
            statement = connection.prepareStatement("SELECT * FROM public.chatMembers WHERE chatID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("isPrivate");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return true;
        }
    }

}
