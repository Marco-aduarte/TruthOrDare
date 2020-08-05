package com.app.truthordare.Model;

import java.util.ArrayList;
import java.util.Random;

public class Actions{
    private ArrayList<Integer> emojis;
    private ArrayList<String> actualTruth;
    private ArrayList<String> actualDare;
    private ArrayList<String> usedTruth = new ArrayList<>();
    private ArrayList<String> usedDare = new ArrayList<>();


    public Actions(ArrayList<String> truth, ArrayList<String> dare, ArrayList<Integer> emojis){
        actualTruth = truth;
        actualDare = dare;
        this.emojis = emojis;
    }

    static int random(int min, int max){
        Random rdm = new Random();
        return rdm.nextInt(max - min) + min;
    }

    public String get_truth(){
        if(actualTruth.isEmpty()){
            actualTruth = usedTruth;
            usedTruth = new ArrayList<>();
        }
        int rand = -1;
        while(rand < 0 || rand > (actualTruth.size() - 1))
            rand = actualTruth.size() == 1 ? 0 : random(0,actualTruth.size());
        String phrase = actualTruth.get(rand);
        actualTruth.remove(rand);
        usedTruth.add(phrase);
        return phrase;
    }

    public String get_dare(){
        if (actualDare.isEmpty()){
            actualDare = usedDare;
            usedDare = new ArrayList<>();
        }
        int rand = -1;
        while(rand < 0 || rand > (actualDare.size() - 1))
            rand = actualDare.size() == 1 ? 0 : random(0, actualDare.size());
        String phrase = actualDare.get(rand);
        actualDare.remove(rand);
        usedDare.add(phrase);
        return phrase;
    }

    public int get_emoji(){
        int rand = random(0, emojis.size());
        return emojis.get(rand);
    }
}

