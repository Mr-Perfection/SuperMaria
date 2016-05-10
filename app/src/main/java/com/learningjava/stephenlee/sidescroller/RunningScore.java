package com.learningjava.stephenlee.sidescroller;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;

/**
 * Created by StephenLee on 5/10/16.
 */
public class RunningScore {

    int total = 0;

    public RunningScore() {



    }

    public void setScore(int points) {total+=points;}
    public String getScore() {return Integer.toString(total);}
}
