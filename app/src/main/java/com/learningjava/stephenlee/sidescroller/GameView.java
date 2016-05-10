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

    //background
    private Bitmap background;
    private int x1, x2, y1, y2 = 0;

    //enemy
    private Bitmap enemybitmap;
    private Objects enemy;

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

        gameThread = new GameThread(this);  //Set game thread constructor
        gameThread.start();

        //initialize the background
        background = BitmapFactory.decodeResource(getResources(), R.drawable.level_1short1);
        x2 = background.getWidth() * getHeight() / background.getHeight();
        y2 = getHeight();

        //initialize enemy
        enemybitmap= BitmapFactory.decodeResource(getResources(), R.drawable.goomba1);


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

        int i, j;
//        c.drawColor(Color.rgb(135,206,250));
        Paint paint = new Paint();
        Rect bk = new Rect(x1, y1, x2, y2);
        c.drawBitmap(background, null, bk, paint);
        if(player.getX() > getWidth()*4/7){

            x2 -= 30;
            x1 -= 30;
//            System.out.println(x1+"x1 x2 "+x2);
        }
        if(player.getX() < getWidth() *2/7){

            x2 += 30;
            x1 += 30;
//            System.out.println(x1+"x1 x2"+x2);
        }
        //setting goomba somewhere on the rolling background
        if( x1 <= -300 && x1 >= -300-getWidth()){
            enemy = new Objects(enemybitmap,x1+(enemybitmap.getWidth()*3), getHeight()*2/3, x1+(enemybitmap.getWidth()*32/10), 300+enemybitmap.getHeight());
            enemy.drawObject(c);
            System.out.println("draw enemy");

        }
        if(player.getVisibility())
        {
            player.draw(c);
            player.gravity();
        }


    }






}
