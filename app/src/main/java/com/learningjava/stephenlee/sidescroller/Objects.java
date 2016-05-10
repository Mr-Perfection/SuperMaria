package com.learningjava.stephenlee.sidescroller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by elisahuang & Stephen Lee on 5/9/16.
 */

public class Objects {
    //instance of objects
    public int xi, xf;
    public int yi, yf;
    private Rect dst;
    private Bitmap bitmap;
    private Paint paint;
    public int centerX;
    public int centerY;
    /***
     * Contructor for Objects class
     * @param : _bitmap is for the object image.
     * @params : (left, top, right bot) are four cooardinates for the rectangle.
     * Description: this constructor is used to initialize the objects and get the center coordinate for collision detection
     * ***/
    public Objects(Bitmap _bitmap, int _left, int _top, int _right, int _bot){
        xi = _left;
        xf = _right;
        yi = _top;
        yf = _bot;
        this.centerX = xi + (xf - xi) / 2;      //used for checking if collision of player and objects
        this.centerY = yi + (yf - yi) / 2;      //used for checking if collision of player and objects
        dst = new Rect(xi,yi,xf,yf);

        bitmap = _bitmap;
    }
    //move the objects by subtracting the x cooridinates
    //can use this later when implementing enemy's AI
    /***
     * setMoveX function
     * @param: move integer is the magnitude of the movement in x axis.
     * Purpose: this will make the objects move the left***/
    public void setMoveX(int move) {
        this.xi -= move;
        this.xf -= move;
        this.centerX -= move;
        dst.set(xi,yi,xf,yf);
    }

    /***
     * **/

    public boolean collisionDetected(Rect playerRect)
    {
        if (dst.contains(playerRect)){return true;}
        return false;
    }

    public void drawObject(Canvas c) {      //drawing the  objects
        c.drawBitmap(bitmap, null, dst, null);
    }
}
