package com.learningjava.stephenlee.sidescroller;

import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by StephenLee on 5/14/16.
 */
public class Level {

    //Initialize the flag

    private Objects flagpole;
    private Objects mushroom;
    public Level(Objects _flagpole, Objects _mushroom)
    {
        flagpole = _flagpole;
        mushroom = _mushroom;
    }
    /**Set methods **/
    public void setFlagpoleMove(int delta) {flagpole.setMoveX(delta);}


    /***Check whether flag is collided with the player**/
    public Boolean flagPoleCollided(int playerX, int playerY) {return flagpole.collisionDetected(playerX,playerY);}

    public void draw(Canvas c)
    {
        flagpole.drawObject(c);
    }


}
