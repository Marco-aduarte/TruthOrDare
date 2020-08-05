package com.app.truthordare;

public class Players {
    private int score = 0;
    private String name;

    public Players(String name){
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

