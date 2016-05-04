package com.learningjava.stephenlee.sidescroller;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    //Get the name of this class
    /*final means it cannot be modified and is final value*/
    private static final String Name = MainActivity.class.getSimpleName();
    // protected SpaceShooter spaceShooter;
    //protected SpaceView spaceView;
    // protected GameLoopThread gameLoopThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new GameView(this));
        Log.d(Name, "GameView added!");

        // spaceView = View.findViewById(R.id.entire_view);
        //spaceView.setOnTouchListener(spaceView);


        //creating a list of bullets
        // bullet = new ArrayList<Bullet>();
    }//EOF OnCreate

    @Override
    protected void onDestroy() {
        Log.d(Name, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(Name, "Stopping...");
        super.onStop();
    }
}




