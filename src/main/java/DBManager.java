import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private DBManager() {}

    private static String dbPath;
    public static void createDb() {
        //Create chat database
        File file = new File("chat.db");
        try {
            dbPath = file.getAbsolutePath();
            if (file.createNewFile()) {
                createTable();
                System.out.println("File is created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite://" + dbPath);
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE chat (id INTEGER PRIMARY KEY AUTOINCREMENT, ip TEXT, msg TEXT, sent Bool);");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static synchronized void insert(String ip, String msg, boolean sent) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite://" + dbPath);
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO chat (ip, msg, sent) VALUES (\""+ip+"\", \""+msg+"\",\""+sent+"\");");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void select(String ip) {

    }
}
