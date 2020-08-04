package com.app.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MainActivity extends Activity {
    /*

    [0] [fgrt] [gewrgth]
    [1] [AFG]
    [2] [dsfh]


    [2] [dsfh]

     */

    private Button casual, party, dirty;
    private final String file = "color.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        casual = findViewById(R.id.casual);
        party = findViewById(R.id.party);
        dirty = findViewById(R.id.dirty);

        casual.setOnClickListener(v -> startCasual(v));
        party.setOnClickListener(v -> startParty(v));
        dirty.setOnClickListener(v -> startDirty(v));

    }

    //TODO: Guardar a cor que temos que meter na view

    private void startDirty(View v) {
        Intent dirty = new Intent(this, Players_Activity.class);
        saveColor("#D1495B");
        startActivity(dirty);
    }

    private void startParty(View v) {
        Intent party = new Intent(this, Players_Activity.class);
        saveColor("#16DB93");
        startActivity(party);
    }

    private void startCasual(View v) {
        Intent casual = new Intent(this, Players_Activity.class);
        saveColor("#2E5EAA");
        startActivity(casual);
    }

    private void saveColor(String color) {
        try (OutputStream os = openFileOutput(file,MODE_PRIVATE)){
            PrintWriter to = new PrintWriter(os);
            to.print(color);
            to.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
