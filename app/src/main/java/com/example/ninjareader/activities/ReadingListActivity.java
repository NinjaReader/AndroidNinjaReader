package com.example.ninjareader.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ninjareader.R;
import com.example.ninjareader.adapters.ArticleArrayAdapter;
import com.example.ninjareader.clients.ReadabilityClient;
import com.example.ninjareader.fragments.AddArticleDialog;
import com.example.ninjareader.model.Article;
import com.facebook.AppEventsLogger;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReadingListActivity extends ActionBarActivity implements AddArticleDialog.AddUrlDialogListener{
    private ArrayList<Article> articles;
    private ArticleArrayAdapter articleArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_list);

        //I'D LIKE TO MAKE IT WORK THROUGH THE STYLES..
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Toast.makeText(ReadingListActivity.this, "Welcome " + ParseUser.getCurrentUser().get("name"), Toast.LENGTH_SHORT).show();

        articles = new ArrayList<Article>();
        articleArrayAdapter = new ArticleArrayAdapter(this, articles);

        ListView lvReadingItems = (ListView) findViewById(R.id.lvArticles);
        lvReadingItems.setAdapter(articleArrayAdapter);

        // Gets the user's current articles
        getArticles();

        // Handle any articles that were shared to this activity
        handleSharedArticle();
    }

    private void getArticles() {
        // Set up the query on the Bookmark table
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Bookmark");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("read", false);
        query.include("article");
        query.orderByDescending("createdAt");

        // Execute the query
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                for (int i = 0; i < parseObjects.size(); i++) {
                    Article article = (Article) parseObjects.get(i).getParseObject("article");
                    articles.add(article);
                }
                articleArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void handleSharedArticle() {
        Intent intent = getIntent();
        final String sharedUrl = intent.getStringExtra("shared_url");
        if (sharedUrl != null) {
            addNewArticle(sharedUrl);
        }
    }

    private void addToReadingList(final Article article) {
        // Add bookmark if it doesn't already exist
        // Set up the query on the Bookmark table
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Bookmark");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("article", article);

        // Execute the query
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (parseObjects.size() > 0) {
                    Toast.makeText(ReadingListActivity.this, "Article is already in user's reading list", Toast.LENGTH_SHORT).show();
                } else {
                    ParseObject bookmark = new ParseObject("Bookmark");
                    bookmark.put("user", ParseUser.getCurrentUser());
                    bookmark.put("article", article);
                    bookmark.put("read", false);
                    bookmark.saveInBackground();
                    Toast.makeText(ReadingListActivity.this, "Added article to user's reading list", Toast.LENGTH_SHORT).show();
                    articles.add(0, article);
                    articleArrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void getArticleInfo(String sharedUrl) {
        ReadabilityClient.getArticleInfo(sharedUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(ReadingListActivity.this, "Success getting article info", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", response.toString());
                Article.fromJSON(response, new Article.SaveArticleCallback() {
                    @Override
                    public void onArticleSaveSuccess(Article article) {
                        addToReadingList(article);
                    }

                    @Override
                    public void onArticleSaveFailure(ParseException e) {
                        Toast.makeText(ReadingListActivity.this, "Failed saving article", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(ReadingListActivity.this, "Failed getting article info", Toast.LENGTH_SHORT).show();
                if (errorResponse != null) {
                    Log.d("ERROR", errorResponse.toString());
                }
            }
        });
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
        if(id == R.id.action_add) {
            showAddDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAddDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddArticleDialog addArticleDialog = AddArticleDialog.newInstance("Add Article");
        addArticleDialog.show(fm, "fragment_add_dialog");
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

    @Override
    public void onFinishAddUrl(String url) {
        addNewArticle(url);
    }

    public void readArticle(Article article) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(article.getUrl()));
        startActivity(i);
    }

    private void addNewArticle(final String url) {
        // Add the url to User's reading list
        Toast.makeText(this, "Adding url " + url + " to reading list", Toast.LENGTH_SHORT).show();

        // Check if that Url already exists in the database
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Article");
        query.whereEqualTo("url", url);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (parseObjects.size() > 0) {
                    // Entry already exists
                    Toast.makeText(ReadingListActivity.this, url + " already exists in Article DB", Toast.LENGTH_SHORT).show();
                    addToReadingList((Article) parseObjects.get(0));
                } else {
                    // Add it to the Article DB
                    getArticleInfo(url);
                }

            }
        });
    }

    public void markAsRead(Article article) {
        // Set up the query on the Bookmark table
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Bookmark");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.whereEqualTo("article", article);

        // Execute the query
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                ParseObject bookmark = parseObjects.get(0);
                bookmark.put("read", true);
                bookmark.saveInBackground();
            }
        });
    }
}
