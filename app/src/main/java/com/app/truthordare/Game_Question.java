package com.app.truthordare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.truthordare.Model.Actions;
import com.app.truthordare.Model.Phrases;
import com.app.truthordare.Model.PlayerScore;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game_Question extends Activity {

    private final String file = "color.txt";
    private String mode=null, option;
    private TextView txt, title;
    private Button next, add, add2;
    private ImageButton drink;
    private ImageView image;
    private PlayerScore playerScore;
    private Parcelable parcelable;
    private boolean isRunning = false;
    private long then=0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_question);

        Intent intent = getIntent();
        parcelable = intent.getParcelableExtra(Players_Activity.PLAYER);
        playerScore = Parcels.unwrap(parcelable);
        playerScore.setArray(intent.getParcelableArrayListExtra(Players_Activity.ARRAY));
        mode = intent.getStringExtra(MainActivity.MODE);
        option = intent.getStringExtra(Game_Option.OPTION);
        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.game_question_view);
        view.setBackgroundColor(Color.parseColor(color));
        txt = findViewById(R.id.question);
        title = findViewById(R.id.option);
        next = findViewById(R.id.nextRound);
        image = findViewById(R.id.Imagemoji_question);
        add = findViewById(R.id.addQuestion);
        add2 = findViewById(R.id.add2Question);
        drink = findViewById(R.id.drinkButton);

        Phrases phrases = new Phrases();
        Actions actions = null;
        try {
            actions = new Actions(((ArrayList<String>) getArray(phrases, "truth")), ((ArrayList<String>) getArray(phrases, "dare")), (ArrayList<Integer>) getEmojisArray(phrases));
            image.setImageResource(actions.get_emoji());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        txt.setText(getQuestion(actions));
        title.setText("It's a " + option + "!");

        //TODO: Fazer debug no nextRound (get_current_player)


        next.setOnClickListener(v -> {
            playerScore.get_current_player().add_score();
            nextRound();
        });
        add.setOnClickListener(v -> forfeitRound());
        add2.setOnClickListener(v -> forfeitRound());

        drink.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    drink.setBackgroundColor(Color.TRANSPARENT);
                    drink.setImageResource(R.drawable.beer_gif);
                    AnimationDrawable runningBeer = (AnimationDrawable) drink.getDrawable();
                    Log.d("Time", "ENTREI ");
                    if(!isRunning) {
                        runningBeer.start();
                        isRunning=true;
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 1200);
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    isRunning=false;
                    mHandler=null;
                    drink.setImageResource(R.drawable.frame_00);
                    return true;
                }
                return true;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    if (isRunning)
                        nextRound();
                }
            };
        });
    }

    //TODO: forfeit round -> passa para o proximo player sem dar pontos, avança game_option
    private void forfeitRound() {
        nextRound();
    }

    private void nextRound() {
        Intent question = new Intent(this,Game_Option.class);
        question.putExtra(MainActivity.MODE,getIntent().getStringExtra(MainActivity.MODE));
        question.putExtra(Players_Activity.PLAYER, parcelable);
        question.putParcelableArrayListExtra(Players_Activity.ARRAY,playerScore.getArray());
        startActivity(question);
    }

    private String getQuestion(Actions actions) {
        if(option.equals("Truth")) return actions.get_truth();
        return actions.get_dare();
    }

    private Object getEmojisArray(Phrases phrases) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return phrases.getClass().getMethod("get_"+mode+"_emojis").invoke(phrases);
    }

    private Object getArray(Phrases phrases, String option) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return phrases.getClass().getMethod("get_"+mode+"_"+option).invoke(phrases);
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
