package com.app.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.truthordare.Model.PlayerScore;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

//TODO: Adicionar rondas, toast para quando a lista de players tá vazia
public class Players_Activity extends Activity {
    private final String file = "color.txt";
    public static final String PLAYER = "com.app.truthordare.PLAYER", ARRAY = "com.app.truthordare.ARRAY";
    private Button begin, add, add2;
    private String mode;
    private LinearLayout layout;
    private ScrollView scroll;
    private LinkedList<EditText> list = new LinkedList<>();
    private PlayerScore playerScore;
    private Parcelable parcelable;


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
        add = findViewById(R.id.add);
        add2 = findViewById(R.id.add2);
        scroll=findViewById(R.id.players);

        //TODO: Mudar o parâmetro
        playerScore = new PlayerScore(3);
        parcelable = Parcels.wrap(playerScore);

        begin.setOnClickListener(v -> startGame());

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        init();

        add.setOnClickListener(v -> addEditText());
        add2.setOnClickListener(v -> addEditText());
    }

    private void init() {
        EditText text = new EditText(this);
        EditText text2 = new EditText(this);
        text.setHint("Name");
        text.setTextColor(Color.WHITE);
        text.setTextSize(25);
        text.setHintTextColor(Color.WHITE);
        text.setTypeface(Typeface.create("Lato",Typeface.BOLD));
        text2.setHint("Name");
        text2.setTextColor(Color.WHITE);
        text2.setTextSize(25);
        text2.setHintTextColor(Color.WHITE);
        text2.setTypeface(Typeface.create("Lato",Typeface.BOLD));
        list.add(text);
        list.add(text2);
        layout.addView(text);
        layout.addView(text2);
        scroll.addView(layout);
        scroll.invalidate();
    }

    private void addEditText(){
        EditText newText = new EditText(this);
        newText.setHint("Name");
        newText.setHintTextColor(Color.WHITE);
        newText.setTypeface(Typeface.create("Lato",Typeface.BOLD));
        newText.setTextColor(Color.WHITE);
        newText.setTextSize(25);
        list.add(newText);
        layout.addView(newText);
    }

    private void startGame() {
        Intent game = new Intent(this, Game_Option.class);
        game.putExtra(MainActivity.MODE, mode);
        savePlayers();
        game.putExtra(PLAYER,parcelable);
        game.putParcelableArrayListExtra(ARRAY,playerScore.getArray());
        startActivity(game);
    }

    private void savePlayers() {
        ArrayList<String> names = new ArrayList<>();
        //adicionar os player que tem nome diferente de "Name"
        for( EditText text : list ) {
            String name = String.valueOf(text.getText());
            if (!name.equals(""))
                names.add(name);
        }
        playerScore.add_all_players(names);
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
