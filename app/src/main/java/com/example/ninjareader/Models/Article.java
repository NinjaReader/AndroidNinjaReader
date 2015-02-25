package com.example.ninjareader.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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
}