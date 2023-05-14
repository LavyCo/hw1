package com.example.hw2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    private MaterialButton slowMode;
    private MaterialButton fastMode;
    private MaterialButton sensorMode;
    private MaterialButton topTen;
    private eGameMode gameMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        findViews();
        initViews();
    }

    private void initViews() {
        slowMode.setOnClickListener(v -> {
            gameMode = eGameMode.SLOW_ARROWS;
            startGame();
        });

        fastMode.setOnClickListener(v -> {
            gameMode = eGameMode.FAST_ARROWS;
            startGame();
        });

        sensorMode.setOnClickListener(v -> {
            gameMode = eGameMode.SENSOR;
            startGame();
        });

        topTen.setOnClickListener(v -> openRecords());


    }


    private void findViews() {
        slowMode = findViewById(R.id.slowMode);
        fastMode = findViewById(R.id.fastMode);
        sensorMode = findViewById(R.id.sensorMode);
        topTen = findViewById(R.id.topTen);
    }

    private void startGame() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(DataManager.GAME_MODE, gameMode.name());
        startActivity(intent);
        finish();
    }

    private void openRecords() {
        Intent intent = new Intent(this, RecordsActivity.class);
        startActivity(intent);
        finish();
    }

}