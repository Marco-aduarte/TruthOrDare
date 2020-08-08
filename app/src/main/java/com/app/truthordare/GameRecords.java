package com.app.truthordare;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.truthordare.Model.Player;
import com.app.truthordare.Model.PlayerScore;

import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

//TODO: Fazer layout
public class GameRecords extends AppCompatActivity {

    private final String file = "color.txt";
    private String mode=null;
    private PlayerScore playerScore;
    private ConstraintLayout view;
    private Parcelable parcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_records);

        Intent intent = getIntent();
        parcelable = intent.getParcelableExtra(Players_Activity.PLAYER);
        playerScore = Parcels.unwrap(parcelable);
        playerScore.setArray(intent.getParcelableArrayListExtra(Players_Activity.ARRAY));
        mode = intent.getStringExtra(MainActivity.MODE);

        String color = loadColor();
        view = findViewById(R.id.game_records_view);
        view.setBackgroundColor(Color.parseColor(color));

        //lista de players. ver o size e adicionar tantos quanto o size

        createRank();

    }

    private void createRank() {
        ArrayList<Player> list = playerScore.sort_players();
        Player player = list.remove(0);
        TextView n = findViewById(R.id.name);
        n.setText(player.getName());
        TextView score = findViewById(R.id.points);
        score.setText(player.get_score());
        int position = 1;
        Drawable drawable = getDrawable(R.drawable.records_style);
        for (Player p : list){
            TextView number = new TextView(this), name= new TextView(this), points= new TextView(this);
            createNumber(number, position, drawable);
            createName(name, drawable, p.getName());
            createPoints(points ,drawable, p.get_score());
        }
    }

    private void createPoints(TextView txt, Drawable drawable, String score) {
        txt.invalidateDrawable(drawable);
        txt.setText(score);
        view.addView(txt, txt.getLayoutParams());
    }

    private void createName(TextView txt, Drawable drawable, String name) {
        txt.invalidateDrawable(drawable);
        txt.setText(name);
        view.addView(txt, txt.getLayoutParams());
    }

    private void createNumber(TextView txt, int position, Drawable drawable) {
        txt.invalidateDrawable(drawable);
        txt.setText(new StringBuilder().append(++position).append("º").toString());
        view.addView(txt, txt.getLayoutParams());
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
