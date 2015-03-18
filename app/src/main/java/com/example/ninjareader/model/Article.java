package com.example.ninjareader.model;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mvince on 2/25/15.
 */
@ParseClassName("Article")
public class Article extends ParseObject {

    // Getters
    public String getTitle() {
        return getString("title");
    }

    public String getAuthor() {
        return getString("author");
    }

    public String getPublishedDate() {
        return getString("date_published");
    }

    public String getUrl() {
        return getString("url");
    }

    public String getLeadImageUrl() {
        return getString("lead_image_url");
    }

    public int getWordCount() {
        return getInt("word_count");
    }

    public String getDomain() {
        return getString("domain");
    }

    public String getBodyHTML() {
        String text = getString("bodyHTML");

        if(text == null)
            text = getTitle();

        if(text == null)
            return "";
        return text;

    }

    // Setters
    public void setTitle(String title) {
        put("title", title);
    }

    public void setAuthor(String author) {
        put("author", author);
    }

    public void setPublishedDate(String datePublished) {
        put("date_published", datePublished);
    }

    public void setUrl(String url) {
        put("url", url);
    }

    public void setLeadImageUrl(String leadImageUrl) {
        put("lead_image_url", leadImageUrl);
    }

    public void setWordCount(int wordCount) {
        put("word_count", wordCount);
    }

    public void setDomain(String domain) { put("domain", domain); }

    public void setBodyHTML(String bodyHTML) {
        put("bodyHTML", bodyHTML);
    }


    // Define callback interface
    public interface SaveArticleCallback {
        void onArticleSaveSuccess(Article article);
        void onArticleSaveFailure(ParseException e);
    }

    // Stores article in Parse table
    public static Article fromJSON(JSONObject jsonObject, final SaveArticleCallback callback) {
        // SAMPLE jsonObject
        /**
         * {
         domain: "news.yahoo.com",
         next_page_id: null,
         url: "http://news.yahoo.com/congress-sends-keystone-bill-obama-plans-veto-140235568--finance.html",
         short_url: "http://rdd.me/oixds86z",
         author: null,
         excerpt: "WASHINGTON (AP) — House Speaker John Boehner (BAY'-nur) says Congress is sending President Barack Obama legislation to build the Keystone XL pipeline on Tuesday.The White House is indicating Obama&hellip;",
         direction: "ltr",
         word_count: 120,
         total_pages: 0,
         content: "<div class="book clearfix" score="-22.5"> <header class="header"> </header> <div class="body yom-art-content clearfix" score="14.5"> <meta value="2015-02-24T14:02:35Z"> <meta value="Congress sends Keystone bill to Obama, who plans to veto it"> <meta value=""> <meta value=""> <meta value="WASHINGTON (AP) — House Speaker John Boehner (BAY'-nur) says Congress is sending President Barack Obama legislation to build the Keystone XL pipeline on Tuesday."> <p>WASHINGTON (AP) — House Speaker John Boehner (BAY'-nur) says Congress is sending President Barack Obama legislation to build the Keystone XL pipeline on Tuesday.</p><p>The White House is indicating Obama will quickly veto it in private over Republican lawmakers' urging that he sign it.</p><p>It would be the third veto of Obama's presidency.</p><p>Republicans may try to override Obama's veto, but have yet to show they can muster the two-thirds majority in both chambers that they would need.</p><p>First proposed in 2008, the pipeline would connect Canada's tar sands to Gulf Coast refineries.</p><p>The White House has said repeatedly it will wait to make its decision about whether to let the project go forward until after a State Department review.</p><ul id="topics" class="hidden"><li>Politics &amp; Government</li><li>Executive Branch</li><li>Barack Obama</li><li>The White House</li></ul> </div> </div> ",
         date_published: null,
         dek: null,
         lead_image_url: null,
         title: "Congress sends Keystone bill to Obama, who plans to veto it",
         rendered_pages: 1
         }
         */

        // put article info
        final Article article = new Article();
        try {
            article.setAuthor(jsonObject.getString("author"));
            article.setLeadImageUrl(jsonObject.getString("lead_image_url"));
            article.setPublishedDate(jsonObject.getString("date_published"));
            article.setTitle(jsonObject.getString("title"));
            article.setUrl(jsonObject.getString("url"));
            article.setWordCount(jsonObject.getInt("word_count"));
            article.setDomain(jsonObject.getString("domain"));
            article.setBodyHTML(jsonObject.getString("content"));
            article.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        callback.onArticleSaveSuccess(article);
                    } else {
                        callback.onArticleSaveFailure(e);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return article;
    }
}