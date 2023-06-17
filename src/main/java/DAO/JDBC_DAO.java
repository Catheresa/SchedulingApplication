package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

/** A class used to connect to the SQL database. */
public abstract class JDBC_DAO {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    private static Connection connection;

    /** A method to connect to the SQL database. */
    private static void openConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
    public static Connection getConnection(){
        if(connection == null){
            openConnection();
        }
        return connection;
    }

    /** A method to close the connection to the SQL database. */
    public static void closeConnection() {
        try {
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
