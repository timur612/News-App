package com.timur.itlab.newsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.timur.itlab.newsapp.R;
import com.timur.itlab.newsapp.adapter.NewsAdapter;
import com.timur.itlab.newsapp.items.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<News> news;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        news = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        getNews();

    }

    private void getNews() {
        String url = "http://newsapi.org/v2/everything?q=apple&from=2020-06-18&to=2020-06-18&sortBy=popularity&apiKey=1b5af2e59d4c412abaa424533478e1c0";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for(int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String author = jsonObject.getString("title");
                        String content = jsonObject.getString("content");
                        String image = jsonObject.getString("urlToImage");
                        String date = jsonObject.getString("publishedAt");

                        if(author=="null"){
                            author = "Без автора";
                        }
                        News newsItem = new News();
                        newsItem.setDate(date);
                        newsItem.setAuthor(author);
                        newsItem.setContent(content);
                        newsItem.setImage(image);

                        news.add(newsItem);
                    }
                    newsAdapter = new NewsAdapter(news, MainActivity.this);
                    recyclerView.setAdapter(newsAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }
}
