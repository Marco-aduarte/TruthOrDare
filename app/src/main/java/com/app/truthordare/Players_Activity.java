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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Players_Activity extends Activity {
    private final String file = "color.txt";
    private Button begin, add, add2;
    private String mode;
    private LinearLayout layout;
    private ScrollView scroll;
    private LinkedList<EditText> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        Intent intent = getIntent();
        mode = intent.getStringExtra(MainActivity.MODE);
        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.playersView);
        view.setBackgroundColor(Color.parseColor(color));
        begin=findViewById(R.id.begin);
        add = findViewById(R.id.add);
        add2 = findViewById(R.id.add2);
        scroll=findViewById(R.id.players);
        begin.setOnClickListener(v -> startGame());

        init();
        //TODO: criar editTexts que começam com Name

        add.setOnClickListener(v -> addEditText());
        add2.setOnClickListener(v -> addEditText());
    }

    private void init() {
        EditText text = new EditText(this);
        EditText text2 = new EditText(this);
        list.add(text);
        list.add(text2);
        layout.addView(text);
        layout.addView(text2);
        scroll.addView(layout);
        scroll.invalidate();
    }

    private void addEditText(){
        EditText newText = new EditText(this);
        list.add(newText);
        layout.addView(newText);
    }

    private void startGame() {
        Intent game = new Intent(this, Game_Option.class);
        game.putExtra(MainActivity.MODE, mode);
        savePlayers();
        startActivity(game);
    }

    private void savePlayers() {
        ArrayList<String> players = new ArrayList<>();
        //TODO: guardar todos os nomes dos players que tem nome diferente de "Name"
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
