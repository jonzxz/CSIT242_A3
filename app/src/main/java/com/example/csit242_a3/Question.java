package com.example.csit242_a3;

public class Question {

    private int id;
    private int x;
    private int y;
    private int answer;
    private char symbol;
    private String category;


    public Question() {

    }

    public Question(int id, int x, int y, int answer, char symbol, String category) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.answer = answer;
        this.symbol = symbol;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

}
