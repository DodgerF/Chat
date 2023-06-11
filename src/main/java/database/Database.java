package database;

import parameters.HostParameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Database {
    protected static Connection connection;
    protected static PreparedStatement statement;


    public static boolean connection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + HostParameters.getIp() + ":" + HostParameters.getPortDB()
                    + "/postgres", HostParameters.getLoginDB(), HostParameters.getPasswordDB());
            System.out.println("Connection to DB successful");
            createTables();
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }
    private static void createTables(){
        createUsersTable();
        createChatsTable();
        createChatMembersTable();
    }

    private static void createUsersTable() {
        try {

            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS public.users\n" +
                    "(\n" +
                    "    username VARCHAR(20) CHECK (username ~ '^[a-zA-Z]{2,10}$|^([A-Z][a-z]){2,10}$'),\n" +
                    "    password VARCHAR(20) NOT NULL,\n" +
                    "    PRIMARY KEY (username)\n" +
                    ");"
            );
            statement.execute();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    private static void createChatsTable(){
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS public.chats\n" +
                    "(\n" +
                    "    chatID INT,\n" +
                    "    text VARCHAR(8000),\n" +
                    "    PRIMARY KEY (chatID)\n" +
                    ");"
            );
            statement.execute();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    private static void createChatMembersTable(){
        try {
            statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS public.chatMembers\n" +
                    "(\n" +
                    "    chatID INT,\n" +
                    "    username VARCHAR(20),\n" +
                    "    isPrivate BOOLEAN\n" +
                    ");"
            );
            statement.execute();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
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
