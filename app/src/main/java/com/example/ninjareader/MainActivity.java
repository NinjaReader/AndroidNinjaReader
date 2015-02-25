package com.example.ninjareader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ninjareader.Models.Article;
import com.facebook.AppEventsLogger;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;


public class MainActivity extends ActionBarActivity {

    public static final int PARSE_LOGIN_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //I'D LIKE TO MAKE IT WORK THROUGH THE STYLES..
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getAction().equals(Intent.ACTION_SEND))
        {
            String type = getIntent().getType();
            Log.i("TYPE", type);
            String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            Log.i("TEXT", text);
        }
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));

        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), PARSE_LOGIN_REQUEST_CODE);

        ParseTwitterUtils.initialize(getResources().getString(R.string.twitter_app_key), getResources().getString(R.string.twitter_app_secret));

        ArticleArrayAdapter articleArrayAdapter =
                new ArticleArrayAdapter(this, Article.GetFakeArticles());

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PARSE_LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ParseUser currentUser = ParseUser.getCurrentUser();

                // Determine how the user has linked
                if (ParseFacebookUtils.isLinked(currentUser)) {
                    Toast.makeText(MainActivity.this, "Linked with facebook", Toast.LENGTH_SHORT).show();
                }

                if (ParseTwitterUtils.isLinked(currentUser)) {
                    Toast.makeText(MainActivity.this, "Linked with twitter", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(MainActivity.this, "Welcome " + currentUser.get("name"), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Login was canceled", Toast.LENGTH_SHORT).show();
            }
        }
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
