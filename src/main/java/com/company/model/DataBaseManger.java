package com.company.model;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseManger {

    private static DataBaseManger shared = new DataBaseManger();
    private static String url;
    private static String username;
    private static String password;
    private static Connection connection = null;

    public static  DataBaseManger getDataBaseManager() {
        if(shared == null) {
            shared = new DataBaseManger();
        }

        return shared;
    }

    private DataBaseManger() {}

    public void setConfig(String url, String username, String password) {
        DataBaseManger.url = url;
        DataBaseManger.username = username;
        DataBaseManger.password = password;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws SQLException, ClassNotFoundException {
        System.out.println("Тестирование подключения к базе данных:");
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, username, password);
    }

    public ArrayList<Word> selectRequest(String key) throws SQLException {
        ArrayList<Word> array = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM dict WHERE dict.word ILIKE ?;");
        statement.setString(1, "%" + key + "%");
        ResultSet request = statement.executeQuery();

        while (request.next()) {
            array.add(new Word(request.getInt(1), request.getString(2), request.getString(3)));
        }

        return array;
    }

    public void insertRequest(Word word) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO dict(word,mean) VALUES(?,?);");
        statement.setString(1,word.getWord());
        statement.setString(2, word.getDescription());
        statement.executeUpdate();
    }

    public void updateRequest(Word word) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE dict SET word=?, mean=? WHERE dict.id=?;");
        statement.setString(1, word.getWord());
        statement.setString(2, word.getDescription());
        statement.setInt(3, word.getId());
        statement.executeUpdate();
    }

    public void deleteRequest(Word word) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM dict WHERE dict.id=?;");
        statement.setInt(1, word.getId());
        statement.executeUpdate();
    }

    public void disconnect() throws SQLException { connection.close(); }

}
