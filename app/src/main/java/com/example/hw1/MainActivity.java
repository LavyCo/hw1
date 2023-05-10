package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;



public class MainActivity extends AppCompatActivity {

    public final int DELAY = 1000;
    public final int HEIGHT = 6;
    public final int NUMOFCHICKENS = 5;
    private final int LIFE = 3;
    private Gamemanager gamemanager;
    private ShapeableImageView[] hearts;
    private ShapeableImageView[][] eggs;
    private ShapeableImageView[] brokenEggs;
    private ShapeableImageView[] ship;

    private  int counter=1;


    private MaterialButton[] mainLeftRightBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gamemanager=new Gamemanager(LIFE,HEIGHT,NUMOFCHICKENS);
        findViewForAllGameBoard();
        viewShip();
       setButtons();
         start();


    }
    private void start() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, 800);
                gamemanager.randomEgg();
                initBrokenEggs();
                refreshUI();
                int index=gamemanager.getShipIndex();
                eggs[HEIGHT-1][index].setVisibility(View.INVISIBLE);

            }
        }, DELAY);


    }

    private void refreshUI() {
        if(gamemanager.isCrashed()){
            int index=gamemanager.getShipIndex();
            swichEggs(eggs[HEIGHT-2][index],brokenEggs[index]);
            gamemanager.crash();
            if(gamemanager.isLose()){
                toast("GAME OVER!");
                vibrate();
                gamemanager.setLife(LIFE);
                for(int i = 0;i <hearts.length;i++){
                    hearts[i].setVisibility(View.VISIBLE);
                }

            }
            else{
                toast("Lost Life!");
                vibrate();
                for(int i = gamemanager.getLife();i < hearts.length;i++){
                    hearts[i].setVisibility(View.INVISIBLE);
                }

            }
        }
        gamemanager.updateBoard();
        gamemanager.randomEgg();
        viewBoard();
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
            }
        }
    }

    private void setButtons() {
        mainLeftRightBTN[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clicked(0);
            }
        });
        mainLeftRightBTN[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clicked(1);
            }
        });
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
        gamemanager.updateBoard();
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
    private void toast(String st) {
        Toast.makeText(this, st, Toast.LENGTH_SHORT).show();
    }
    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
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

        setEggView() ;

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

    public void swichEggs(ShapeableImageView egg, ShapeableImageView brokenEgg) {
        egg.setVisibility(View.INVISIBLE);
        brokenEgg.setVisibility(View.VISIBLE);

    }

    public void initBrokenEggs() {
        for(int i=0;i<NUMOFCHICKENS;i++){
            this.brokenEggs[i].setVisibility(View.INVISIBLE);
        }
    }

}