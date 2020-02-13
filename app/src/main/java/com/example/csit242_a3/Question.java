package com.example.csit242_a3;

public class Question {

    private int id;
    private int x;
    private int y;
    private int answer;
    private String category;


    public Question() {

    }

    public Question(int id, int x, int y, int answer, String category) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.answer = answer;
        this.category = category;
    }

    public Question(int x, int y, int answer, String category) {
        this.x = x;
        this.y = y;
        this.answer = answer;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getAnswer() {
        return this.answer;
    }

    public String getCategory() {
        return this.category;
    }


}
