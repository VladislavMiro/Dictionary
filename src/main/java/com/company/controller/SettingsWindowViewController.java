package com.company.controller;

import com.company.model.DataBaseManger;
import com.company.model.XMLFile;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.sql.SQLException;
import java.util.Map;

public class SettingsWindowViewController {

    private String url = "";
    private String username = "";
    private String password = "";

    public String getUrl() { return url; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public boolean getSettings() {
        XMLFile file = new XMLFile("settings.xml");
        try {
            Map<String,String> config = file.readFile();
            if (!config.isEmpty()) {
                username = config.get("username");
                password = config.get("password");
                url = config.get("url");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() { return DataBaseManger.getDataBaseManager().isConnected(); }

    public boolean connectToDataBase() {
        if (isConnected()) {
            try {
                DataBaseManger.getDataBaseManager().connect();
                System.out.println("PostgreSQL JDBC Драйвер успешно подключен!");
                if (DataBaseManger.getDataBaseManager().isConnected()) {
                    System.out.println("База данных успешно подключена!");
                    return true;
                } else {
                    System.out.println("Ошибка при пожключении к базе данных!");
                    return false;
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                System.out.println("Ошибка SQL запроса!");
                return false;
            } catch (ClassNotFoundException classNotFoundException) {
                System.out.println("PostgreSQL JDBC Драйвер не найден!");
                classNotFoundException.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public void disconnectFromDataBase() {
        try {
            DataBaseManger.getDataBaseManager().disconnect();
            System.out.println("База данных отключена!");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при отключении базы данных!");
        }
    }

    public void setSettingsForDataBaseManager(String url, String username, String password) {
        DataBaseManger.getDataBaseManager().setConfig(url,username,password);
    }

    public boolean saveSettings(String url, String username, String password) {
        XMLFile file = new XMLFile("settings.xml");
        try {
            file.writeFile(username, password, url);
            return true;
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return false;
        }
    }

}
