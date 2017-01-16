package com.example.ross.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelect extends AppCompatActivity {

    public static final String GAMESIZE = "GAMESIZE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        Button btnEasy = (Button) findViewById(R.id.btnEasy);
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithSize(5);
            }
        });

        Button btnMedium = (Button) findViewById(R.id.btnMedium);
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithSize(8);
            }
        });

        Button btnHard = (Button) findViewById(R.id.btnHard);
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameWithSize(10);
            }
        });
    }

    private void startGameWithSize(int size) {
        Intent intentStartGame = new Intent();
        intentStartGame.setClass(this, MainActivity.class);

        intentStartGame.putExtra(GAMESIZE, size);

        startActivity(intentStartGame);
    }
}
