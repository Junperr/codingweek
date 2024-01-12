package com.example.codingweek.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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

    public HashMap<String, Object> fetchOneMap(String query, Object... args) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            HashMap row = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                row.put(metaData.getColumnLabel(i), resultSet.getObject(i));
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

    public ArrayList<HashMap<String, Object>> fetchAllMap(String query, Object... args) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setParameters(preparedStatement, args);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                HashMap row = new HashMap();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
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
                exec("CREATE TABLE IF NOT EXISTS Users (firstName TEXT, lastName TEXT, userName TEXT PRIMARY KEY, email TEXT, address TEXT, zipCode TEXT, city TEXT, password TEXT, coins TEXT, averageEval TEXT)");
                exec("CREATE TABLE IF NOT EXISTS Offers (id UUID PRIMARY KEY, title TEXT, type TEXT, user TEXT, description TEXT, imagePath TEXT, price INTEGER, availability BOOL, FOREIGN KEY(user) REFERENCES Users(username))");
                exec("CREATE TABLE IF NOT EXISTS Categories (offer UUID, category TEXT, FOREIGN KEY(offer) REFERENCES Offers(id),UNIQUE(offer, category))");
                exec("create table if not exists Orders (id UUID PRIMARY KEY, offer UUID, cost INTEGER, buyer TEXT, seller TEXT)");
//                exec("create table if not exists Reviews (id UUID PRIMARY KEY, eval INTEGER, writer TEXT, description TEXT, order UUID)");
                exec("CREATE TABLE IF NOT EXISTS Reviews (orderId UUID, writer TEXT, eval INTEGER, description TEXT,FOREIGN KEY(orderId) REFERENCES Orders(id),UNIQUE(orderId, writer))");

                // Insert data into Users
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins, averageEval) VALUES ('', '', 'admin', '', '', '', '', '', '100000000', '5')");
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins, averageEval) VALUES ('Anna', 'Galkowski', 'annaG', 'anna.galkowski@telecomnancy.net', 'address1', '33000', 'city1', '', '1000', '-1')");
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins, averageEval) VALUES ('Joel', 'Duhem', 'joelD', 'joel.duhem@telecomnancy.net', 'address2', '59000', 'city1', '', '0', '1')");
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins, averageEval) VALUES ('Ugo', 'Gosso', 'ugoG', 'ugo.gosso@telecomnancy.net', 'address3', '25000', 'city1', '', '9000', '4')");
                exec("INSERT INTO Users (firstName, lastName, userName, email, address , zipCode , city, password, coins, averageEval) VALUES ('Julie', 'Zhen', 'julieZ', 'julie.zhen@telecomnancy.net', 'address4', '75000', 'city1', '', '5000', '-1')");

                // Insert data into Offers
                exec("INSERT INTO Offers (id, title, type, description, imagePath, price, user, availability) VALUES ('6cc0106a-8d73-4ead-935e-971d00196e6f', 'Pelle à prêter', 'Loan', 'Une belle pelle à prêter', 'offers/pelle.jpg', 10, 'joelD', 'true')");
                exec("INSERT INTO Offers (id, title, type, description, imagePath, price, user, availability) VALUES ('337bc42e-1a1a-4237-a45d-a66d3da4ed03', 'Machine à café à prêter', 'Loan', 'Une belle machine à café à prêter', 'offers/pelle.jpg', 30, 'joelD', 'false')");
                exec("INSERT INTO Offers (id, title, type, description, imagePath, price, user, availability) VALUES ('cfe6e949-e07f-4b87-a0e6-0715db4da09a', 'Machine à thé à prêter', 'Loan', 'Une belle machine à thé à prêter', 'offers/pelle.jpg', 50, 'joelD', 'true')");
                exec("INSERT INTO Offers (id, title, type, description, imagePath, price, user, availability) VALUES ('f3925355-d605-4f40-b012-829adb56738c', 'Cours particulier', 'Service', 'Donne cours de maths pas cher', 'default.png', 200,'annaG', 'true')");
                exec("INSERT INTO Offers (id, title, type, description, imagePath, price, user, availability) VALUES ('c307e79f-de27-4803-b3f4-48007300a43f', 'Cours de natation', 'Service', 'Donne cours de natation pas cher', 'default.png', 50,'ugoG', 'false')");

                // Insert data into Categories
                exec("INSERT INTO Categories (offer, category) VALUES ('6cc0106a-8d73-4ead-935e-971d00196e6f', 'Jardinage')");
                exec("INSERT INTO Categories (offer, category) VALUES ('6cc0106a-8d73-4ead-935e-971d00196e6f', 'Jardin')");
                exec("INSERT INTO Categories (offer, category) VALUES ('6cc0106a-8d73-4ead-935e-971d00196e6f', 'Outils')");
                exec("INSERT INTO Categories (offer, category) VALUES ('337bc42e-1a1a-4237-a45d-a66d3da4ed03', 'Electroménager')");
                exec("INSERT INTO Categories (offer, category) VALUES ('cfe6e949-e07f-4b87-a0e6-0715db4da09a', 'Electroménager')");
                exec("INSERT INTO Categories (offer, category) VALUES" +
                        "('f3925355-d605-4f40-b012-829adb56738c', 'Cours')," +
                        "('f3925355-d605-4f40-b012-829adb56738c', 'Maths')," +
                        "('c307e79f-de27-4803-b3f4-48007300a43f', 'Cours')," +
                        "('c307e79f-de27-4803-b3f4-48007300a43f', 'Natation')");


                // Insert data into Orders
                exec("INSERT INTO Orders (id, offer, cost, buyer, seller) VALUES " +
                        "('fff862f5-dbd3-4c41-ac9a-b778fd2ed222', '337bc42e-1a1a-4237-a45d-a66d3da4ed03', 30, 'annaG', 'joelD')," +
                        "('ec8f59cc-15df-4207-ab46-12789fc97e8d', 'c307e79f-de27-4803-b3f4-48007300a43f', 50, 'joelD', 'ugoG')");

                // Insert data into Evals
                exec("INSERT INTO Reviews (orderId, eval, writer, description) VALUES " +
                        "('fff862f5-dbd3-4c41-ac9a-b778fd2ed222', 1, 'annaG', 'Great service hahahaha...')," +
                        "('ec8f59cc-15df-4207-ab46-12789fc97e8d', 4, 'joelD', 'Had fun !')");


                // Fetch data from Users
                ArrayList<ArrayList<Object>> dataTable1 = fetchAll("SELECT * FROM Users");
                System.out.println("Data from Users:");
                printData(dataTable1);

                // Fetch data from Offers
                ArrayList<ArrayList<Object>> dataTable2 = fetchAll("SELECT * FROM Offers");
                System.out.println("Data from Offers:");
                printData(dataTable2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        try {
            // Clear all data from relevant tables
            exec("DELETE FROM Categories");
            exec("DELETE FROM Offers");
            exec("DELETE FROM Users");
            exec("DELETE FROM Orders");
            exec("DELETE FROM Reviews");

//            // Insert default data
//            createDatabaseFile(); // Assuming this method exists in your class

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
        createDatabaseFile();
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