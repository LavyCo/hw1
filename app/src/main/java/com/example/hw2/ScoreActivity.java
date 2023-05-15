package com.example.hw2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ScoreActivity extends AppCompatActivity {

    public static final String KEY_STATUS = "KEY_STATUS";
    public static final String KEY_SCORE = "KEY_SCORE";

    private TextView score_LBL_score;
    private Button score_BTN_save;
    private Button back_To_Menu_BTN;

    private EditText score_ETXT_name;
    private GPS myGps;
    private String name;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        findViews();
        initViews();
        myGps = new GPS(this);
        Intent previousIntent = getIntent();
        score = previousIntent.getExtras().getInt(KEY_SCORE);
        score_LBL_score.setText("Score: " + score);

    }

    private void findViews() {

        score_LBL_score = findViewById(R.id.score);
        score_BTN_save = findViewById(R.id.scoreSave);
        score_ETXT_name = findViewById(R.id.name);
        back_To_Menu_BTN=findViewById(R.id.backToMenu);
    }


    private void openMenuPage(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();

    }

    private void initViews() {

        back_To_Menu_BTN.setOnClickListener(view -> openMenuPage());
        score_BTN_save.setOnClickListener(v -> {
            saveName();
            score_BTN_save.setVisibility(View.INVISIBLE);
            openMenuPage();
        });

    }

    private void saveName() {
        name = score_ETXT_name.getText().toString();
        UserDetails userDetails = new UserDetails().setName(name).setScore(score).setLat(myGps.getLat()).setLag(myGps.getLag());
        DataManager.getDataManager().updateTopRecords(userDetails);
    }
}
