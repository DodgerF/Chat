package server;

import database.Database;
import parameters.HostParameters;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static List<MonoThreadClientHandler> clients = new ArrayList<>();

    public static List<String> namesList = new ArrayList<>();
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(HostParameters.getPortServer());
            Database.connection();
            while (true) {
                Socket client = server.accept();
                try {
                    MonoThreadClientHandler clientThread = new MonoThreadClientHandler(client);
                    clientThread.start();
                }
                catch (Exception exception){
                    client.close();
                }
            }
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
}