package com.app.truthordare.Model;

import java.util.ArrayList;
import java.util.Hashtable;

public class PlayerScore {
    private ArrayList <Player> playersArray;
    private int current_player = 0;
    private int rounds;
    private int current_round = 1;

    public PlayerScore(int rounds){
        playersArray = new ArrayList<>();
        this.rounds = rounds;
    }
    private void add_player(Player player){
        playersArray.add(player);
    }

    public void add_all_players(ArrayList<String> players){
        for (String player : players)
            add_player(new Player(player));
    }

    public Player next_Player(){
        if(current_player == playersArray.size()) {
            current_player = 0;
            current_round++;
        }
        if(current_round > rounds)
            return null;
        return playersArray.get(current_player++);
    }

    public int get_round(){
        return current_round;
    }

    private boolean less(Player x, Player y){
        return x.get_score() < y.get_score();
    }

    public ArrayList<Player> sort_players() {
        int len = playersArray.size();
        int max;
        for (int i = 0; i < len - 1; i++){
            max = i + 1;
            for(int j = i + 1; j < len;j++){
                if(less(playersArray.get(max), playersArray.get(j)))
                    max = j;
            }
            if(less(playersArray.get(i), playersArray.get(max))) {
                Player aux = playersArray.get(i);
                playersArray.set(i, playersArray.get(max));
                playersArray.set(max, aux);
            }
        }
        return playersArray;
    }
}
