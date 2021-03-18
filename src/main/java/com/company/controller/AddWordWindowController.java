package com.company.controller;

import com.company.model.DataBaseManger;
import com.company.model.Word;

import java.sql.SQLException;

public class AddWordWindowController {

    public boolean addWord(String word, String description) {
        word = "'" + word + "'";
        description = "'" + description + "'";
        if (DataBaseManger.getDataBaseManager().isConnected()) {
            try {
                DataBaseManger.getDataBaseManager().insertRequest(new Word(word, description));
                System.out.println("Слово было эдобавлено");
                return true;
            } catch (SQLException e) {
                System.out.println("Слово не было добавлено!");
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

}
