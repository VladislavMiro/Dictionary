package com.company.controller;

import com.company.model.DataBaseManger;
import com.company.model.Word;

import java.sql.SQLException;

public class EditWordWindowController {

    public boolean editWord(int idOfWord, String word, String description) {
        if (DataBaseManger.getDataBaseManager().isConnected()) {
            try {
                DataBaseManger.getDataBaseManager().updateRequest(new Word(idOfWord, word, description));
                System.out.println("Запись была успешно изменена!");
                return true;
            } catch (SQLException e) {
                System.out.println("Запись не изменена успешно!");
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

}
