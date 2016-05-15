package com.learningjava.stephenlee.sidescroller;

import android.graphics.Canvas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by StephenLee on 5/14/16.
 */

/****
 * Level
 * Description:
 * this class contains flagpole, enemies, coins, map, and terrain. Gather all the objects together into one class. Set methods to update the positions
 * and draw function to draw the objects****/
public class Level {

    //Initialize the flag
//    private boolean GameOver = false;
    public Player player;
    private Objects flagpole;
    private List<Objects> mushrooms;
    private Maps map;
    public Level(Player player,Objects _flagpole, List<Objects> _mushroom, Maps map)
    {
        flagpole = _flagpole;
        mushrooms = _mushroom;
        this.map = map;
        this.player = player;
    }
    /**Set methods **/
    public void setbgMove(int delta){map.bgMovement(delta);}
    public void setFlagpoleMove(int delta) {flagpole.setMoveX(delta);}
    public void setMushroomMove(int delta)
    {
        for(int i=0;i<mushrooms.size();++i)
            mushrooms.get(i).setMoveX(delta);
    }
    public void playerGravity(){player.gravity();}
    public void playerUpdate(int eventX, int eventY){player.update(eventX, eventY);}

    /***GET methods****/
    //public boolean getGameOver(){return GameOver;}
    public int playerGetX() {return player.getX();}
    public int playerGetY() {return player.getY();}
    public Boolean playerGetVisibility() {return player.getVisibility();}

    public List<Objects> getMushrooms() {
        return mushrooms;
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

            map.draw(c);
            for (int i = 0; i < mushrooms.size(); ++i) {
                if (mushrooms.get(i).getX() > 0) {
                    mushrooms.get(i).setdrawObject(true);
                    mushrooms.get(i).drawObject(c);
                }
            }
            flagpole.setdrawObject(true);
            flagpole.drawObject(c);

    }


}
