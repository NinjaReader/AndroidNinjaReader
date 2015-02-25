package com.example.ninjareader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ninjareader.Models.Article;

import java.util.ArrayList;

/**
 * Created by ccoria on 2/22/15.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public ArticleArrayAdapter(Context context, ArrayList<Article> objects) {
        super(context, R.layout.reading_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Article article = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.reading_item, parent, false);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getContext();
                //open the article
            }
        });

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvItemNumber = (TextView) convertView.findViewById(R.id.tvItemNumber);
        tvTitle.setText(article.getTitle());
        tvItemNumber.setText(getNumber(position));
        return convertView;
    }

    private String getNumber(int position) {
        // http://unicode-search.net/unicode-namesearch.pl?term=circled
        String[] circledNumbers = new String[]{"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"};
        circledNumbers[0] = "➊";
        circledNumbers[1] = "➋";
        return circledNumbers[position];
    }
}
