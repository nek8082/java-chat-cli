package com.nek;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class DBManager {
    private DBManager() {}

    private static String dbPath;
    static void createDb() {
        //Create chat database
        final File file = new File("chat.db");
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

    private static void createTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite://" + dbPath);
            statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE chat (id INTEGER PRIMARY KEY AUTOINCREMENT, ip TEXT, msg TEXT," +
                    " sent Bool);");
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
            statement.executeUpdate("INSERT INTO chat (ip, msg, sent) VALUES (\""+ip+"\", \""+msg+"\",\""+
                    sent+"\");");
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

    static void select(final String ip) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite://" + dbPath);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT msg, sent FROM chat WHERE ip=\""+ip+"\"");
            while (resultSet.next()) {
                if (resultSet.getString("sent").equals("true")) {
                    System.out.println(resultSet.getString("msg"));
                } else {
                    System.out.println("    " + resultSet.getString("msg"));
                }
            }
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
}
