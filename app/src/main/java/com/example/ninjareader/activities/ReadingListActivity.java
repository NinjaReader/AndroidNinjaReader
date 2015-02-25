package com.example.ninjareader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ninjareader.adapters.ArticleArrayAdapter;
import com.example.ninjareader.R;
import com.example.ninjareader.models.FakeArticle;
import com.facebook.AppEventsLogger;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;


public class ReadingListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //I'D LIKE TO MAKE IT WORK THROUGH THE STYLES..
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_SEND)) {
            String type = getIntent().getType();
            Log.i("TYPE", type);
            String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            Log.i("TEXT", text);
        }

        ArticleArrayAdapter articleArrayAdapter =
                new ArticleArrayAdapter(this, FakeArticle.GetFakeArticles());

        ListView lvReadingItems = (ListView) findViewById(R.id.lvArticles);

        lvReadingItems.setAdapter(articleArrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}