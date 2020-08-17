package com.app.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.app.truthordare.Model.Player;
import com.app.truthordare.Model.PlayerScore;
import org.parceler.Parcels;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class GameRecords extends Activity {

    private static int NAME = 1000, NUMBER = 1101, POINTS=1202;
    private final String file = "color.txt";
    private String mode=null;
    private PlayerScore playerScore;
    private ConstraintLayout view, background;
    private Parcelable parcelable;
    private TextView nameTxt, rank;
    private LinkedList<TextView> textViews;
    private int width, height, rank_width, rank_height;
    private Button back;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_records);

        intent = getIntent();
        parcelable = intent.getParcelableExtra(Players_Activity.PLAYER);
        playerScore = Parcels.unwrap(parcelable);
        playerScore.setArray(intent.getParcelableArrayListExtra(Players_Activity.ARRAY));
        mode = intent.getStringExtra(MainActivity.MODE);

        back = findViewById(R.id.back);

        String color = loadColor();
        background = findViewById(R.id.game_records_view);
        background.setBackgroundColor(Color.parseColor(color));

        view = findViewById(R.id.scroll_view);
        view.setBackgroundColor(Color.parseColor(color));

        textViews = new LinkedList<>();

        createRank();

        back.setOnClickListener(v -> goMainActivity());
    }

    private void goMainActivity() {
        Intent main = new Intent(this, MainActivity.class);
        main.putExtra(MainActivity.FLAG, intent.getIntExtra(MainActivity.FLAG, 0));
        startActivity(main);
    }

    private void createRank() {
        ArrayList<Player> list = playerScore.sort_players();
        Player player = list.remove(0);
        TextView firstName = findViewById(R.id.name);
        firstName.setText(" "+player.getName());
        TextView score = findViewById(R.id.points);
        score.setText(String.format("%d pts", player.get_score()));
        rank = findViewById(R.id.rank);
        int position = 1;
        Drawable drawable = getDrawable(R.drawable.records_style);
        textViews.add(firstName);

        width = firstName.getLayoutParams().width;
        height = firstName.getLayoutParams().height;
        rank_width = rank.getLayoutParams().width;
        rank_height = rank.getLayoutParams().height;

        for (Player p : list){
        //Player p = list.remove(0);
            TextView number = new TextView(this), name= new TextView(this), points= new TextView(this);
            textViews.add(name);
            TextView previous= textViews.remove(0);
            createName(name, drawable, p.getName(), previous);
            createNumber(number, ++position, drawable);
            createPoints(points ,drawable, String.format("%d pts", p.get_score()), previous);
        }
    }

    private void createPoints(TextView txt, Drawable drawable, String score, TextView root) {
        setLayout(txt, -1, -1, score, POINTS++, drawable, Gravity.RIGHT, 15);
        view.addView(txt);

        //(cima) nome do 2 (parte de cima) (10 de margem),
        //(direita) nome (direita) (14 de margem)}
        setConstraints( txt.getId(), ConstraintSet.TOP, nameTxt.getId(), ConstraintSet.TOP, 27);
        setConstraints(txt.getId(), ConstraintSet.RIGHT, root.getId(), ConstraintSet.RIGHT, 37);

    }

    private void createName(TextView txt, Drawable drawable, String name, TextView root) {
        //637 , 104
        setLayout(txt, width, height, name, NAME++, drawable, Gravity.LEFT, 25);
        view.addView(txt);
        nameTxt=txt;
        //(cima) nome do 1 (parte de baixo) (20 de margem),
        //(direita) nome 1 (direita)}
        setConstraints(txt.getId(), ConstraintSet.TOP, root.getId(), ConstraintSet.BOTTOM, 50);
        setConstraints(txt.getId(), ConstraintSet.RIGHT, root.getId(), ConstraintSet.RIGHT, 0);
        setConstraints(txt.getId(), ConstraintSet.LEFT, root.getId(), ConstraintSet.LEFT, 0);
    }

    private void createNumber(TextView txt, int position, Drawable drawable) {
        setLayout(txt, rank_width, rank_height, new StringBuilder().append(position).append(".º").toString(), NUMBER++, drawable, Gravity.CENTER,25);
        view.addView(txt);
        //{(cima) nome (parte de cima) (20 de margem)
        // (direita) nome (esquerda) (6 de margem)
        setConstraints(txt.getId(), ConstraintSet.TOP, nameTxt.getId(), ConstraintSet.TOP, 0);
        setConstraints(txt.getId(), ConstraintSet.RIGHT, rank.getId(), ConstraintSet.RIGHT, 0);
        rank=txt;
    }

    private void setLayout(TextView txt, int width, int height, String name, int id, Drawable drawable, int gravity, int size) {
        if(width != -1) {
            txt.setWidth(width);
            txt.setHeight(height);
        }
        txt.setText(" "+name);
        txt.setId(id);
        txt.setBackground(drawable);
        txt.setGravity(gravity);
        txt.setTextSize(size);
        txt.setTextColor(Color.parseColor("#212738"));
    }

    private void setConstraints(int startID, int startSide, int endID, int endSide, int margin) {
        ConstraintSet set = new ConstraintSet();
        set.clone(view);

        //set.connect(txt.getId(), ConstraintSet.TOP, view.getId(), ConstraintSet.TOP, 60);
        set.connect(startID, startSide, endID, endSide, margin);
        set.applyTo(view);
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
