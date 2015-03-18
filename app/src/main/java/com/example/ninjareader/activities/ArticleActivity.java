package com.example.ninjareader.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ninjareader.R;
import com.example.ninjareader.model.Article;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class ArticleActivity extends ActionBarActivity {

    TextView articleBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        //Article article = (Article) getIntent().getSerializableExtra("article");
         articleBody = (TextView) findViewById(R.id.tvArticleBody);

        String articleBodyHTML = getIntent().getStringExtra("bodyHTML");
        String objectId = getIntent().getStringExtra("objectId");

        ParseQuery<Article> query = ParseQuery.getQuery(Article.class);
        query.getInBackground(objectId, new GetCallback<Article>() {
            @Override
            public void done(Article article, ParseException e) {
                if(e==null) {
                    articleBody.setText(Html.fromHtml(article.getBodyHTML()));
                    articleBody.setMovementMethod(new ScrollingMovementMethod());
                }
                else {
                    Log.e("ArticleActivity", "error loading object");
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
