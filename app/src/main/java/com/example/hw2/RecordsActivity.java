package com.example.hw2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class RecordsActivity extends AppCompatActivity {
    private MaterialButton top_BTN_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_activity);
        MapsFragments mapsFragment = new MapsFragments();
        TopFragments fragment_top10 = new TopFragments();

        getSupportFragmentManager().beginTransaction().add(R.id.top_LAY_details, fragment_top10).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.top_LAY_maps, mapsFragment).commit();
        findViews();
        initViews();
    }

    private void initViews() {
        top_BTN_back.setOnClickListener(view -> openGamePage());
    }

    private void openGamePage() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        top_BTN_back = findViewById(R.id.top_BTN_back);

    }


}
