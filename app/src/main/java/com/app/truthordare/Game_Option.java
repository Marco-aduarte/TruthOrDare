package com.app.truthordare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Game_Option extends Activity {

    private final String file = "color.txt";
    private Button truth, dare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_option);
        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.game_option_view);
        view.setBackgroundColor(Color.parseColor(color));
        truth=findViewById(R.id.truth);
        dare=findViewById(R.id.dare);

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
