package com.app.truthordare.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    int score = 0;
    String name;

    public Player(String name){
        this.name = name;
    }

    protected Player(Parcel in) {
        score = in.readInt();
        name = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public void add_score(){
        score++;
    }

    public int get_score(){
        return score;
    }

    public String getName(){
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(score);
        dest.writeString(name);
    }
}

