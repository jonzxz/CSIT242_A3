package com.example.csit242_a3;

public class Session {

    private int id;
    private String date;
    private String name;
    private int score;

    public Session() {

    }

    public Session(int id, String name, String date, int score) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.score = score;
    }

    public Session(String name, String date, int score) {
        this.name = name;
        this.date = date;
        this.score = score;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public int getScore() {
        return this.score;
    }

    public int getID() {
        return this.id;
    }
}
