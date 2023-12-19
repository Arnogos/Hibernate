package jm.task.core.jdbc;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String userName = "root";
        String password = "baguvix1";
        String connectionURL = "jdbc:mysql://localhost:3306/test";
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement statemetn = connection.createStatement()) {
            statemetn.executeUpdate("CREATE TABLE IF NOT EXISTS Users (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id));");
            statemetn.executeUpdate("INSERT INTO Users (name) values ('Poppy')");
            statemetn.executeUpdate("INSERT INTO Users (name) values ('Kayn')");
            statemetn.executeUpdate("INSERT INTO Users set name = 'Warwick'");
            statemetn.executeUpdate("INSERT INTO Users set name = 'Rhaast'");
            ResultSet resultSet = statemetn.executeQuery("select * from Users");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println("---------------");

            }
            statemetn.executeUpdate("Delete from Users");
            statemetn.executeUpdate("drop table Users");

        }
    }
}
/*
Class.forName("com.mysql.jdbc.Driver");
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        System.setProperty("jdbc.drivers","com.mysql.jdbc.Driver");*/
