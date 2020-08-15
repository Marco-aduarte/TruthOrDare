package com.app.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

public class MainActivity extends Activity {
    private Button casual, party, dirty;
    private final String file = "color.txt";
    public static final String MODE = "com.app.truthordare.MODE", LANGUAGE = "com.app.truthordare.LANGUAGE", FLAG = "com.app.truthordare.FLAG";
    private FloatingActionButton settings, language, ads;
    private Animation fab_open, fab_close, fabRClockwise, fabRAntiClockwise;
    private String idiom = "_english";
    private boolean isOpen=false;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        flag = intent.getIntExtra(MainActivity.FLAG, 0);

        casual = findViewById(R.id.casual);
        party = findViewById(R.id.party);
        dirty = findViewById(R.id.dirty);
        settings = findViewById(R.id.main_settings);
        language = findViewById(R.id.main_language);
        ads = findViewById(R.id.main_ads);

        idiomInit();

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        casual.setOnClickListener(v -> startPlayerActivity("#2E5EAA", "casual"));
        party.setOnClickListener(v -> startPlayerActivity("#16DB93", "party"));
        dirty.setOnClickListener(v -> startPlayerActivity("#D1495B", "dirty"));

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

        language.setOnClickListener(v -> changeLanguage());

    }

    private void idiomInit() {
        if(flag==0) {
            Drawable drawable = getDrawable(R.mipmap.reino_unido);
            language.setForeground(drawable);
            setAppLocale("en");
            idiom="_english";
        }
        else if(flag==1){
            Drawable drawable = getDrawable(R.mipmap.portugal);
            language.setForeground(drawable);
            setAppLocale("pt");
            idiom = "_portuguese";
        }
    }

    private void changeLanguage() {
        if (idiom.equals("_portuguese")) {
            idiom = "_english";
            Drawable drawable = getDrawable(R.mipmap.reino_unido);
            language.setForeground(drawable);
            language.invalidateDrawable(drawable);
            setAppLocale("en");
            flag = 0;
            startMain();
        } else{
            idiom = "_portuguese";
            Drawable drawable = getDrawable(R.mipmap.portugal);
            language.setForeground(drawable);
            language.invalidateDrawable(drawable);
            setAppLocale("pt");
            flag = 1;
            startMain();
        }
    }

    private void setAppLocale(String localeCode){
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(localeCode.toLowerCase()));
        res.updateConfiguration(conf, dm);
    }

    private void startMain() {
        Intent main = new Intent(this, MainActivity.class);
        main.putExtra(FLAG, flag);
        startActivity(main);
    }

    private void startPlayerActivity(String color, String mode){
        Intent activity = new Intent(this, Players_Activity.class);
        saveColor(color);
        activity.putExtra(MODE, mode);
        activity.putExtra(LANGUAGE, idiom);
        startActivity(activity);
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
