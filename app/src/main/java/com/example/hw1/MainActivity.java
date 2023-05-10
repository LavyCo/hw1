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

import java.util.Random;

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
   private ShapeableImageView ship1;
    private ShapeableImageView ship2;
    private ShapeableImageView ship3;
    private ShapeableImageView ship4;
    private ShapeableImageView ship5;
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
//        setVisibility();
//        buttonLeftRightClick();
//        startGame();

    }
    private void start() {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, 800);
                gamemanager.randomEgg();
                initBrokenEggs();
                refreshUI();
            }
        }, DELAY);

        runOnUiThread(new Runnable() {
            public void run() {
                handler.postDelayed(this,1700);
                gamemanager.updateBoard();
            }
        });
    }

    private void refreshUI() {
        showLife();
        if(gamemanager.isCrashed()){
            gamemanager.crash();
            if(gamemanager.isLose()){
                toast("GAME OVER!");

            }
            else{
                toast("Lost Life!");
                vibrate();

            }
        }
        gamemanager.randomEgg();
        viewBoard();
    }
    private void  showLife(){
        int life=gamemanager.getLife();
        if(life==3){
            hearts[0].setVisibility(View.VISIBLE);
            hearts[1].setVisibility(View.VISIBLE);
            hearts[2].setVisibility(View.VISIBLE);
        }
        else if(life==2){
            hearts[0].setVisibility(View.VISIBLE);
            hearts[1].setVisibility(View.VISIBLE);
            hearts[2].setVisibility(View.INVISIBLE);
        }
        else if(life==1){
            hearts[0].setVisibility(View.VISIBLE);
            hearts[1].setVisibility(View.INVISIBLE);
            hearts[2].setVisibility(View.INVISIBLE);
        }
        else if(life==0){
            hearts[0].setVisibility(View.INVISIBLE);
            hearts[1].setVisibility(View.INVISIBLE);
            hearts[2].setVisibility(View.INVISIBLE);
        }
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
    //move to game manager
    private void startGame() {
          final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, 800);
                 dropEggsDownView();
                eggsCrash();
            }
        }, DELAY);

        runOnUiThread(new Runnable() {
            public void run() {
                handler.postDelayed(this,1700);
                startFallingEggs();
                initBrokenEggs();


            }
        });



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


    //move to game manager
private void buttonLeftRightClick(){
    mainLeftRightBTN[0].setOnClickListener(v->moveLeft());
    mainLeftRightBTN[1].setOnClickListener(v->moveRight());
}
    //move to game manager
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
        ship4 = findViewById(R.id.ship4);
        ship5 = findViewById(R.id.ship5);
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

    //move to game manager
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
    //move to game manager
    private void setVisibility(){
        initShip();
        initEggs();
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
            swichEggs(eggs[4][0], brokenEggs[0]);
            heartManager(counter);
            toastVibrator(getApplicationContext(), v, counter);
            counter++;

        }
        if (eggs[4][1].getVisibility() == View.VISIBLE && ship2.getVisibility() == View.VISIBLE) {

            swichEggs(eggs[4][1], brokenEggs[1]);
            heartManager(counter);
            toastVibrator(getApplicationContext(), v, counter);
            counter++;
        }
        if (eggs[4][2].getVisibility() == View.VISIBLE && ship3.getVisibility() == View.VISIBLE) {

            swichEggs(eggs[4][2], brokenEggs[2]);
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
    public void initShip(){//move the ship to the middle
        ship1.setVisibility(View.INVISIBLE);
        ship2.setVisibility(View.INVISIBLE);
        ship4.setVisibility(View.INVISIBLE);
        ship5.setVisibility(View.INVISIBLE);

    }

    public void moveLeft(){
        if(ship2.getVisibility()==View.VISIBLE){
            ship1.setVisibility(View.VISIBLE);
            ship2.setVisibility(View.INVISIBLE);
        }else if(ship3.getVisibility()==View.VISIBLE){
            ship2.setVisibility(View.VISIBLE);
            ship3.setVisibility(View.INVISIBLE);

        }
    }

    public void moveRight(){
        if(ship2.getVisibility()==View.VISIBLE){
            ship3.setVisibility(View.VISIBLE);
            ship2.setVisibility(View.INVISIBLE);
        }else if(ship1.getVisibility()==View.VISIBLE){
            ship2.setVisibility(View.VISIBLE);
            ship1.setVisibility(View.INVISIBLE);
        }
    }
    public void initEggs(){
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < NUMOFCHICKENS; j++) {
                this.eggs[i][j].setVisibility(View.INVISIBLE);
            }
        }
        initBrokenEggs();
    }

    public void swichEggs(ShapeableImageView egg, ShapeableImageView brokenEgg) {
        egg.setVisibility(View.INVISIBLE);
        brokenEgg.setVisibility(View.VISIBLE);

    }

    public void startFallingEggs() {
        Random rand = new Random();
        int randEgg = rand.nextInt(5);
        eggs[0][randEgg].setVisibility(View.VISIBLE);
    }

    public void dropEggsDownView() {
        for(int i = HEIGHT-1;i>=0;i--){
            for (int j=NUMOFCHICKENS-1;j>=0;j--){
                if(i==HEIGHT-1){
                    eggs[i][j].setVisibility(View.INVISIBLE);
                }
                else{
                    if(eggs[i][j].getVisibility()==View.VISIBLE){
                        eggs[i+1][j].setVisibility(View.VISIBLE);
                        eggs[i][j].setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    //move to main activity
    public void initBrokenEggs() {
        for(int i=0;i<NUMOFCHICKENS;i++){
            this.brokenEggs[i].setVisibility(View.INVISIBLE);
        }
    }

}