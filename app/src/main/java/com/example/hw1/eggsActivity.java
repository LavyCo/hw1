package com.example.hw1;

import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class eggsActivity {
    public final int HEIGHT = 6;
    public final int NUMOFCHICKENS = 3;
    private final ShapeableImageView[][] eggs;
    private final ShapeableImageView[] brokenEggs;

    public eggsActivity(ShapeableImageView[][] eggs, ShapeableImageView[] brokenEggs) {
        this.eggs = eggs;
        this.brokenEggs = brokenEggs;

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
        int randEgg = rand.nextInt(3);
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


    public void initBrokenEggs() {
        for(int i=0;i<NUMOFCHICKENS;i++){
            this.brokenEggs[i].setVisibility(View.INVISIBLE);
        }
    }
}
