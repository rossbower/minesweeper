package com.example.ross.minesweeper;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ross.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    public static int GAMESIZE;
    private LinearLayout layoutContent;
    private MinesweeperView gameView;
    public static Button btnMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);
        gameView = (MinesweeperView) findViewById(R.id.gameView);

        GAMESIZE = getIntent().getIntExtra(LevelSelect.GAMESIZE, 1);
        gameView.gameSetUp(GAMESIZE);

        final MinesweeperView gameView = (MinesweeperView) findViewById(R.id.gameView);

        btnMode = (Button) findViewById(R.id.btnMode);
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.changeMode();
            }
        });

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameView.restartGame(GAMESIZE);
            }
        });

    }

    public static int getGameSize() {return GAMESIZE;};

    public void showModeMessage(String message) {
        Snackbar.make(layoutContent, message, Snackbar.LENGTH_LONG).show();
    }

    public void showOverMessage(String message) {
        Snackbar.make(layoutContent, message, Snackbar.LENGTH_LONG).
                setAction(R.string.restart_txt, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gameView.restartGame(GAMESIZE);
                    }
                }).show();
    }
}
