package com.app.truthordare;

public class Player {
    private int score = 0;
    private String name;

    public Player(String name){
        this.name = name;
    }

    public void add_score(){
        score++;
    }

    public int get_score(){
        return score;
    }

    public String getName(){
        return name;
    }
}
