package com.app.truthordare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class Game_Option extends Activity {

    private final String file = "color.txt";
    private Button truth, dare, add, back;
    private String mode=null;
    private TextView player;
    private ImageView image;
    private PlayerScore playerScore;
    private Parcelable parcelable;
    public static final String OPTION = "com.app.truthordare.OPTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_option);

        Intent intent = getIntent();
        parcelable = intent.getParcelableExtra(Players_Activity.PLAYER);
        playerScore = Parcels.unwrap(parcelable);
        playerScore.setArray(intent.getParcelableArrayListExtra(Players_Activity.ARRAY));
        mode = intent.getStringExtra(MainActivity.MODE);

        String color = loadColor();
        ConstraintLayout view = findViewById(R.id.game_option_view);
        view.setBackgroundColor(Color.parseColor(color));
        truth=findViewById(R.id.truth);
        dare=findViewById(R.id.dare);
        image = findViewById(R.id.Imagemoji_option);
        player=findViewById(R.id.currentPlayer);
        add=findViewById(R.id.addOption);
        Button add2 = findViewById(R.id.add2Option);
        back=findViewById(R.id.buttonPopup_option);

        updatePlayerView();

        Phrases phrases = new Phrases();
        Actions actions;
        try {
            actions = new Actions(((ArrayList<String>) getArray(phrases, "truth")), ((ArrayList<String>) getArray(phrases, "dare")), (ArrayList<Integer>) getEmojisArray(phrases));
            image.setImageResource(actions.get_emoji());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        truth.setOnClickListener(v -> startGameQuestion(v, "Truth"));
        dare.setOnClickListener(v -> startGameQuestion(v, "Dare"));
        add.setOnClickListener(v -> forfeitRound());
        add2.setOnClickListener(v -> forfeitRound());
        back.setOnClickListener(v -> showPopUp());
    }

    private void showPopUp() {
        AlertDialog.Builder popup = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_window, findViewById(R.id.layoutDialog));
        //popup.setView(R.layout.popup_window);
        popup.setView(view);

        AlertDialog alertDialog = popup.create();
        view.findViewById(R.id.yes).setOnClickListener(v -> {}/*//TODO: Vai parar ao scoreBoard*/);
        view.findViewById(R.id.no).setOnClickListener(v -> alertDialog.dismiss());

        Window window = alertDialog.getWindow();
        int width = 251;
        int height = 182;
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

    //TODO: forfeit round -> passa para o proximo player sem dar pontos, avança game_option
    private void forfeitRound() {
        updatePlayerView();
    }

    private void updatePlayerView() { player.setText("It's "+playerScore.next_Player().getName()+"!"); }

    private void startGameQuestion(View v, String option) {
        Intent question = new Intent(this,Game_Question.class);
        question.putExtra(OPTION,option);
        question.putExtra(MainActivity.MODE,getIntent().getStringExtra(MainActivity.MODE));
        question.putExtra(Players_Activity.PLAYER, parcelable);
        question.putParcelableArrayListExtra(Players_Activity.ARRAY,playerScore.getArray());
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

    private Object getEmojisArray(Phrases phrases) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return phrases.getClass().getMethod("get_"+mode+"_emojis").invoke(phrases);
    }

    private Object getArray(Phrases phrases, String option) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //comparar o type e o método
        Method method = phrases.getClass().getMethod("get_"+mode+"_"+option);
        return method.invoke(phrases);
    }


}
