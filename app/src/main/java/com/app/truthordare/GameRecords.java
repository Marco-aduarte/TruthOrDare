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
        ConstraintLayout view = findViewById(R.id.game_records_view);
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
            TextView number = new TextView(this), name= new TextView(this), points= new TextView(this);;
            number.invalidateDrawable(drawable);
            name.invalidateDrawable(drawable);
            number.setText(new StringBuilder().append(++position).append("º").toString());
            name.setText(p.getName());
            points.setText(p.get_score());
        }
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
