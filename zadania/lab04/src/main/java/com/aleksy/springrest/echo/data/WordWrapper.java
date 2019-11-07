package com.aleksy.springrest.echo.data;

public class WordWrapper {
    public WordWrapper(String word) {
        this.word = word;
    }

    public WordWrapper() {}

    private String word;

    public String getWord() {
        return word;
    }
}
