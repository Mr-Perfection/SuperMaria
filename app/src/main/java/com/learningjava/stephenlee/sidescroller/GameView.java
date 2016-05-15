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

/**
 * Created by StephenLee on 5/4/16.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String Name = GameView.class.getSimpleName();
    /*****Initializations******/
//    private Player player;
    private GameThread gameThread;

    /***MAP settings****/
    private Map<Integer, Level> levels = new HashMap<>();
    Level level1;
    private Level level;
    private int levelCounter = 0;
    float gravity = 0f;

    //Score
    private int score;
    private int xpos=0; //this will check whether player is moving to the right.
    private int scoreX = 0;
    private int scoreY = 0;

   //Objects
    List<Objects> mushrooms = new ArrayList<>();

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
            levels.put(i,initializeLevel());
        }

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

//        maps.draw(c);
//        if(player.getX() > getWidth()*4/7)
        if(level.playerGetX() > getWidth()*4/7)
        {
            level.setbgMove(-30);
            xpos++;
            if(xpos>0)  //If and only if the traveled distance is positive
                score++; //increments score
            Log.d(Name, "flagpole moved left!");
//            flagpole.setMoveX(30);
            level.setFlagpoleMove(30);
        }
//        else if(player.getX() < getWidth() *2/7)
        else if(level.playerGetX() < getWidth() *2/7)
        {
            level.setbgMove(30);
            xpos--; //going against the travel distance. Subtract xpos
//            flagpole.setMoveX(-30);
            level.setFlagpoleMove(-30);
        }

        if(level.flagPoleCollided(level.playerGetX(),level.playerGetY()))
        {

            Log.d(Name, "flagpole collided!");
            try {
                ++levelCounter;
                level = levels.get(levelCounter);
                gameThread.sleep(2000);
                Log.d(Name, "Current game level is: "+levelCounter);

            }
            catch (InterruptedException ex)
            {
                Log.d(Name, "Something went wrong with flag collision!");
                gameThread.interrupt();
            }

        }
        //DRAW the score
        scoreBoard(c, scoreX, scoreY, score);

        //Set the mushroom movement
        level.setMushroomMove(30);


        //Check whether mushroom has been collided with player
        if (level.mushroomCollided(level.playerGetX(), level.playerGetY()))
        {
//            player.setVisibility(false);

            gameThread.interrupt();
        }
        /**Level drawn***/
        level.draw(c);

//        if(player.getVisibility())
        if(level.playerGetVisibility())
        {

            level.player.draw(c);
//            player.gravity();
            level.playerGravity();
        } //EOF


    } //EOF draw


    /****
     * initializeLevels
     * @params: None
     * Description: this function is not static which means it is function that is an instance of the class.
     * It initializes the levels of the game by storing them into the hashmap <Integer, Level > which will contain all the the necessary data for three levels
     * *****/
    private Level initializeLevel()
    {

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
        int flagpole_left = background.getWidth() * 9/11;
        int flagpole_bot = getHeight()*9/11;
        Objects flagpole = new Objects(_flagpole, flagpole_left, flagpole_bot-_flagpole.getHeight(),flagpole_left + _flagpole.getWidth(), flagpole_bot);

        //initialize mushroom
        Bitmap mushroombitmap = BitmapFactory.decodeResource(getResources(), R.drawable.goomba1);
        int mushroom_left = background.getWidth()/2;
        int mushroom_bot = getHeight()*9/11;

        for(i=0;i<5;++i)
        {
            Objects mushroom = new Objects(mushroombitmap,mushroom_left + 300 * i,mushroom_bot-mushroombitmap.getHeight(),mushroom_left+mushroombitmap.getWidth() + 300 * i,mushroom_bot);
            mushrooms.add(mushroom);
        }
        //initialize level
//        level1 = new Level(flagpole,mushrooms,maps);
        return new Level(player, flagpole,mushrooms,maps);
    } //EOF initializeLevels

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






}
