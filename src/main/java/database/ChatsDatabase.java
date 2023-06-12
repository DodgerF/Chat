package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatsDatabase extends Database {
    public static Integer createChat(String username1, String username2, boolean isPrivate) {
        try {
            Random random = new Random();
            int id = random.nextInt(1000000);
            statement = connection.prepareStatement("INSERT INTO public.chats (chatID) VALUES (?)");
            statement.setInt(1, id);
            statement.execute();
            ChatMembersDatabase.createChatMember(id, username1, isPrivate);
            ChatMembersDatabase.createChatMember(id, username2, isPrivate);
            System.out.println("Chat created successfully");
            return id;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            createChat(username1, username2, isPrivate);
        }
        return null;
    }

    public static String getText(Integer id){
        try {
            statement = connection.prepareStatement("SELECT text FROM public.chats WHERE chatID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString("text");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return "";
    }

    public static void setText(String text, Integer id){
        try {
            statement = connection.prepareStatement("SELECT text FROM chats WHERE chatID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            String currentText = resultSet.getString("text");
            String newText;
            if (currentText != null) {
                newText = currentText + text;
            }
            else newText = text;
            if (newText.length() > 8000){
                newText = newText.substring(newText.length()/2);
            }
            statement = connection.prepareStatement("UPDATE chats SET text = ? WHERE chatID = ?");
            statement.setString(1, newText);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Text updated successfully");
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


}
