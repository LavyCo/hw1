package com.example.hw1;

import android.os.Handler;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class Gamemanager {
    public final int DELAY = 1000;
    public final int HEIGHT = 6;
    public final int NUMOFCHICKENS = 3;

    private void startGame() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, 800);
                gameEggs.dropEggsDownView();
                eggsCrash();
            }
        }, DELAY);

        runOnUiThread(new Runnable() {
            public void run() {
                handler.postDelayed(this,1700);
                gameEggs.startFallingEggs();
                gameEggs.initBrokenEggs();


            }
        });



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
            }
        }

    }
    private void buttonLeftRightClick(){
        mainLeftRightBTN[0].setOnClickListener(v->gameShip.moveLeft());
        mainLeftRightBTN[1].setOnClickListener(v->gameShip.moveRight());
    }

    private void findViewForAllGameBoard() {
        mainLeftRightBTN = new MaterialButton[]{
                findViewById(R.id.Main_button_left),
                findViewById(R.id.Main_button_right)};
        hearts = new ShapeableImageView[]{
                findViewById(R.id.heart1),
                findViewById(R.id.heart2),
                findViewById(R.id.heart3)};
        ship1 = findViewById(R.id.ship1);
        ship2 = findViewById(R.id.ship2);
        ship3 = findViewById(R.id.ship3);
        brokenEggs=new ShapeableImageView[]
                {findViewById(R.id.broken_egg1),
                        findViewById(R.id.broken_egg2),
                        findViewById(R.id.broken_egg3)};

        setEggView() ;

    }
    private void setVisibility(){

        gameShip.initShip();
        gameEggs.initEggs();

    }

}
