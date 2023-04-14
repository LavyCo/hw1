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
    public final int NUMOFCHICKENS = 3;

    private eggsActivity gameEggs;
    private rocketShipActivity gameShip;
    private ShapeableImageView[] hearts;
    private ShapeableImageView[][] eggs;
    private ShapeableImageView[] brokenEggs;
    private ShapeableImageView ship1;
    private ShapeableImageView ship2;
    private ShapeableImageView ship3;
    private  int counter=1;

    private MaterialButton[] mainLeftRightBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewForAllGameBoard();
        gameEggs=new eggsActivity(eggs,brokenEggs);
        gameShip=new rocketShipActivity(ship1,ship2,ship3);
        setVisibility();
        buttonLeftRightClick();
        startGame();

    }
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
    private void setVisibility(){

        gameShip.initShip();
        gameEggs.initEggs();

    }
    private void heartManager(int heartsNumber){
        hearts[hearts.length - heartsNumber].setVisibility(View.INVISIBLE);
        if(heartsNumber == 3){
            for (int i = 1; i <= 3 ; i++) {
                hearts[hearts.length - i].setVisibility(View.VISIBLE);
            }
        }

    }
    private void eggsCrash() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (counter == 4)
            counter = 1;

        if (eggs[4][0].getVisibility() == View.VISIBLE && ship1.getVisibility() == View.VISIBLE) {
            gameEggs.swichEggs(eggs[4][0], brokenEggs[0]);
            heartManager(counter);
            toastVibrator(getApplicationContext(), v, counter);
            counter++;

        }
        if (eggs[4][1].getVisibility() == View.VISIBLE && ship2.getVisibility() == View.VISIBLE) {

            gameEggs.swichEggs(eggs[4][1], brokenEggs[1]);
            heartManager(counter);
            toastVibrator(getApplicationContext(), v, counter);
            counter++;
        }
        if (eggs[4][2].getVisibility() == View.VISIBLE && ship3.getVisibility() == View.VISIBLE) {

            gameEggs.swichEggs(eggs[4][2], brokenEggs[2]);
            heartManager(counter);
            toastVibrator(getApplicationContext(), v, counter);
            counter++;
        }

    }
    private void toastVibrator(Context context,Vibrator v,int heartsNumber){

        if(heartsNumber == 3)
            heartsNumber = 0;
        Toast.makeText(context," The number of attempts left:" + (hearts.length - heartsNumber)  ,Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

}