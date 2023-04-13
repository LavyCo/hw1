package com.example.hw1;

import android.view.View;

import com.google.android.material.imageview.ShapeableImageView;

public class rocketShipActivity {
    private ShapeableImageView ship1;
    private ShapeableImageView ship2;
    private ShapeableImageView ship3;
    public rocketShipActivity(ShapeableImageView ship1,ShapeableImageView ship2,ShapeableImageView ship3){
        this.ship1=ship1;
        this.ship2=ship2;
        this.ship3=ship3;
    }
    public void initShip(){//move the ship to the middle
        ship1.setVisibility(View.INVISIBLE);
        ship3.setVisibility(View.INVISIBLE);

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
}
