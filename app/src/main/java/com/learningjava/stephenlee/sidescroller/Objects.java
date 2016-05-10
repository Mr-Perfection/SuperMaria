package com.learningjava.stephenlee.sidescroller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by elisahuang on 5/9/16.
 */
public class Objects {
    //instance of objects
    public int xi, xf;
    public int yi, yf;
    private Rect dst;
    private Bitmap object;
    private Paint paint;
    public int centerX;
    public int centerY;
    public Objects(Bitmap object, int xi, int yi, int xf, int yf){
        this.xi = xi;
        this.xf = xf;
        this.yi = yi;
        this.yf = yf;
        this.centerX = xi + (xf - xi) / 2;      //used for checking if collision of player and objects
        this.centerY = yi + (yf - yi) / 2;      //used for checking if collision of player and objects
        dst = new Rect(xi, yi, xf, yf);
        this.object = object;
    }
    //move the objects by subtracting the x cooridinates
    //can use this later when implementing enemy's AI
    public void setMove(int move) {
        this.xi -= move;
        this.xf -= move;
        this.centerX -= move;
        dst = new Rect(xi, yi, xf, yf);
    }
    public void drawObject(Canvas c) {      //drawing the  objects
        paint = new Paint();
        c.drawBitmap(object, null, dst, paint);
    }
}
