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
    public int fliped = 1; /** -1 to flip**/
    public int xi, xf;
    public int yi, yf;
    private Rect dst;
    private Bitmap bitmap;
    private Paint paint;
    public int centerX;
    public int centerY;
    private boolean drawobject = false;
    private boolean noObjectflag = false;


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
//        System.out.println(dst);
        bitmap = _bitmap;
    }
    //move the objects by subtracting the x cooridinates
    //can use this later when implementing enemy's AI
    /***
     * setMoveX function
     * @param: move integer is the magnitude of the movement in x axis.
     * Purpose: this will make the objects move the left***/
    public void setMoveX(int move) {
        this.xi -= move*fliped;
        this.xf -= move*fliped;
        this.centerX -= move*fliped;
        dst.set(xi,yi,xf,yf);
    }
    public void setMoveY(int move) {
        this.yi += move;
        this.yf += move;
        this.centerY += move;
        dst.set(xi,yi,xf,yf);
    }
    public int getLeftX(){return xi;}
    public int getX(){return centerX;}
    public int getY(){return centerY;}
    public Rect getRect(){return dst;}


    /***
     *Collision detection function
     * @param : playerRect is the rect that will be used to detect whether dst contains it or not.
     * @return : boolean to decide whether collision has been detected or not. True or false. **/

    public boolean collisionDetected(int x, int y)
    {
        if (dst.contains(x,y)){return true;}
        return false;
    }
    public boolean isNoObjectflag(){return noObjectflag;}
    public void setNoObjectflag(boolean _noObjectflag){noObjectflag = _noObjectflag;}


    public boolean getdrawObject(){return drawobject;}
    public void setdrawObject(boolean _drawobject){drawobject = _drawobject;}

    public void drawObject(Canvas c)
    {
    //drawing the  objects
        if(getdrawObject()) {
            c.drawBitmap(bitmap, null, dst, null);
//        System.out.println(dst);
        }
    }
}
