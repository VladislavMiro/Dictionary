package com.company.model;

public class Word {
    private int id;
    private final String word;
    private final String description;

    public Word(int id, String word, String description) {
        this.id = id;
        this.word = word;
        this.description = description;
    }

    public Word(String word, String description) {
        this.word = word;
        this.description = description;
    }

    public int getId() { return id; }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

}
