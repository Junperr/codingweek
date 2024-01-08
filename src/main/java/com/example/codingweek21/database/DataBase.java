package com.example.codingweek21.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private String dbName;
    private static DataBase instance;

    private DataBase() {
        this.dbName = "pixou.db";
        File dbFile = new File("pixou.db");
        if (!dbFile.exists()) {
            this.createDatabaseFile();
        }
    }

    public static synchronized DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public void exec(String query, Object... args) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, args);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object fetchOne(String query, Object... args) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getObject(1) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<List<Object>> fetchAll(String query, Object... args) {
        List<List<Object>> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getObject(i));
                }
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void setParameters(PreparedStatement preparedStatement, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
    }

    private void createDatabaseFile() {
        try {
            File dbFile = new File(dbName);
            if (!dbFile.exists()) {
                exec("CREATE TABLE IF NOT EXISTS Users (firstName TEXT, lastName TEXT, userName TEXT PRIMARY KEY, email TEXT, password TEXT, coins INTEGER)");
                exec("CREATE TABLE IF NOT EXISTS Offers (id UUID PRIMARY KEY, title TEXT, user TEXT, description TEXT, imagePath TEXT, price INTEGER, FOREIGN KEY(user) REFERENCES Users(id))");

                // Insert data into table1
                exec("INSERT INTO Users (userName, firstName, lastName, email, password, coins) VALUES ('user1', 'firstName1', 'lastName1', 'email1', 'password1', 100)");
                exec("INSERT INTO Users (userName, firstName, lastName, email, password, coins) VALUES ('user2', 'firstName2', 'lastName2', 'email2', 'password2', 50)");

                // Insert data into table2
                exec("INSERT INTO offers (id, title, description, imagePath, price, user) VALUES ('1', 'title1', 'description1', 'imagePath1', 10, 'user1')");

                // Fetch data from table1
                List<List<Object>> dataTable1 = fetchAll("SELECT * FROM Users");
                System.out.println("Data from Users:");
                printData(dataTable1);

                // Fetch data from table2
                List<List<Object>> dataTable2 = fetchAll("SELECT * FROM Offers");
                System.out.println("Data from Offers:");
                printData(dataTable2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printData(List<List<Object>> data) {
        for (List<Object> row : data) {
            System.out.println(row);
        }
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }
}
