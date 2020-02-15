package com.example.csit242_a3;

// Question class for Quiz
public class Question {

    private int x;
    private int y;
    private int answer;
    private char symbol;

    public Question(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.setAnswer();
    }


    // Getter / Setters
    public char getSymbol() {
        return this.symbol;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {
        return (String.format("%d %c %d", x, symbol, y));
    }

    public int getAnswer() {
        return this.answer;
    }

    private void setAnswer() {
        if (this.symbol == '+') {
            this.answer = x + y;
        } else if (this.symbol == '-') {
            this.answer = x - y;
        } else if (this.symbol == '×') {
            this.answer = x * y;
        } else if (this.symbol == '÷') {
            this.answer = x/y;
        }
    }

    // For debugging purpose because I might have terrible math
    public String toStringAnswer() {
        return (String.format("%d %c %d = %d", x, symbol, y, answer));
    }
}
