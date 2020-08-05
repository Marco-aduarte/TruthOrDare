package com.app.truthordare;

import java.util.ArrayList;
import java.util.Hashtable;

public class PlayerScore {
    private Hashtable <String, Players> scoreBoard;
    private ArrayList <Players> playersArray;

    public PlayerScore(){
        scoreBoard = new Hashtable<>();
        playersArray = new ArrayList<>();
    }
    public void add_player(Players player){
        scoreBoard.put(player.getName(), player);
        playersArray.add(player);
    }
    public Players get_player(String player_name){
        return scoreBoard.get(player_name);
    }

    private boolean less(Players x, Players y){
        return x.get_score() < y.get_score();
    }

    public ArrayList<Players> sort_players() {
        int len = playersArray.size();
        int max;
        for (int i = 0; i < len - 1; i++){
            max = i + 1;
            for(int j = i + 1; j < len;j++){
                if(less(playersArray.get(max), playersArray.get(j)))
                    max = j;
            }
            if(less(playersArray.get(i), playersArray.get(max))) {
                Players aux = playersArray.get(i);
                playersArray.set(i, playersArray.get(max));
                playersArray.set(max, aux);
            }
        }
        return playersArray;
    }
}
