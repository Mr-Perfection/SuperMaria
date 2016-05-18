package com.learningjava.stephenlee.sidescroller;

/**
 * Created by StephenLee on 5/11/16.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/***
 * In this class Maps, we will store background image and all the necessary components for the maps.
 * Description: this Maps class will contain the level's information such as terrain, enemies positions, coin positions, etc...**/
public class Maps {

    /**Background***/
    Rect bgRect = new Rect();
    Bitmap bgImage;
    int xi, xf, yi,yf;  //these variables are to determine the beginning and the end of the game.
    int WIDTH, HEIGHT;  //Width and Height of the background
    Boolean GameOver;
    public Maps(Bitmap backgroundImage, int bgWidth, int bgHeight )
    {

        bgImage = backgroundImage;
        //Set xy positions
        xi = 0;
        xf = xi + bgWidth;
        yi = 0;
        yf = yi + bgHeight;
    }


    /**This will update the left and right positions of background rect.**/
    public void bgMovement(int delta)
    {
        xi -= delta;
        xf -= delta;
    }
    public int getbgYf(){return yf;}

    public void gameOver(boolean isOver) {GameOver = isOver;}

    public void draw(Canvas c)
    {
        bgRect.set(xi, yi, xf, yf);
        c.drawBitmap(bgImage,null,bgRect,null);
    }



}
