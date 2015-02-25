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
import com.example.ninjareader.clients.ReadabilityClient;
import com.example.ninjareader.model.Article;
import com.example.ninjareader.model.FakeArticle;
import com.facebook.AppEventsLogger;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

import org.apache.http.Header;
import org.json.JSONObject;


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

        // example get article call
        ReadabilityClient.getArticleInfo("http://news.yahoo.com/congress-sends-keystone-bill-obama-plans-veto-140235568--finance.html", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(ReadingListActivity.this, "Success getting article info", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", response.toString());
                Article article = Article.fromJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ReadingListActivity.this, "Failed getting article info", Toast.LENGTH_SHORT).show();
                if (errorResponse != null) {
                    Log.d("ERROR", errorResponse.toString());
                }
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();

        // Determine how the user has linked
        if (ParseFacebookUtils.isLinked(currentUser)) {
            Toast.makeText(ReadingListActivity.this, "Linked with facebook", Toast.LENGTH_SHORT).show();
        }

        if (ParseTwitterUtils.isLinked(currentUser)) {
            Toast.makeText(ReadingListActivity.this, "Linked with twitter", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(ReadingListActivity.this, "Welcome " + currentUser.get("name"), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reading_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            ParseUser.getCurrentUser().logOut();
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(i);
            finish();
        }

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
