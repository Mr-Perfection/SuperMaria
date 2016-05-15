package com.learningjava.stephenlee.sidescroller;

import android.graphics.Canvas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by StephenLee on 5/14/16.
 */
public class Level {

    //Initialize the flag

    private Objects flagpole;
    private List<Objects> mushrooms;
    public Level(Objects _flagpole, List<Objects> _mushroom)
    {
        flagpole = _flagpole;
        mushrooms = _mushroom;
    }
    /**Set methods **/
    public void setFlagpoleMove(int delta) {flagpole.setMoveX(delta);}
    public void setMushroomMove(int delta)
    {
        for(int i=0;i<mushrooms.size();++i)
            mushrooms.get(i).setMoveX(delta);
    }


    /***Check whether flag is collided with the player**/
    public Boolean flagPoleCollided(int playerX, int playerY) {return flagpole.collisionDetected(playerX,playerY);}
    public Boolean mushroomCollided(int playerX, int playerY)
    {
        for(int i=0;i<mushrooms.size();++i)
        {
            if(mushrooms.get(i).getX() > 0) //check if x is still greater than 0
                if(mushrooms.get(i).collisionDetected(playerX, playerY)) return true;
        }
        return false;
    }
    public void draw(Canvas c)
    {
        for(int i=0;i<mushrooms.size();++i)
        {
            if(mushrooms.get(i).getX()>0)
                mushrooms.get(i).drawObject(c);
        }



        flagpole.drawObject(c);
    }


}
