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

/**
 * Created by StephenLee on 5/4/16.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String Name = GameView.class.getSimpleName();

    /*****Initializations******/
    private Player player;
    private GameThread gameThread;

    /***MAP settings****/
    float gravity = 0f;

    //Score
    private int score;
    private int xpos=0; //this will check whether player is moving to the right.
    private int scoreX = 0;
    private int scoreY = 0;


    //background
    private Bitmap background; //THIS IS FULL SIZED BACKGROUND
    private int x1, x2, y1, y2 = 0;
    Rect bk = new Rect();

    /**Objects**/

    //mushroom
    private Bitmap mushroombitmap;
    private Objects mushroom;

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
        Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sungsoo);
        Bitmap animatedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.troll);
        player = new Player(playerBitmap,animatedBitmap, getWidth(), getHeight()); //Set player constructor
        //Set score
        scoreX =100;
        scoreY = getHeight()/11;
        //initialize the background
        background = BitmapFactory.decodeResource(getResources(), R.drawable.level_1short1);
        x2 = background.getWidth() * getHeight() / background.getHeight();
        y2 = getHeight();
        //initialize mushroom
        mushroombitmap= BitmapFactory.decodeResource(getResources(), R.drawable.goomba1);
        int mushroom_left = background.getWidth()/2;
        int mushroom_bot = getHeight()*9/11;
        mushroom = new Objects(mushroombitmap,mushroom_left,mushroom_bot-mushroombitmap.getHeight(),mushroom_left+mushroombitmap.getWidth(),mushroom_bot);

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
        Log.d(Name, "Surface is getting destroyeeddd!");
        gameThread.interrupt(); //Stop the thread
        Log.d(Name, "Thread shut down cleanly");

    }//EOF SurfaceDestroyed


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int touchX = (int)event.getX();
        int touchY = (int)event.getX();
        player.update(touchX, touchY);
        return true;
    }



    public void draw(Canvas c) {
        //Set rect background to...
        bk.set(x1, y1, x2, y2);

        c.drawBitmap(background, null, bk, null);
        if(player.getX() > getWidth()*4/7)
        {
            x2 -= 30;
            x1 -= 30;
            xpos++;
            if(xpos>0)  //If and only if the traveled distance is positive
                score++; //increments score

        }
        else if(player.getX() < getWidth() *2/7)
        {
            x2 += 30;
            x1 += 30;
            xpos--; //going against the travel distance. Subtract xpos

        }


        //DRAW the score
        scoreBoard(c,scoreX,scoreY,score);

        //Set the mushroom movement
        mushroom.setMoveX(30); //Moving to the left
        //setting goomba somewhere on the rolling background
        if(mushroom.getX() > 0)
            mushroom.drawObject(c);

        //Check whether mushroom has been collided with player
        if(mushroom.collisionDetected(player.getX(), player.getY()))
        {
            player.setVisibility(false);
            gameThread.interrupt();
        }
        if(player.getVisibility())
        {
            player.draw(c);
            player.gravity();
        } //EOF
    } //EOF draw



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
