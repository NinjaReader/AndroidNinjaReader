package com.example.ninjareader;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by mvince on 2/25/15.
 */
public class NinjaReaderApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));
    }
}
