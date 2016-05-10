package com.learningjava.stephenlee.sidescroller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by StephenLee on 5/4/16.
 */
public class Player {
    private static final String Name = GameView.class.getSimpleName();

    private int width, height;  //Screen size
    private Rect touchGrid; /*What touch grid would do is that it will take the user touch coordinates and check whether half left side has been
                            pressed or top right side has been press**/
    private Rect body;  //Player body object that checks whether it contains the touch location or not.
    private Bitmap bitmap; // the actual bitmap
    private Bitmap originalBitmap; // the temp bitmap or original
    private Bitmap animatedBitmap; //the animated bitmap

    private int x;   // the X coordinate
    private int y;   // the Y coordinate
    private int deltaX = 35;    //movement in magnitude for x coordinate
    private int deltaY = 210;    //movement in magnitude for x coordinate
    private boolean isFliped = false; //check if the bitmap is fliped or not.
    private boolean isVisible = true; //invisibility flag (when bullet hits the enemy, it becomes invisible)
    private boolean jumped = false; //check if jump is already executed or not.
    /*Set SpaceShooter (bitmap, x, y)*/
    public Player(Bitmap _bitmap,Bitmap _animtedBitmap, int _width, int _height)
    {
        bitmap = _bitmap;
        originalBitmap = bitmap;
        animatedBitmap = _animtedBitmap;
        width = _width;
        height = _height * 7/9;
        x = _width / 2;
        y = height;
        body = new Rect(x,y,x+_bitmap.getWidth(), y+_bitmap.getHeight());

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
//    //is touched
//    public boolean isTouched(){
//        return touched;
//    }
//    public void setTouched(boolean _touched)
//    {
//        touched = _touched;
//    }


    /**
     Creates a new bitmap by flipping the specified bitmap

     @param src        Bitmap to flip

     @return           New bitmap created by flipping the given one
     vertically or horizontally as specified by
     the <code>type</code> parameter or
     the original bitmap if an unknown type
     is specified.
     **/
    private Bitmap flipBitmap(Bitmap src)
    {
        Matrix matrix = new Matrix();

        matrix.preScale(-1,1);


//        bitmap = Bitmap.createBitmap(temp, x,y,temp.getWidth(),temp.getHeight(),matrix,false);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    void draw(Canvas canvas) {

        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);



    }

    /***Stephen Sungsoo Lee
     * Update the movement of the player based on the touches on the screen.
     * @param eventX: get the touch x-coordinate from the user touch input.
     * @param eventY: get the touch y-coordinate from the user touch input.
     *
     * Description:
     * This update function will update based on the touch events. If touch x coordinate is less than the width/2 (left half) then
     * the player will move to the left. Same thing goes to the >width/2 (right half). And if the user touches the middle half of
     * the screen, then it iwll trigger the jump.
     * For our animation, I flip the bitmap based on the isFliped flag. And if the user jumps or flies by tapping the middle of
     * the screen, then it will draw the different bitmap.***/
    public void update(int eventX, int eventY) {

        /**IF the left screen is touched***/
        if(eventX < width/2)
        {
            //Only move left with this condition
            if(x>=width/4) x-=deltaX; //MOVE to the left

            if(!isFliped)
            {
                originalBitmap = flipBitmap(originalBitmap);
                isFliped = true;
            }
        }   //EOF if < width/2
        /**IF the right screen is touched***/
        else if(eventX > width/2)
        {


            if(x <= width*3/4) x+=deltaX; //MOVE to the right
            if(isFliped)
            {
                originalBitmap = flipBitmap(originalBitmap);
                isFliped = false;
            }

        }
        /**IF the touch is in the middle of the screen**/
        if(eventX > width/4 && eventX < width*3/4)
        {


            int i;
            if(!jumped)
            {
                y-=deltaY;//MOVE up

                bitmap = animatedBitmap;

                jumped = true;
                return;
            }


        } //EOF if event X
        bitmap = originalBitmap;    //If the player is not jumped use the orginal bitmap

    }//EOF update

    /**
     * Gravity always pulls the player downward until it hits the certain coordinates**/
    public void gravity()
    {
        if(jumped)
        {

            while (y <= height)
            {

                y+=5;

            }   //Check while y <= certain height because of the gravity.

            if(y >= height)

            {
                jumped = false;
                bitmap = originalBitmap;
            }
        }

    }//EOF gravity

}
