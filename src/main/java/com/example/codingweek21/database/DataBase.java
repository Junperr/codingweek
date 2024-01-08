package com.example.codingweek21.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private String dbName;
    private static DataBase instance;

    private DataBase() {
        File dbFile = new File("pixou.db");
        if (!dbFile.exists()) {
            this.createDatabaseFile();
        }
        this.dbName = "pixou.db";
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
                exec("CREATE TABLE IF NOT EXISTS table1 (id INTEGER PRIMARY KEY, name TEXT, age INTEGER)");
                exec("CREATE TABLE IF NOT EXISTS table2 (id INTEGER PRIMARY KEY, title TEXT, author TEXT)");

                // Insert data into table1
                exec("INSERT INTO table1 (name, age) VALUES ('John Doe', 25)");

                // Insert data into table2
                exec("INSERT INTO table2 (title, author) VALUES ('Book Title', 'Author Name')");

                // Fetch data from table1
                List<List<Object>> dataTable1 = fetchAll("SELECT * FROM table1");
                System.out.println("Data from table1:");
                printData(dataTable1);

                // Fetch data from table2
                List<List<Object>> dataTable2 = fetchAll("SELECT * FROM table2");
                System.out.println("Data from table2:");
                printData(dataTable2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printData(List<List<Object>> data) {
        System.out.println("Data:");
        for (List<Object> row : data) {
            System.out.println(row);
        }
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }
}
