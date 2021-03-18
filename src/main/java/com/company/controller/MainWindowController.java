package com.company.controller;

import com.company.model.DataBaseManger;
import com.company.model.Word;
import com.company.model.XMLFile;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;

public class MainWindowController {

    private Vector<Word> arrayOfWords = new Vector<>();
    private final Vector<String> namesVector = new Vector<>();

    public Vector<Word> getArrayOfWords() {
        return arrayOfWords;
    }

    public Vector<String> getNamesArray() { return namesVector; }

    public MainWindowController() {
    }

    public boolean connectToDataBase() {
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
    }

    public boolean disconnectFromDataBase() {
        if (isConnected())
            try {
                DataBaseManger.getDataBaseManager().disconnect();
                System.out.println("База данных отключена!");
                return true;
            } catch (SQLException e) {
                System.out.println("Произошла ошибка при отключении базы данных!");
                return false;
            }
            else {
                return false;
        }
    }

    public boolean isConnected() { return DataBaseManger.getDataBaseManager().isConnected(); }

    public boolean searchWord(String key) {
        key = "'%" + key + "%'";
        try {
            namesVector.clear();
            arrayOfWords = DataBaseManger.getDataBaseManager().selectRequest(key);
            arrayOfWords.forEach(word -> namesVector.add(word.getWord()));
            if (!arrayOfWords.isEmpty()) {
                System.out.println("Запись успешно найдена!");
                return true;
            } else {
                System.out.println("Запись не найдена!");
                return false;
            }
        } catch (SQLException e) {
            namesVector.clear();
            arrayOfWords.clear();
            System.out.println("Ошибка SQL запроса!");
            return false;
        }
    }

    public boolean deleteWord(int index) {
        if (DataBaseManger.getDataBaseManager().isConnected()){
            try {
                DataBaseManger.getDataBaseManager().deleteRequest(arrayOfWords.get(index));
                System.out.println("Запись была успешно удалена!");
                return true;
            } catch (SQLException e) {
                System.out.println("Запись не была удалена!");
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Нет соединения с базой данных!");
            return false;
        }
    }

    public boolean configureSettings() {
        try {
            XMLFile file = new XMLFile("settings.xml");
            Map<String, String> config = file.readFile();
            DataBaseManger.getDataBaseManager().setConfig(config.get("url"),
                    config.get("username"), config.get("password"));
            return true;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return false;
        }
    }

}
