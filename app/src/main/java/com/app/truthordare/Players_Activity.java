package com.app.truthordare;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Players_Activity extends Activity {
    private final String file = "color.txt";
    private Button begin;
    private String mode;
    private LinearLayout layout = new LinearLayout(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        Intent intent = getIntent();
        mode = intent.getStringExtra(MainActivity.MODE);
        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.playersView);
        view.setBackgroundColor(Color.parseColor(color));
        begin=findViewById(R.id.begin);
        begin.setOnClickListener(v -> startGame());

        init();

        //TODO: Adicionar editText de acordo com o selecionado no botão
        /*
        <LinearLayout
            editText
            editText

        </LinearLayout>
         */
    }

    private void init() {

    }


    private void addEditText(){

    }

    private void startGame() {
        Intent game = new Intent(this, Game_Option.class);
        game.putExtra(MainActivity.MODE, mode);
        startActivity(game);
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
