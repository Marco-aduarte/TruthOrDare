package com.app.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.app.truthordare.Model.PlayerScore;
import org.parceler.Parcels;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Players_Activity extends Activity {
    private final String file = "color.txt";
    public static final String PLAYER = "com.app.truthordare.PLAYER", ARRAY = "com.app.truthordare.ARRAY", FLAG = "com.app.truthordare.FLAG";
    private Button begin, add, add2, back, rules, drinkMode, rounds;
    private TextView roundText;
    private String mode, language;
    private LinearLayout layout;
    private ScrollView scroll;
    private LinkedList<EditText> list = new LinkedList<>();
    private PlayerScore playerScore;
    private Parcelable parcelable;
    private int value=0;
    private ImageView drinkImg, infiniteImg;
    private boolean flag, menuIsOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        Intent intent = getIntent();
        mode = intent.getStringExtra(MainActivity.MODE);
        language = intent.getStringExtra(MainActivity.LANGUAGE);

        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.playersView);
        view.setBackgroundColor(Color.parseColor(color));

        begin=findViewById(R.id.begin);
        add = findViewById(R.id.add);
        add2 = findViewById(R.id.add2);
        scroll=findViewById(R.id.players);
        back = findViewById(R.id.back_players);
        rules = findViewById(R.id.rules);
        drinkMode = findViewById(R.id.drinkMode);
        rounds= findViewById(R.id.roundsButton);
        drinkImg = findViewById(R.id.drinkActivate);
        roundText = findViewById(R.id.roundsNumber);
        infiniteImg = findViewById(R.id.imageView2);

        infiniteImg.setVisibility(View.VISIBLE);
        menuIsOpen=false;
        flag=false;
        drinkImg.setVisibility(View.INVISIBLE);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        init();

        begin.setOnClickListener(v -> startGame());
        add.setOnClickListener(v -> addEditText());
        add2.setOnClickListener(v -> addEditText());
        back.setOnClickListener(v -> goBack());

        rules.setOnClickListener(v -> menu());
        drinkMode.setOnClickListener(v -> drinkMode());
        rounds.setOnClickListener(v -> updateRounds());
    }

    private void menu() {
        if(!menuIsOpen) {
            drinkMode.setVisibility(View.VISIBLE);
            rounds.setVisibility(View.VISIBLE);
        }else{
            drinkMode.setVisibility(View.GONE);
            rounds.setVisibility(View.GONE);
        }
        menuIsOpen=!menuIsOpen;
    }

    private void updateRounds() {
        if(value==30){
            infiniteImg.setVisibility(View.VISIBLE);
            roundText.setVisibility(View.GONE);
            value=0;
        }
        else{
            roundText.setVisibility(View.VISIBLE);
            infiniteImg.setVisibility(View.INVISIBLE);
            value+=10;
            roundText.setText(String.valueOf(value));
        }
    }

    private void drinkMode() {
        if(flag) drinkImg.setVisibility(View.INVISIBLE);
        else drinkImg.setVisibility(View.VISIBLE);
        flag=!flag;
    }

    private void goBack() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    private void init() {
        int maxLength = 12;
        EditText text = new EditText(this);
        EditText text2 = new EditText(this);
        text.setHint("Name");
        text.setTextColor(Color.WHITE);
        text.setMaxLines(1);
        text.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        text.setTextSize(25);
        text.setHintTextColor(Color.WHITE);
        text.setTypeface(Typeface.create("Lato",Typeface.BOLD));
        text2.setHint("Name");
        text2.setTextColor(Color.WHITE);
        text2.setMaxLines(1);
        text2.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
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
        int maxLength = 12;
        EditText newText = new EditText(this);
        newText.setHint(getString(R.string.hint_name));
        newText.setHintTextColor(Color.WHITE);
        newText.setTypeface(Typeface.create("Lato",Typeface.BOLD));
        newText.setTextColor(Color.WHITE);
        newText.setTextSize(25);
        newText.setMaxLines(1);
        newText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        list.add(newText);
        layout.addView(newText);
    }

    private void startGame() {
        if(!savePlayers()) return;
        Intent game = new Intent(this, Game_Option.class);
        game.putExtra(MainActivity.MODE, mode);
        game.putExtra(PLAYER,parcelable);
        game.putExtra(FLAG, flag);
        game.putParcelableArrayListExtra(ARRAY,playerScore.getArray());
        game.putExtra(MainActivity.LANGUAGE, language);
        startActivity(game);
    }

    private boolean savePlayers() {
        if(value==0) value = -1;
        playerScore = new PlayerScore(value);
        parcelable = Parcels.wrap(playerScore);

        ArrayList<String> names = new ArrayList<>();
        for( EditText text : list ) {
            String name = String.valueOf(text.getText());
            if (!name.equals(""))
                names.add(name);
        }
        if(playerScore.isEmpty(names)){
            Toast.makeText(this,"Add Player",Toast.LENGTH_SHORT).show();
            return false;
        }
        playerScore.add_all_players(names);
        return true;
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
