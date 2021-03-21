package com.company.model;

import java.sql.*;
import java.util.ArrayList;

enum Request {
    INSERT,
    SELECT,
    UPDATE,
    DELETE;

    @Override
    public String toString() {
        return switch (this) {
            case SELECT -> "SELECT * FROM dict WHERE dict.word ILIKE ";
            case INSERT -> "INSERT INTO dict(word,mean) VALUES(";
            case UPDATE -> "UPDATE dict SET ";
            case DELETE -> "DELETE FROM dict WHERE dict.id=";
        };
    }
}

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
        Statement statement = connection.createStatement();
        String req = Request.SELECT.toString() + "'%" + key + "%'" + ";";
        ResultSet request = statement.executeQuery(req);
        while (request.next()) {
            array.add(new Word(request.getInt(1), request.getString(2), request.getString(3)));
        }
        return array;
    }

    public void insertRequest(Word word) throws SQLException {
        Statement statement = connection.createStatement();
        String request = Request.INSERT.toString() +
                "'"+word.getWord()+"', '"+word.getDescription()+"');";
        statement.executeUpdate(request);
    }

    public void updateRequest(Word word) throws SQLException {
        Statement statement = connection.createStatement();
        String request = Request.UPDATE.toString() + "word='" +
                word.getWord() + "', mean='" +
                word.getDescription() + "' WHERE dict.id=" + word.getId() + ";";
        statement.executeUpdate(request);
    }

    public void deleteRequest(Word word) throws SQLException {
        Statement statement = connection.createStatement();
        String request = Request.DELETE.toString() + word.getId() + ";";
        statement.executeUpdate(request);
    }

    public void disconnect() throws SQLException { connection.close(); }

}
