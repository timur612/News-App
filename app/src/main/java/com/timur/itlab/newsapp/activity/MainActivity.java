package com.timur.itlab.newsapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<News> news;
    private RequestQueue requestQueue;
    private SimpleDateFormat input;
    private SimpleDateFormat output;
    private Date d;
    private String formatted;
    private TextView contentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.diolog_content,null);
                contentText = mView.findViewById(R.id.news_card_content);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });


        news = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        output = new SimpleDateFormat("dd/MM/yyyy");
        d = null;

        getNews();

    }

    private void getNews() {
        String url = "http://newsapi.org/v2/everything?domains=wsj.com&apiKey=1b5af2e59d4c412abaa424533478e1c0";

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
                        try
                        {
                            d = input.parse(date);

                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }
                        if(d!=null){
                            formatted = output.format(d);
                        }
                        else{
                            formatted = "00/00";
                        }
                        if(author=="null"){
                            author = "Без автора";
                        }
                        News newsItem = new News();
                        newsItem.setDate(formatted);
                        newsItem.setAuthor(author);
                        newsItem.setContent(content);
                        newsItem.setImage(image);
                        try {
                            contentText.setText(content);
                        }catch (Exception e){
                            e.printStackTrace();
                        }



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
