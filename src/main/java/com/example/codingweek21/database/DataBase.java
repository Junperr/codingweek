package com.example.codingweek21.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<Object> fetchOne(String query, Object... args) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<Object> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            return row;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ArrayList<Object>> fetchAll(String query, Object... args) {
        ArrayList<ArrayList<Object>> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                ArrayList<Object> row = new ArrayList<>();
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

                // Insert data into Users
                exec("INSERT INTO Users (userName, firstName, lastName, email, password, coins) VALUES ('annaG', 'Anna', 'Galkowski', 'anna.galkowski@telecomnancy.net', '1234', 1000)");
                exec("INSERT INTO Users (userName, firstName, lastName, email, password, coins) VALUES ('joelD', 'Joel', 'Duhem', 'joel.duhem@telecomnancy.net', '2704', 10)");
                exec("INSERT INTO Users (userName, firstName, lastName, email, password, coins) VALUES ('ugoG', 'Ugo', 'Gosso', 'ugo.gosso@telecomnancy.net', '0000', 50)");
                exec("INSERT INTO Users (userName, firstName, lastName, email, password, coins) VALUES ('julieZ', 'Julie', 'Zhen', 'julie.zhen@telecomnancy.net', '8888', 5000)");

                // Insert data into Offers
                exec("INSERT INTO offers (id, title, description, imagePath, price, user) VALUES ('1', 'Pelle à prêter', 'Une belle pelle à prêter', '/pelle.jpg', 10, 'joelD')");
                exec("INSERT INTO offers (id, title, description, imagePath, price, user) VALUES ('2', 'Machine à café à prêter', 'Une belle machine à café à prêter', '/pelle.jpg', 10, 'joelD')");
                exec("INSERT INTO offers (id, title, description, imagePath, price, user) VALUES ('2', 'Machine à thé à prêter', 'Une belle machine à thé à prêter', '/pelle.jpg', 10, 'joelD')");

                // Fetch data from table1
                ArrayList<ArrayList<Object>> dataTable1 = fetchAll("SELECT * FROM Users");
                System.out.println("Data from Users:");
                printData(dataTable1);

                // Fetch data from table2
                ArrayList<ArrayList<Object>> dataTable2 = fetchAll("SELECT * FROM Offers");
                System.out.println("Data from Offers:");
                printData(dataTable2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printData(ArrayList<ArrayList<Object>> data) {
        for (ArrayList<Object> row : data) {
            System.out.println(row);
        }
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }
}
