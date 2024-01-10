package com.example.codingweek.database;

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

    public ArrayList<String> fetchUser(String query, Object... args) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add((String) resultSet.getObject(i));
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
                exec("CREATE TABLE IF NOT EXISTS Users (firstName TEXT, lastName TEXT, userName TEXT PRIMARY KEY, email TEXT, address TEXT, zipCode TEXT, city TEXT, password TEXT, coins TEXT)");
                exec("CREATE TABLE IF NOT EXISTS Offers (id UUID PRIMARY KEY, title TEXT, user TEXT, description TEXT, imagePath TEXT, price INTEGER, FOREIGN KEY(user) REFERENCES Users(id), availability BOOL)");
                exec("CREATE TABLE IF NOT EXISTS Categories (offer UUID, category TEXT, FOREIGN KEY(offer) REFERENCES Offers(id),UNIQUE(offer, category))");
                exec("create table if not exists Orders (id UUID PRIMARY KEY, cost INTEGER, FOREIGN KEY(buyer) REFERENCES Users(userName), FOREIGN KEY(seller) REFERENCES Users(userName))");
                exec("create table if not exists Marks (id UUID PRIMARY KEY, mark INTEGER, FOREIGN KEY(order) REFERENCES Orders(id))");

                // Insert data into Users
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins) VALUES ('', '', 'admin', '', '', '', '', '', '100000000')");
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins) VALUES ('Anna', 'Galkowski', 'annaG', 'anna.galkowski@telecomnancy.net', 'address1', '33000', 'city1', '12345678', '1000')");
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins) VALUES ('Joel', 'Duhem', 'joelD', 'joel.duhem@telecomnancy.net', 'address2', '59000', 'city1', '27042704', '10')");
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins) VALUES ('Ugo', 'Gosso', 'ugoG', 'ugo.gosso@telecomnancy.net', 'address3', '25000', 'city1', '00000000', '9000')");
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins) VALUES ('Julie', 'Zhen', 'julieZ', 'julie.zhen@telecomnancy.net', 'address4', '75000', 'city1', '88888888', '5000')");

                // Insert data into Offers
                exec("INSERT INTO offers (id, title, description, imagePath, price, user, availability) VALUES ('1', 'Pelle à prêter', 'Une belle pelle à prêter', 'pelle.jpg', 10, 'joelD', 1)");
                exec("INSERT INTO offers (id, title, description, imagePath, price, user, availability) VALUES ('2', 'Machine à café à prêter', 'Une belle machine à café à prêter', 'pelle.jpg', 100, 'joelD', 1)");
                exec("INSERT INTO offers (id, title, description, imagePath, price, user, availability) VALUES ('3', 'Machine à thé à prêter', 'Une belle machine à thé à prêter', 'pelle.jpg', 50, 'joelD', 1)");

                // Insert data into Categories
                exec("INSERT INTO Categories (offer, category) VALUES ('1', 'Jardinage')");
                exec("INSERT INTO Categories (offer, category) VALUES ('1', 'Jardin')");
                exec("INSERT INTO Categories (offer, category) VALUES ('1', 'Outils')");
                exec("INSERT INTO Categories (offer, category) VALUES ('2', 'Electroménager')");
                exec("INSERT INTO Categories (offer, category) VALUES ('3', 'Electroménager')");


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