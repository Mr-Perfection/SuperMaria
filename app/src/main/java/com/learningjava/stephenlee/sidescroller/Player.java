package com.learningjava.stephenlee.sidescroller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by StephenLee on 5/4/16.
 */
public class Player {

    private int width, height;  //Screen size
    private Rect touchGrid; /*What touch grid would do is that it will take the user touch coordinates and check whether half left side has been
                            pressed or top right side has been press**/
    private Bitmap bitmap; // the actual bitmap
    private int x;   // the X coordinate
    private int y;   // the Y coordinate
    private int deltaX = 25;    //movement in magnitude for x coordinate
    private int deltaY = 25;    //movement in magnitude for x coordinate
    private boolean touched; // if spaceshooter is touched/picked up
    private boolean isVisible = true; //invisibility flag (when bullet hits the enemy, it becomes invisible)

    /*Set SpaceShooter (bitmap, x, y)*/
    public Player(Bitmap _bitmap, int _width, int _height)
    {
        bitmap = _bitmap;
        width = _width;
        height = _height;
        x = _width / 2;
        y = _height * 6/7;

        touchGrid = new Rect(0,0,width,height);

    }


    public void setVisibility(boolean _isVisible)
    {
        isVisible = _isVisible; //set it to false when hit by bullet

    }

    public Boolean getVisibility()
    {
        return isVisible; //set it to false when hit by bullet

    }
    //Get bitmap
    public Bitmap getBitmap()
    {
        return bitmap;
    }

    //Set bitmap
    public void setBitmap(Bitmap _bitmap)
    {
        bitmap = _bitmap;
    }
    //Get x axis
    public int getX() {

        return x;

    }

    //Set x axis
    public void setX(int _x) {

        x = _x;

    }
    //Get y axis
    public int getY() {

        return y;

    }
    //Set y axis
    public void setY(int _y) {

        y = _y;

    }
    //is touched
    public boolean isTouched(){
        return touched;
    }
    public void setTouched(boolean _touched)
    {
        touched = _touched;
    }

    void draw(Canvas canvas) {

        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);



    }

    public void update(int eventX, int eventY) {

        /**IF the left screen is touched***/
        if(eventX < width/2)
        {
            x=x-deltaX; //MOVE to the left
        }
        /**IF the right screen is touched***/
        if(eventX > width/2)
        {
            x=x+deltaX; //MOVE to the right
        }

//        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
//
//            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
//
//                // spaceshooter touched
//
//                setTouched(true);
//
//            } else {
//
//                setTouched(false);
//
//            }
//
//        } else {
//
//            setTouched(false);
//
//        }



    }

}
