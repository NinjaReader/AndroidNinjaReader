package com.example.ninjareader.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ninjareader.R;
import com.example.ninjareader.activities.ReadingListActivity;
import com.example.ninjareader.model.Article;
import com.example.ninjareader.model.FakeArticle;
import com.squareup.picasso.Picasso;

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

        final Article article = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.reading_item, parent, false);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadingListActivity activity = (ReadingListActivity) getContext();
                activity.readArticle(article);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ReadingListActivity activity = (ReadingListActivity) getContext();
                activity.markAsRead(article);
                remove(article);
                return true;
            }
        });

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        ImageView ivLeadImage = (ImageView) convertView.findViewById(R.id.ivLeadImage);
        TextView tvItemNumber = (TextView) convertView.findViewById(R.id.tvItemNumber);
        TextView tvDomain = (TextView) convertView.findViewById(R.id.tvDomain);
        TextView tvWordCount = (TextView) convertView.findViewById(R.id.tvWordCount);

        tvTitle.setText(article.getTitle());
        if (!article.getLeadImageUrl().equals("null")) {
            ivLeadImage.setImageResource(android.R.color.transparent); // clear out the old image for a recycled view
            Picasso.with(getContext()).load(article.getLeadImageUrl()).into(ivLeadImage);
            ivLeadImage.setVisibility(View.VISIBLE);
        } else {
            ivLeadImage.setVisibility(View.GONE);
        }
        tvItemNumber.setText(String.valueOf(position+1));



        tvDomain.setText(article.getDomain());
        tvWordCount.setText(String.valueOf(article.getWordCount() + " words"));
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
