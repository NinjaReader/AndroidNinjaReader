package com.example.ninjareader.Models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ccoria on 2/22/15.
 */
public class Article {
    private String title;
    private String origin;
    private String imgUrl;
    private Date dueDate;

    public Article(String title, String origin, String imgUrl, Date dueDate) {
        this.title = title;
        this.origin = origin;
        this.imgUrl = imgUrl;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getOrigin() {
        return origin;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getPrettyDueDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM.dd");
        String formattedDateString = formatter.format(this.dueDate);

        return formattedDateString;
    }

    public static ArrayList<Article> GetFakeArticles () {
        ArrayList<Article> fakeArticles = new ArrayList();

        Date currentDate = new Date();

        fakeArticles.add(new Article(
                "What your business can learn from Ello?",
                "readwrite.com",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "Holo Everywhere | Android Developers Blog",
                "android-developers.blogspot.com",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "The Netflix Tech Blog: Node.js in Flames",
                "techblog.netflix.com",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "Proof-of-work system - Wikipedia",
                "en.wikipedia.org",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "Turing Test Passed for First Time",
                "ca.news.yahoo.com",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "The next supermodel",
                "economist.com",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "5 Ways Graphene Will Change Gadgets Forever",
                "news.yahoo.com",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "What your business can learn from Ello?",
                "readwrite.com",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "Holo Everywhere | Android Developers Blog",
                "android-developers.blogspot.com",
                "",
                currentDate));

        fakeArticles.add(new Article(
                "The Netflix Tech Blog: Node.js in Flames",
                "techblog.netflix.com",
                "",
                currentDate));

        return fakeArticles;
    }
}
