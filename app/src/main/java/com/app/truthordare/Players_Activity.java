package com.app.truthordare;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Players_Activity extends Activity {
    private final String file = "color.txt";
    private Button begin;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.playersView);
        view.setBackgroundColor(Color.parseColor(color));
        begin=findViewById(R.id.begin);
        begin.setOnClickListener(v -> startGame());

        listView = findViewById(R.id.listView);

        ArrayList<String> array = new ArrayList<>();

        array.add("Name");
        array.add("Name");
        array.add("Name");
        array.add("Name");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

        listView.setAdapter(arrayAdapter);

        

    }


    private void startGame() {
        Intent game = new Intent(this, Game_Option.class);
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
