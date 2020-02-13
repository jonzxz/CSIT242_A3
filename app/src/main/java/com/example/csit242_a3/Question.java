package com.example.csit242_a3;

public class Question {

    private int x;
    private int y;
    private double answer;
    private char symbol;

    public Question(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.setAnswer();
    }


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

    private void setAnswer() {
        if (this.symbol == '+') {
            this.answer = x + y;
        } else if (this.symbol == '-') {
            this.answer = x - y;
        } else if (this.symbol == '*') {
            this.answer = x * y;
        } else if (this.symbol == '/') {
            this.answer = (double)x/y;
        }
    }

    public String toStringAnswer() {
        if (this.symbol == '/') {
            return (String.format("%d %c %d = %.2f", x, symbol, y, answer));
        }
        return (String.format("%d %c %d = %.0f", x, symbol, y, answer));
    }
}
