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
    private List<Objects> boos;
    private List<Objects> coins;
    private Objects terrain;
    private boolean noLives = false;
    private int numLives = 3;

    public Level(Player player,Objects _flagpole, List<Objects> _mushroom, Maps map,List<Objects> _boo,List<Objects> _coins)
    {
        flagpole = _flagpole;
        mushrooms = _mushroom;
        this.map = map;
        this.player = player;
        boos = _boo;
        coins = _coins;


    }
    /**Set methods **/
    public void setbgMove(int delta){map.bgMovement(delta);}
    public void setFlagpoleMove(int delta) {flagpole.setMoveX(delta);}
    public void setMushroomMove(int delta)
    {
        for(int i=0;i<mushrooms.size();++i)
            mushrooms.get(i).setMoveX(delta);
    }
    public void setBooMove(int delta)
    {
        for(int b = 0; b< boos.size(); ++b) {
            if(boos.get(b).getY()> map.getbgYf()-200){
                boos.get(b).setMoveX(delta);
                boos.get(b).setMoveY(-delta);
            }
            else{
                boos.get(b).setMoveX(delta);
                boos.get(b).setMoveY(delta);
            }
        }
    }
    public void setCoinMove(int delta){
        for(int cn = 0; cn < coins.size(); ++cn){
            coins.get(cn).setMoveX(delta);
        }
    }
    public void setnoCoin(boolean noCoin) {
        for (int cn = 0; cn < coins.size(); ++cn) {
            coins.get(cn).setNoObjectflag(noCoin);
        }
    }
    public void setdrawCoin(boolean ddrawcoin) {
        for (int cn = 0; cn < coins.size(); ++cn) {
            coins.get(cn).setdrawObject(ddrawcoin);
        }
    }
    public void playerGravity(){player.gravity();}
    public void playerUpdate(int eventX, int eventY){player.update(eventX, eventY);}
    public void setnoLives(boolean _noLives){noLives = _noLives;}
    public void setIntialLives(int _numLives){numLives = _numLives;}

    /***GET methods****/
    //public boolean getGameOver(){return GameOver;}
    public int playerGetX() {return player.getX();}
    public int playerGetY() {return player.getY();}
    public Boolean playerGetVisibility() {return player.getVisibility();}

    public List<Objects> getMushrooms() {
        return mushrooms;
    }
    public List<Objects> getBoos() {
        return boos;
    }
    public Boolean noCoin() {
        for (int cn = 0; cn < coins.size(); ++cn) {
            coins.get(cn).isNoObjectflag();
            return true;
        }
        return false;
    }
    public Boolean getdrawCoin( ) {
        for (int cn = 0; cn < coins.size(); ++cn) {
            coins.get(cn).getdrawObject();
            return false;
        }
        return true;
    }
    public boolean isnoLives(){return noLives;}
    public int getIntialLives(){return numLives;}


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
    public Boolean booCollided(int playerX, int playerY)
    {
        for(int b = 0; b< boos.size(); ++b) {
            if(boos.get(b).getX()>0 && boos.get(b).getY()>0) {
                if(boos.get(b).collisionDetected(playerX, playerY)) {
                    return true;
                }
            }
        }
        return false;
    }
    public Boolean coinCollided(int playerX, int playerY) {
        for (int cn = 0; cn < coins.size(); ++cn) {
            if(coins.get(cn).collisionDetected(playerX, playerY)&&(coins.get(cn).getX() <= playerX || coins.get(cn).getX() >= playerX))
            {
                coins.get(cn).setdrawObject(false);
                coins.get(cn).setNoObjectflag(true);
                return true;
            }

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
            for(int b = 0; b< boos.size(); ++b) {
                if(boos.get(b).getX()>0 && boos.get(b).getY()>0) {
                    boos.get(b).setdrawObject(true);
                    boos.get(b).drawObject(c);
                }
            }
        for(int cn = 0; cn < coins.size(); ++cn) {
            if (!coins.get(cn).isNoObjectflag()) {

                coins.get(cn).setdrawObject(true);
                coins.get(cn).drawObject(c);
            }
        }
            flagpole.setdrawObject(true);
            flagpole.drawObject(c);

    }


}
