package database;
import parameters.HostParameters;

import java.sql.*;


public class UsersDatabase {
    private static Connection connection;
    private static PreparedStatement statement;



    public static boolean connection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + HostParameters.getIp() + ":" + HostParameters.getPortDB()
                    + "/postgres", HostParameters.getLoginDB(), HostParameters.getPasswordDB());
            System.out.println("Connection to users db successful");
            createTable();
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public static void createTable() {
        try {

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS public.users\n" +
                    "(\n" +
                    "    username TEXT CHECK (username ~ '^[a-zA-Z]{2,10}$|^([A-Z][a-z]){2,10}$'),\n" +
                    "    password VARCHAR(20) NOT NULL,\n" +
                    "    PRIMARY KEY (username)\n" +
                    ");"
            );
            statement.execute();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

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

    public static void closeDB() {
        try {
            statement.close();
            connection.close();

            System.out.println("Users db closed");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


}
