package database;
import parameters.HostParameters;

import java.sql.*;


public class UsersDatabase extends Database {

    public static void clearTable() {
        try {
            statement = connection.prepareStatement("DELETE FROM public.users");
            statement.execute();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static boolean addUser(String username, String password) {
        try {
            statement = connection.prepareStatement("INSERT INTO public.users (username, password) VALUES (?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();

            System.out.println("User " + username + " added successfully");
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }

    }

    public static boolean checkUser(String username) {
        try {
            statement = connection.prepareStatement("SELECT COUNT(*) FROM public.users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("COUNT");
            resultSet.close();
            return count != 0;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }

    }
    public static boolean checkUserPassword(String username, String password) {
        try {
            statement = connection.prepareStatement("SELECT password FROM public.users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getString("password").equals(password) ) {
                resultSet.close();
                return true;
            }
            resultSet.close();
            return false;
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }



}
