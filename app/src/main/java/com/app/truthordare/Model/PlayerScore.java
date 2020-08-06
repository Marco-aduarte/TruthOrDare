package com.app.truthordare.Model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

@org.parceler.Parcel
public class PlayerScore implements Parcelable {
    ArrayList <Player> playersArray=null;
    int current_player = 0;
    int rounds;
    int current_round = 1;

    public PlayerScore(){
    }

    public PlayerScore(int rounds){
        playersArray = new ArrayList<>();
        this.rounds = rounds;
    }


    protected PlayerScore(Parcel in) {
        current_player = in.readInt();
        rounds = in.readInt();
        current_round = in.readInt();
    }

    public static final Creator<PlayerScore> CREATOR = new Creator<PlayerScore>() {
        @Override
        public PlayerScore createFromParcel(Parcel in) {
            return new PlayerScore(in);
        }

        @Override
        public PlayerScore[] newArray(int size) {
            return new PlayerScore[size];
        }
    };

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

    public Player get_current_player(){return playersArray.get(current_player);}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(current_player);
        dest.writeInt(rounds);
        dest.writeInt(current_round);
    }

    public ArrayList<Player> getArray() {
        return playersArray;
    }

    public void setArray(ArrayList<Player> array){
        this.playersArray=array;
    }

}
