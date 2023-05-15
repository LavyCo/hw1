package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.example.hw2.Interfaces.StepCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;



public class MainActivity extends AppCompatActivity {

    private final int SLOW_DELAY = 1000;
    private final int FAST_DELAY = 700;
    private static int currentDelay;
    public final int HEIGHT = 6;
    public final int NUMOFCHICKENS = 5;
    private final int LIFE = 3;
    private Gamemanager gamemanager;
    private ShapeableImageView[] hearts;
    private ShapeableImageView[][] eggs;
    private ShapeableImageView[][] fried_chicken;
    private ShapeableImageView[] brokenEggs;
    private ShapeableImageView[] ship;
    private TextView score;
    private int counterScore;
    private MediaPlayer eggSound;
    private MediaPlayer eatSound;
    private MaterialButton[] mainLeftRightBTN;
    private eGameMode gameMode;
    private StepDetector stepDetector;
    private ManualFunctions manualFunctions;
    final Handler handler = new Handler();
    Runnable runnable;
    private boolean isStartGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gamemanager = new Gamemanager(LIFE, HEIGHT, NUMOFCHICKENS);
        findViewForAllGameBoard();
        setButtons();
        Intent previousIntent = getIntent();
        String enum_name = previousIntent.getExtras().getString(DataManager.GAME_MODE);
        gameMode = eGameMode.valueOf(enum_name);
        manualFunctions = new ManualFunctions(this);
        if (gameMode == eGameMode.SLOW_ARROWS || gameMode == eGameMode.FAST_ARROWS) {
            initViewDelay();
        } else if (gameMode == eGameMode.SENSOR) {
            initViewSensors();
            stepDetector.start();
        }
        eggSound =MediaPlayer.create(this,R.raw.eggs_break);
        eatSound =MediaPlayer.create(this,R.raw.eating);
        counterScore=0;
        viewShip();
        isStartGame = true;
        start();
    }

    private void initViewSensors() {
        currentDelay = SLOW_DELAY;
        mainLeftRightBTN[0].setVisibility(View.INVISIBLE);//left
        mainLeftRightBTN[1].setVisibility(View.INVISIBLE);//right
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void left() {
                clicked(0);
            }

            @Override
            public void right() {
                clicked(1);
            }
        });
    }

    private void initViewDelay() {
        if (gameMode == eGameMode.SLOW_ARROWS)
            currentDelay = SLOW_DELAY;
        else if (gameMode == eGameMode.FAST_ARROWS)
            currentDelay = FAST_DELAY;
    }

    private void start() {
        if (isStartGame) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, currentDelay);
                    gamemanager.randomEgg();
                    initBrokenEggs();
                    refreshUI();
                    eggs[HEIGHT-1][gamemanager.getShipIndex()].setVisibility(View.INVISIBLE);
                    fried_chicken[HEIGHT-1][gamemanager.getShipIndex()].setVisibility(View.INVISIBLE);
                }
            };
            handler.post(runnable);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);


    }
    @Override
    public void onResume() {
        super.onResume();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,currentDelay);
    }

    private void endGame() {
        handler.removeCallbacks(runnable);
        eatSound.stop();
        eggSound.stop();
        isStartGame = false;
    }

    private void refreshUI() {
        counterScore++;
        score.setText(" "+counterScore);
        if(gamemanager.isCrashed()){
            eggSound.start();
            brokenEgg();
            gamemanager.crash();
            if(gamemanager.isLose()){
                endGame();
                openScorePage(counterScore);

            }
            else{
                if(isStartGame) {
                    manualFunctions.toast("Lost Life!");
                    manualFunctions.vibrate();
                    for (int i = gamemanager.getLife(); i < hearts.length; i++) {
                        hearts[i].setVisibility(View.INVISIBLE);
                    }
                }
            }
        }else if(gamemanager.isFried()){
            eatSound.start();
            fried_chicken[HEIGHT-2][gamemanager.getShipIndex()].setVisibility(View.INVISIBLE);
            if(gamemanager.getLife()!=LIFE){
                manualFunctions.toast("Get Life!");
                manualFunctions.vibrate();
            }
            gamemanager.addLife();
            for (int i = 0; i < gamemanager.getLife(); i++) {
                hearts[i].setVisibility(View.VISIBLE);
            }
        }
        gamemanager.updateBoard();
        gamemanager.randomEgg();
        gamemanager.randomFriedChicken();
        viewBoard();
    }

    private void brokenEgg() {
        int index=gamemanager.getShipIndex();
        swichEggs(eggs[HEIGHT-2][index],brokenEggs[index]);
    }


    private void viewBoard() {
        int[][] board = gamemanager.getEggsBoard();
        int height=0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < NUMOFCHICKENS; j++) {
                if (board[i][j] == 1) {
                    eggs[i][j].setVisibility(View.VISIBLE);
                }
                else  {
                    eggs[i][j].setVisibility(View.INVISIBLE);
                }
                if (board[i][j] == 2) {
                    fried_chicken[i][j].setVisibility(View.VISIBLE);
                } else {
                    fried_chicken[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void setButtons() {
        mainLeftRightBTN[0].setOnClickListener(view -> clicked(0));
        mainLeftRightBTN[1].setOnClickListener(view -> clicked(1));
    }
    private void clicked(int move) {
        int shipInd = gamemanager.getShipIndex();
        if(move == 0){
            shipInd --;
        }
        else if(move == 1){
            shipInd ++;
        }
        if(shipInd >= 0 && shipInd <NUMOFCHICKENS){
            gamemanager.moveShip(shipInd);
            viewShip();
        }

    }
    private void viewShip() {
        int ShipInd = gamemanager.getShipIndex();
        for (int i=0;i<NUMOFCHICKENS;i++){
            if(i==ShipInd){
                ship[i].setVisibility(View.VISIBLE);
            }
            else {
                ship[i].setVisibility(View.INVISIBLE);
            }
        }
    }



    private void findViewForAllGameBoard() {
        mainLeftRightBTN = new MaterialButton[]{
                findViewById(R.id.Main_button_left),
                findViewById(R.id.Main_button_right)};
        hearts = new ShapeableImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)};
        ship = new ShapeableImageView[]{
                findViewById(R.id.ship1),
                findViewById(R.id.ship2),
                findViewById(R.id.ship3),
                findViewById(R.id.ship4),
                findViewById(R.id.ship5)};
        brokenEggs=new ShapeableImageView[]
                {findViewById(R.id.broken_egg1),
                findViewById(R.id.broken_egg2),
                findViewById(R.id.broken_egg3),findViewById(R.id.broken_egg4),findViewById(R.id.broken_egg5)};
        score= findViewById(R.id.main_score);
        setEggView();
        setFriedChickenView();
        initBrokenEggs();
    }


    private void setEggView() {

        eggs = new ShapeableImageView[HEIGHT][NUMOFCHICKENS];
        int num = 1;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < NUMOFCHICKENS; j++) {
                String numOfEgg = "egg" + num;
                num++;
                int resID = getResources().getIdentifier(numOfEgg, "id", getPackageName());
                eggs[i][j] = ((ShapeableImageView) findViewById(resID));
                eggs[i][j].setVisibility(View.INVISIBLE);
            }
        }

    }
    private void setFriedChickenView() {

        fried_chicken = new ShapeableImageView[HEIGHT][NUMOFCHICKENS];
        int num = 1;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < NUMOFCHICKENS; j++) {
                String numOfFChicken = "fried_chicken" + num;
                num++;
                int resID = getResources().getIdentifier(numOfFChicken, "id", getPackageName());
                fried_chicken[i][j] = ((ShapeableImageView) findViewById(resID));
                fried_chicken[i][j].setVisibility(View.INVISIBLE);
            }
        }

    }

    public void swichEggs(ShapeableImageView egg, ShapeableImageView brokenEgg) {
        egg.setVisibility(View.INVISIBLE);
        brokenEgg.setVisibility(View.VISIBLE);

    }

    public void initBrokenEggs() {
        for(int i=0;i<NUMOFCHICKENS;i++){
            this.brokenEggs[i].setVisibility(View.INVISIBLE);
        }
    }

    private void openScorePage(int score) {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(ScoreActivity.KEY_SCORE, counterScore);
        startActivity(intent);
        finish();
    }

}