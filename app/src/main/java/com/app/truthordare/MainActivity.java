package com.app.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MainActivity extends Activity {
    private Button casual, party, dirty;
    private final String file = "color.txt";
    public static final String MODE = "com.app.truthordare.MODE";
    private FloatingActionButton settings, language, ads;
    private Animation fab_open, fab_close, fabRClockwise, fabRAntiClockwise;
    private boolean isOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        casual = findViewById(R.id.casual);
        party = findViewById(R.id.party);
        dirty = findViewById(R.id.dirty);
        settings = findViewById(R.id.main_settings);
        language = findViewById(R.id.main_language);
        ads = findViewById(R.id.main_ads);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        casual.setOnClickListener(v -> startCasual(v));
        party.setOnClickListener(v -> startParty(v));
        dirty.setOnClickListener(v -> startDirty(v));

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen){
                    ads.startAnimation(fab_close);
                    language.startAnimation(fab_close);
                    settings.startAnimation(fabRAntiClockwise);
                    language.setVisibility(View.GONE);
                    ads.setVisibility(View.GONE);
                    language.setClickable(false);
                    ads.setClickable(false);
                    isOpen=false;
                }else{
                    language.setVisibility(View.VISIBLE);
                    ads.setVisibility(View.VISIBLE);
                    ads.startAnimation(fab_open);
                    language.startAnimation(fab_open);
                    settings.startAnimation(fabRClockwise);
                    language.setClickable(true);
                    ads.setClickable(true);
                    isOpen=true;

                }
            }
        });
    }

    private void startDirty(View v) {
        Intent dirty = new Intent(this, Players_Activity.class);
        saveColor("#D1495B");
        dirty.putExtra(MODE,"dirty");
        startActivity(dirty);
    }

    private void startParty(View v) {
        Intent party = new Intent(this, Players_Activity.class);
        saveColor("#16DB93");
        party.putExtra(MODE,"party");
        startActivity(party);
    }

    private void startCasual(View v) {
        Intent casual = new Intent(this, Players_Activity.class);
        saveColor("#2E5EAA");
        casual.putExtra(MODE,"casual");
        startActivity(casual);
    }

    private void saveColor(String color) {
        try (OutputStream os = openFileOutput(file,MODE_PRIVATE)){
            PrintWriter to = new PrintWriter(os);
            to.println(color);
            to.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
