package com.app.truthordare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    /*

    [0] [fgrt] [gewrgth]
    [1] [AFG]
    [2] [dsfh]


    [2] [dsfh]

     */

    private Button casual, party, dirty;

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

    private void startDirty(View v) {
        Intent dirty = new Intent(this, Dirty_Activity.class);
        startActivity(dirty);
    }

    private void startParty(View v) {
        Intent party = new Intent(this, Party_Activity.class);
        startActivity(party);
    }

    private void startCasual(View v) {
        Intent casual = new Intent(this, Casual_Activity.class);
        startActivity(casual);
    }

}
