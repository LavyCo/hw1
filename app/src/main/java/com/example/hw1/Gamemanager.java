package com.example.hw1;

import java.util.Random;

public class Gamemanager {
    private int life;
    private int rows;
    private int cols;
    private int eggsBoard[][];

    private int shipArr[];
    private int shipIndex;

    public Gamemanager(int life, int row, int col) {
        this.life = life;
        this.eggsBoard = new int [row][col];
        this.shipArr = new int [col];
        this.rows = row;
        this.cols = col;
        initBoard();
        moveShip((col-1)/2);
    }

    private void initBoard() {
        for(int i=0;i<rows;i++){
            for (int j=0;j<cols;j++){
                this.eggsBoard[i][j] = 0;
            }
        }
    }
    public void moveShip(int ship) {
        this.shipIndex = ship;
        for(int i = 0; i<cols;i++){
            this.shipArr[i] = 0;
        }
        this.shipArr[ship] = 1;
    }

    public int getLife(){
        return life;
    }

    public int getShipIndex(){
        return shipIndex;
    }

    public int [][] getEggsBoard(){
        return eggsBoard;
    }

    public void updateBoard(){
        for(int i = rows-1;i>=0;i--){
            for (int j=cols-1;j>=0;j--){
                if(i==rows-1){
                    eggsBoard[i][j]=0;
                }
                else{
                    if(eggsBoard[i][j]==1){
                        eggsBoard[i+1][j]=1;
                        eggsBoard[i][j]= 0;
                    }
                }
            }
        }
    }

    public void randomEgg(){
        for(int i =0;i<cols;i++){
            if(eggsBoard[0][i] == 1){
                return;
            }
        }
        int randomCol = new Random().nextInt(cols);
        eggsBoard[0][randomCol] = 1;
    }

    public boolean isCrashed(){
        return (this.eggsBoard[rows-1][shipIndex] == shipArr[shipIndex]);
    }

    public void crash(){
        this.life--;
    }

    public boolean isLose() {
        return life == 0;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
