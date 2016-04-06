package com.androidhunter.testynotes;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by amohnacs on 1/27/16.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //ParseCrashReporting.enable(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
