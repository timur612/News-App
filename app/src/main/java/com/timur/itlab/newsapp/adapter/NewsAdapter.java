package com.timur.itlab.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timur.itlab.newsapp.R;
import com.timur.itlab.newsapp.items.News;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    ArrayList<News> news;
    Context context;

    public NewsAdapter(ArrayList<News> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent,false);
        NewsHolder newsHolder = new NewsHolder(view);

        return newsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        News newsItem = news.get(position);

        String image = newsItem.getImage();
        String date = newsItem.getDate();
        String author = newsItem.getAuthor();
        String content = newsItem.getContent();


        holder.date.setText(date);
        holder.author.setText(author);
        holder.content.setText(content);

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView author;
        public TextView content;
        public TextView date;


        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.news_card_img);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.news_card_content);
            date = itemView.findViewById(R.id.date);


        }
    }

}
