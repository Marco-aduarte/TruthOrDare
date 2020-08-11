package com.app.truthordare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    private Button next, forfeitButton, forfeitRound2, back, settings;
    private ImageButton drink;
    private ImageView image;
    private PlayerScore playerScore;
    private Parcelable parcelable;
    private boolean isRunning = false, flag;

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
        flag = intent.getBooleanExtra(Players_Activity.FLAG, false);
        option = intent.getStringExtra(Game_Option.OPTION);

        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.game_question_view);
        view.setBackgroundColor(Color.parseColor(color));

        txt = findViewById(R.id.question);
        title = findViewById(R.id.option);
        next = findViewById(R.id.nextRound);
        image = findViewById(R.id.Imagemoji_question);
        forfeitButton = findViewById(R.id.addQuestion);
        forfeitRound2 = findViewById(R.id.add2Question);
        drink = findViewById(R.id.drinkButton);
        back = findViewById(R.id.buttonPopup_question);
        settings = findViewById(R.id.settings);

        gifVisibility();
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

        //TODO: Fazer debug no nextRound(get_current_player)
        next.setOnClickListener(v -> {
            playerScore.get_current_player().add_score();
            nextRound();
        });

        forfeitButton.setOnClickListener(v -> forfeitRound());
        forfeitRound2.setOnClickListener(v -> forfeitRound());

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
                        if (mHandler != null) return false;
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
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    if (isRunning) {
                        playerScore.get_current_player().add_score();
                        nextRound();
                    }
                }
            };
        });

        back.setOnClickListener(v -> showPopUp());

        //Mudar. só para debug
        //settings.setOnClickListener(v -> goRecords());
    }

    private void gifVisibility() {
        if(flag) drink.setVisibility(View.VISIBLE);
        else drink.setVisibility(View.INVISIBLE);
    }

    private void goRecords() {
        Intent records = new Intent(this, GameRecords.class);
        records.putExtra(MainActivity.MODE,getIntent().getStringExtra(MainActivity.MODE));
        records.putExtra(Players_Activity.PLAYER, parcelable);
        records.putParcelableArrayListExtra(Players_Activity.ARRAY,playerScore.getArray());
        startActivity(records);
    }

    private void showPopUp() {
        AlertDialog.Builder popup = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_window, findViewById(R.id.layoutDialog));
        popup.setView(view);

        AlertDialog alertDialog = popup.create();
        view.findViewById(R.id.yes).setOnClickListener(v -> goRecords());
        view.findViewById(R.id.no).setOnClickListener(v -> alertDialog.dismiss());

        Window window = alertDialog.getWindow();
        int width = 251;
        int height = 282;
        if(window != null) {
            window.setBackgroundDrawable(getDrawable(R.drawable.popup_style));
            setPosition(window);
            window.setLayout(width,height);
        }
        alertDialog.show();
    }

    private void setPosition(Window window) {
        int height = 282;
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        param.y = height;
        window.setAttributes(param);
    }

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
