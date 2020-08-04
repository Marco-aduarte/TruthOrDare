package com.app.truthordare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Game_Question extends Activity {

    private final String file = "color.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_question);
        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.game_question_view);
        view.setBackgroundColor(Color.parseColor(color));
    }


    private String loadColor(){
        String color=null;
        try (InputStream is = openFileInput(file)){
            Scanner from = new Scanner(is);
            color=from.next();
            from.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return color;
    }

}
