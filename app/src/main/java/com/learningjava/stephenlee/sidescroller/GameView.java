package com.learningjava.stephenlee.sidescroller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * Created by StephenLee on 5/4/16.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String Name = GameView.class.getSimpleName();
    /*****Initializations******/
//    private Player player;
    private GameThread gameThread;

    /***MAP settings****/
    private Map<Integer, Level> levels = new HashMap<>();
    private Level level;
    private int levelCounter = 0;
    float gravity = 0f;

    //Score
    private int score;
    private int xpos=0; //this will check whether player is moving to the right.
    private int scoreX = 0;
    private int scoreY = 0;

    //livies
    private int lives = 10;




    public GameView(Context context)
    {
        super(context);

        //Add callback to the surfaceholder to intercept the events
        // Notify the SurfaceHolder that you’d like to receive
        // SurfaceHolder callbacks .
        getHolder().addCallback(this);

        // make the SpaceView focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        /*SET player bitmaps*/
//        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sungsoo);
//        Bitmap animatedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.troll);
//        player = new Player(playerBitmap,animatedBitmap, getWidth(), getHeight()); //Set player constructor

        //Set score
        scoreX =100;
        scoreY = getHeight()/11;

        //Initialize the levels
        for(int i=0;i<3;++i)
        {
            levels.put(i,initializeLevel(i));
        }   //EOF for loop

//        initializeLevels();
//        level1 = levels.get(0);
        level = levels.get(levelCounter);

        //Start the thread
        gameThread = new GameThread(this);  //Set game thread constructor
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stud
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        gameThread.interrupt(); //Stop the thread
        Log.d(Name, "Thread shut down cleanly");

    }//EOF SurfaceDestroyed


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int touchX = (int)event.getX();
        int touchY = (int)event.getX();
        level.playerUpdate(touchX, touchY);
//        player.update(touchX, touchY);
        return true;
    }



    public void draw(Canvas c) {

        if (level.playerGetX() > getWidth() * 4 / 7) {
            level.setbgMove(30);
            xpos++;
            if (xpos > 0)  //If and only if the traveled distance is positive
                score++; //increments score
//            Log.d(Name, "flagpole moved left!");
            level.setFlagpoleMove(30);
            level.setCoinMove(30);
            level.setTerrainMove(30);

        } else if (level.playerGetX() < getWidth() * 2 / 7) {
            level.setbgMove(-30);
            xpos--; //going against the travel distance. Subtract xpos
            level.setFlagpoleMove(-30);
            level.setCoinMove(-30);
            level.setTerrainMove(-38);
        }

        /***Coin collision**/
        if (level.coinCollided(level.playerGetX(), level.playerGetY())) {
            level.setdrawCoin(false);
            level.setnoCoin(true);
        }


        //Set the mushroom movement
        level.setMushroomMove(38);
        //Check whether mushroom has been collided with player

        /***If terrain collides with player****/
//        Log.d(Name, "terrain collided "+level.terrainCollided(level.playerGetX(), level.playerGetY()));
        if (level.terrainCollided(level.playerGetX(), level.playerGetY())) {
            Log.d(Name, "terrain collided ");

            level.player.setY(getHeight() * 7 / 9 - 200);
        } else if (level.playerGetY() == getHeight() * 7 / 9 - 200)
            level.player.setY(getHeight() * 7 / 9);


        /***If Terrain and mushroom collision happens then flip the mushroom**/
        level.mushroomCollided(level.terrainGetX(), level.terrainGetY(), -1);


        /**Level drawn***/
        level.draw(c);

//        if(player.getVisibility())
        if (level.playerGetVisibility()) {
            level.player.draw(c);
            level.playerGravity();
        } //EOF


        /***Flag collision**/
        if (level.flagPoleCollided(level.playerGetX(), level.playerGetY())) {

            Log.d(Name, "flagpole collided!");
            try {
                ++levelCounter;
                if (levelCounter == 3) {
                    GameOver(c, getWidth(), getHeight());
                } else {
                    level = levels.get(levelCounter);
                    gameThread.sleep(2000);
                    Log.d(Name, "Current game level is: " + levelCounter);
                    level.setIntialLives(lives = 10);
                    level.setbooContainPlayer(false);
                    level.setmushroomsContainPlayer(false);
                }


            } catch (InterruptedException ex) {
                Log.d(Name, "Something went wrong with flag collision!");
                gameThread.interrupt();
            }


        } //EOF flagpole collision

        /***Mushroom and player collision**/
        if (level.mushroomCollided(level.playerGetX(), level.playerGetY())) {

            if (lives >= 1) {
                lives--;
                level.setIntialLives(lives);
            }
            if (level.getIntialLives() == 0) {
                GameOver(c, getWidth(), getHeight());
                gameThread.interrupt();
            }

        }
        level.setBooMove(100);
        if (level.booCollided(level.playerGetX(), level.playerGetY())) {
            if (lives >= 1) {
                lives--;
                level.setIntialLives(lives);
            }
            if (level.getIntialLives() == 0) {
                GameOver(c, getWidth(), getHeight());
                gameThread.interrupt();
            }
        }

        //DRAW the score
        scoreBoard(c, scoreX, scoreY, score);

        //Display the level
        displayLevel(c, getWidth()/2 - 50, 100, levelCounter+1);

        //Display lives
        displayLives(c,scoreX,scoreY+100,level.getIntialLives());


    } //EOF draw
    /****
     * initializeLevels
     * @params: None
     * Description: this function is not static which means it is function that is an instance of the class.
     * It initializes the levels of the game by storing them into the hashmap <Integer, Level > which will contain all the the necessary data for three levels
     * *****/
    private Level initializeLevel(int level)
    {
        Log.d(Name, "InitiallizeLevel!!");
        int i, j;

         /*SET player bitmaps*/
        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sungsoo);
        Bitmap animatedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.troll);
        Player player = new Player(playerBitmap,animatedBitmap, getWidth(), getHeight()); //Set player constructor

        //initialize the background
        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.level_1short1);
        Maps maps = new Maps(background, background.getWidth() * getHeight() / background.getHeight(), getHeight());

        //Initialize the flag
        Bitmap _flagpole = BitmapFactory.decodeResource(getResources(), R.drawable.supermarioflag);
        int flagpole_left = background.getWidth();
        int flagpole_bot = getHeight()*9/11;
        Objects flagpole = new Objects(_flagpole, flagpole_left, flagpole_bot-_flagpole.getHeight(),flagpole_left + _flagpole.getWidth(), flagpole_bot);


        //initialize terrain
        Bitmap _terrain = BitmapFactory.decodeResource(getResources(), R.drawable.terrain);
        int terrain_left = background.getWidth()*2/5;
        int terrain_bot = getHeight()*4/5;
        Objects terrain = new Objects(_terrain,terrain_left,terrain_bot - _terrain.getHeight(),terrain_left+_terrain.getWidth(),terrain_bot);

//        //initialize mushroom, boo, coin and terrain
        List<Objects> mushrooms = new ArrayList<>();
        List<Objects> boos = new ArrayList<>();
        List<Objects> coins = new ArrayList<>();


        difficultiesLevel(level, background, mushrooms, boos, coins);


        /**Return the level***/
        return new Level(player,flagpole,mushrooms,maps,boos,coins,terrain);
    } //EOF initializeLevels

    /***difficultiesLevel
     * @param: level - get the level integer from level1,2,3
     * @param: background - get the bitmap of background to get width and height
     * @param: mushrooms - get a list of mushrooms so we can customize how many are they in each level
     * Description:
     * Set difficulties based on level.
     ****/
    private void difficultiesLevel(int level, Bitmap background, List<Objects> mushrooms,List<Objects> boos, List<Objects> coins)
    {
        //initialize mushroom
        Bitmap mushroombitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goomba1);
        int mushroom_left = background.getWidth()/2;
        int mushroom_bot = getHeight()*9/11;
        for(int i = 0;i<level*2+1;++i)
        {
            Objects mushroom = new Objects(mushroombitmap,mushroom_left + 450 * i,mushroom_bot-mushroombitmap.getHeight(),mushroom_left+mushroombitmap.getWidth() + 450 * i,mushroom_bot);
            mushrooms.add(mushroom);
        }
        //initialize boo
        Bitmap boobitmap = BitmapFactory.decodeResource(getResources(), R.drawable.boo);
        int boo_left = background.getWidth()*3/5;
        int boo_bot = getHeight()*3/5;
        for(int b = 0; b<level+1;++b){
            Objects boo = new Objects(boobitmap,boo_left + 300 * b,boo_bot-boobitmap.getHeight(),boo_left+boobitmap.getWidth() + 300 * b,boo_bot);
            boos.add(boo);
        }
        //initialize coin
        Bitmap coinbitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goldcoin);
        int coin_left = background.getWidth()*3/5;
        int coin_bot = getHeight()*9/11;
        for (int cn = 0; cn<level+1; ++cn) {
            Objects coin = new Objects(coinbitmap,coin_left +400 * cn,coin_bot-(coinbitmap.getHeight()/2),coin_left+((coinbitmap.getWidth()/2 + 400 * cn)),coin_bot);
            coins.add(coin);
        }
    }

    /***difficultiesLevel
     * @param: c - Canvas
     * @param: scoreX - X position of Score label
     * @param: scoreY - Y position of Score label
     * @param: score - score value
     * Description:
     *Display score board on the screen.
     ****/
    private static void scoreBoard(Canvas c, int scoreX, int scoreY, int score)
    {
        //DRAW the score
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        paint.setStyle(Paint.Style.FILL);
        String s = Integer.toString(score);
        c.drawText(s, scoreX, scoreY, paint);
    }
    private static void displayLevel(Canvas c, int levelX, int levelY, int level)
    {
        //DRAW the score
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setTextSize(100);
        paint.setStyle(Paint.Style.FILL);
        String s = Integer.toString(level);
        StringBuilder str = new StringBuilder();
        str.append("LEVEL " + s);
//        System.out.println(str.toString());
        c.drawText(str.toString(), levelX, levelY, paint);
    }

    private static void GameOver(Canvas c,int screen_width, int screen_height)
    {
        //DRAW the score
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        paint.setStyle(Paint.Style.FILL);

//        System.out.println(str.toString());
        c.drawText("GAME OVER!",screen_width/3, screen_height/2, paint);
    }
    public void displayLives(Canvas c, int livesX, int livesY, int livesnum)
    {
        //DRAW lives
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(80);
        paint.setStyle(Paint.Style.FILL);
        String s = Integer.toString(livesnum);
        StringBuilder str = new StringBuilder();
        str.append("LIVES: " +s);
        System.out.println(str.toString());
        c.drawText(str.toString(), livesX, livesY, paint);

    }






}
