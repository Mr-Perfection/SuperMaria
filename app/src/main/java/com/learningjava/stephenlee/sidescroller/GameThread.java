package com.learningjava.stephenlee.sidescroller;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by StephenLee on 5/4/16.
 */
public class GameThread extends Thread {
    private static final String Name = GameThread.class.getSimpleName();
    /***Initializations***/
    private GameView gameView;

    public GameThread(GameView _gameView)
    {
        gameView = _gameView;

    }

    @Override
    public void run() {

        SurfaceHolder surfaceHolder = gameView.getHolder();  //Get surfaceHolder
        Log.d(Name, "Starting game loop");


        // Main game loop.
        while ( !Thread.interrupted()) {

            Canvas canvas = surfaceHolder.lockCanvas(null);

            try {
                synchronized (surfaceHolder) {

                    gameView.draw(canvas); //drawing frame


                }
            } catch (Exception e) {} finally {
                if (canvas != null) {
//                    System.out.println("inif");
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
// Set the frame rate by setting this delay
            try {

                Thread.sleep(1);
            } catch (InterruptedException e) {
// Thread was interrupted while sleeping.
//                System.out.println("b4return");
                return;

            }
        } //While
    }
}
