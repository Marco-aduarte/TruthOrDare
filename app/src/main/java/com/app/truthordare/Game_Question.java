package com.app.truthordare;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class Game_Question extends Activity {

    private final String file = "color.txt";
    private String mode=null, option;
    private TextView txt, title;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_question);
        Intent intent = getIntent();
        mode = intent.getStringExtra(MainActivity.MODE);
        option = intent.getStringExtra(Game_Option.OPTION);
        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.game_question_view);
        view.setBackgroundColor(Color.parseColor(color));
        txt = findViewById(R.id.question);
        title = findViewById(R.id.option);
        next=findViewById(R.id.nextRound);
        Phrases phrases = new Phrases();
        Actions actions=null;
        try {
            actions = new Actions(((ArrayList<String>) getArray(phrases, "truth")), ((ArrayList<String>) getArray(phrases, "dare")), (ArrayList<Integer>) getEmojisArray(phrases));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        txt.setText(getQuestion(actions));
        title.setText("It's a "+option+"!");

        next.setOnClickListener(v -> nextRound(v));

    }

    private void nextRound(View v) {
        Intent question = new Intent(this,Game_Option.class);
        question.putExtra(MainActivity.MODE,getIntent().getStringExtra(MainActivity.MODE));
        startActivity(question);
    }

    private String getQuestion(Actions actions) {
        //comparar o option com truth or dare e retornar um get_truth or get_dare
        if(option.equals("Truth")) return actions.get_truth();
        return actions.get_dare();
    }

    private Object getEmojisArray(Phrases phrases) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return phrases.getClass().getMethod("get_"+mode+"_emojis").invoke(phrases);
    }

    private Object getArray(Phrases phrases, String option) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //comparar o type e o método
        Method method = phrases.getClass().getMethod("get_"+mode+"_"+option);
        return method.invoke(phrases);
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
