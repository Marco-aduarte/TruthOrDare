package com.app.truthordare;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class Game_Option extends Activity {

    private final String file = "color.txt";
    private Button truth, dare;
    private ImageView image;
    public static final String OPTION = "com.app.truthordare.OPTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_option);
        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.game_option_view);
        view.setBackgroundColor(Color.parseColor(color));
        truth=findViewById(R.id.truth);
        dare=findViewById(R.id.dare);
        image = findViewById(R.id.Imagemoji_option);
        truth.setOnClickListener(v -> startGameQuestion(v, "Truth"));
        dare.setOnClickListener(v -> startGameQuestion(v, "Dare"));

    }

    private void startGameQuestion(View v, String option) {
        Intent question = new Intent(this,Game_Question.class);
        question.putExtra(OPTION,option);
        question.putExtra(MainActivity.MODE,getIntent().getStringExtra(MainActivity.MODE));
        startActivity(question);
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
