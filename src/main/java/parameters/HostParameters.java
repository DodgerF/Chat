package parameters;

public class HostParameters {
    private static final String ip = "localhost";
    private static final String portDB = "5432";
    private static final String loginDB = "postgres";
    private static final String passwordDB = "admin";
    private static final int portServer = 5431;

    public static String getIp(){
        return ip;
    }
    public static String getPortDB(){
        return portDB;
    }
    public static int getPortServer(){
        return portServer;
    }
    public static String getLoginDB(){
        return loginDB;
    }
    public static String getPasswordDB(){
        return passwordDB;
    }

}
